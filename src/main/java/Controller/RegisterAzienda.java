/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Utente;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Mattia Lenza
 */
public class RegisterAzienda extends BaseController {

   BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         try{
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("nome_utente", (String)s.getAttribute("username"));
                request.setAttribute("tipologia", (String)s.getAttribute("tipologia"));
            }
            if (request.getParameter("register_azienda") != null) {
                BaseDataLayer dl = (BaseDataLayer)request.getAttribute("datalayer");
                if(dl == null) System.out.println("DataLayer Non c'è");
                action_registrazione_azienda(request, response);
            }
            else{
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("page_title", "Registrazione Azienda");
                res.activate("registrazione_azienda.ftl.html", request, response);
            }
        } catch (TemplateManagerException ex){
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
        
    }
    
    private void action_registrazione_azienda(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, TemplateManagerException {
            
            try{
                //CREO IL RESPONSABILE DEI TIROCINI
                BaseDataLayer dl = (BaseDataLayer)request.getAttribute("datalayer");
                if(dl == null) System.out.println("DataLayer Non Presente");
                Persona responsabile_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().createPersona();
                //validazione input responsabile tirocini
                if (SecurityLayer.checkString(request.getParameter("nome_rt")) && SecurityLayer.checkString(request.getParameter("cognome_rt")) &&
                        SecurityLayer.checkEmail(request.getParameter("email_rt")) && SecurityLayer.checkTelNum(request.getParameter("telefono_rt"))){

                    responsabile_tirocini.setNome(request.getParameter("nome_rt"));
                    responsabile_tirocini.setCognome(request.getParameter("cognome_rt"));
                    responsabile_tirocini.setEmail(request.getParameter("email_rt"));
                    responsabile_tirocini.setTelefono(request.getParameter("telefono_rt"));
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().addPersona(responsabile_tirocini);
                    if (insert != 1) {
                        request.setAttribute("errore", "errore_inserimento");
                        request.setAttribute("messaggio", "Il responsabile tirocini già esistente. Riprova!");
                        action_error(request, response);
                    }
                } else {
                    request.setAttribute("errore", "errore_convalida");
                    request.setAttribute("messaggio", "I campi del responsabile tirocini non sono corretti. Riprova!");
                    action_error(request, response);
                }
                //CREO L'UTENTE 
                Utente ut = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().createUtente();
                // validazione input utente
                if (SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkString(request.getParameter("password")) &&
                        SecurityLayer.checkEmail(request.getParameter("email"))) {

                    /* encrypt pass */
                    String password = request.getParameter("password");
                    String passwordCriptata = passwordEncryptor.encryptPassword(password);

                    ut.setUsername(request.getParameter("username"));
                    ut.setPassword(passwordCriptata);
                    ut.setEmail(request.getParameter("email"));
                    ut.setTipo(2);
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().addUtente(ut);
                    if (insert != 1) {
                        request.setAttribute("errore", "errore_inserimento");
                        request.setAttribute("messaggio", "L'utente già esistente. Riprova!");
                        //Se l'inserimento dell'utente fallisce elimino la persona creata in precedenza
                        action_delete_rt(request, response, responsabile_tirocini);
                        action_error(request, response);
                    }
                } else {
                    request.setAttribute("errore", "errore_convalida");
                    request.setAttribute("messaggio", "I campi utente inseriti non sono validi. Riprova!");
                    //Se l'inserimento dell'utente fallisce elimino la persona creata in precedenza
                    action_delete_rt(request, response, responsabile_tirocini);
                    action_error(request, response);
                }

                //CREO L'AZIENDA
                Azienda az = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().createAzienda();
                //validazione input azienda
                if (SecurityLayer.checkString(request.getParameter("ragione_sociale")) && SecurityLayer.checkString(request.getParameter("indirizzo")) &&
                        SecurityLayer.checkString(request.getParameter("citta")) && SecurityLayer.checkCap(request.getParameter("cap")) &&
                        SecurityLayer.checkString(request.getParameter("provincia")) && SecurityLayer.checkString(request.getParameter("rappresentante_legale")) &&
                        SecurityLayer.checkString(request.getParameter("piva")) && SecurityLayer.checkString(request.getParameter("foro_competente")) &&
                        SecurityLayer.checkString(request.getParameter("tematica")) && SecurityLayer.checkString(request.getParameter("corso_studio")) &&
                        SecurityLayer.isDate(request.getParameter("inizio_conv")) && SecurityLayer.isDate(request.getParameter("fine_conv"))) {

                    az.setRagioneSociale(request.getParameter("ragione_sociale"));
                    az.setIndirizzo(request.getParameter("indirizzo"));
                    az.setCitta(request.getParameter("citta"));
                    az.setCap(Integer.parseInt(request.getParameter("cap")));
                    az.setProvincia(request.getParameter("provincia"));
                    az.setRapprLeg(request.getParameter("rappresentante_legale"));
                    az.setPiva(request.getParameter("piva"));
                    az.setForoCompetente(request.getParameter("foro_competente"));
                    az.setTematica(request.getParameter("tematica"));
                    az.setCorsoStudi(request.getParameter("corso_studio"));
                    az.setInizioConv(SecurityLayer.checkDate(request.getParameter("inizio_conv")));
                    az.setFineConv(SecurityLayer.checkDate(request.getParameter("fine_conv")));
                    az.setRespTirocini(responsabile_tirocini.getId());
                    az.setUtente(ut);
                    
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().addAzienda(az);
                    if (insert != 1) {
                        request.setAttribute("errore", "errore_inserimento");
                        request.setAttribute("messaggio", "Azienda già esistente!");
                        //Se l'inserimento dell'azienda fallisce cancello sia l'utente che il responsabile tirocini
                        action_delete_ut(request, response, ut);
                        action_delete_rt(request, response, responsabile_tirocini);
                        
                        action_error(request, response);
                    }
                    //REGISTRAZIONE AVVENUTA CON SUCCESSO
                    request.setAttribute("MSG", "Grazie per la registrazione. \nPotrai eseguire l'accesso non appena l'admin confermerà la vostra richiesta di convenzionamento");
                    request.setAttribute("ICON", "fas fa-check");
                    request.setAttribute("alert", "success");
                    response.sendRedirect("prova");
                }  else {
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I dati aziendali inseriti non sono validi. Riprova!");
                    //Se l'inserimento dell'azienda fallisce cancello sia l'utente che il responsabile tirocini
                    action_delete_ut(request, response, ut);
                    action_delete_rt(request, response, responsabile_tirocini);
                        
                    action_error(request, response);
                }
            } catch (DataLayerException ex){
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }

    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute("eccezione") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("eccezione"), request, response);
        } else {
            request.setAttribute("referrer", "registrazione_azienda.ftl.html");

            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }  
    
    private void action_delete_ut(HttpServletRequest request, HttpServletResponse response, Utente ut){
        try {
            ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().delUtente(ut);
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", "Errore durante la cancellazione dell'utente: "+ex);
            action_error(request, response);
        }
    }
    
    public void action_delete_rt(HttpServletRequest request, HttpServletResponse response, Persona rt){
        try {
            ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().delPersona(rt.getId());
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", "Errore durante la cancellazione dell'utente: "+ex);
            action_error(request, response);
        }
    }
    
}
