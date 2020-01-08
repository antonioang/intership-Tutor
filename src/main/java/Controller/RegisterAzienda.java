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
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Mattia Lenza
 */
public class RegisterAzienda extends BaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("nome_utente", (String)s.getAttribute("username"));
            request.setAttribute("tipologia", (String)s.getAttribute("tipologia"));
        }
        
        if (request.getParameter("register_studente") != null) {
            try {
                action_registrazione_azienda(request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        } 
        
    }
    
    private void action_registrazione_azienda(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, TemplateManagerException {
            
            try{
                //Creo il RESPONSABILE DEI TIROCINI
                Persona responsabile_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().createPersona();
                //validazione input responsabile tirocini
                if (SecurityLayer.checkString(request.getParameter("nome_rt")) && SecurityLayer.checkString(request.getParameter("cognome_rt")) &&
                        SecurityLayer.checkEmail(request.getParameter("email_rt")) && SecurityLayer.checkTelNum(request.getParameter("telefono_rt"))){

                    responsabile_tirocini.setNome(request.getParameter("nome_rt"));
                    responsabile_tirocini.setCognome(request.getParameter("cognome_rt"));
                    responsabile_tirocini.setEmail(request.getParameter("email_rt"));
                    responsabile_tirocini.setTelefono(SecurityLayer.checkNumeric(request.getParameter("telefono_rt")));
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().insertPersona(responsabile_tirocini);
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
                // controlli sull'utente
                if (SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkString(request.getParameter("pw")) &&
                        SecurityLayer.checkEmail(request.getParameter("email"))) {

                    /* encrypt pass */
                    String password = request.getParameter("password");
                    String encryptedPassword = passwordEncryptor.encryptPassword(password);

                    ut.setUsername(request.getParameter("username"));
                    ut.setPassword(encryptedPassword);
                    ut.setEmail(request.getParameter("email"));
                    ut.setTipo(2);
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().addUtente(ut);
                    if (insert != 1) {
                        request.setAttribute("errore", "errore_inserimento");
                        request.setAttribute("messaggio", "L'utente già esistente. Riprova!");
                        action_error(request, response);
                    }
                } else {
                    request.setAttribute("errore", "errore_convalida");
                    request.setAttribute("messaggio", "I campi utente inseriti non sono validi. Riprova!");
                    action_error(request, response);
                }

                //CREO L'AZIENDA
                Azienda az = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().createAzienda();
                //validazione input azienda
                if (SecurityLayer.checkString(request.getParameter("ragione_sociale")) && SecurityLayer.checkString(request.getParameter("indirizzo")) &&
                        SecurityLayer.checkString(request.getParameter("citta")) && SecurityLayer.checkCap(request.getParameter("cap")) &&
                        SecurityLayer.checkString(request.getParameter("provincia")) && SecurityLayer.checkString(request.getParameter("rappresentante_legale")) &&
                        SecurityLayer.checkString(request.getParameter("piva")) && SecurityLayer.checkString(request.getParameter("foro_competente")) &&
                        SecurityLayer.checkString(request.getParameter("tematiche")) && SecurityLayer.checkString(request.getParameter("corso_studio")) &&
                        SecurityLayer.isDate(request.getParameter("inizio_conv")) && SecurityLayer.isDate(request.getParameter("fine_conv"))) {

                    az.setRagioneSociale(request.getParameter("ragione_sociale"));
                    az.setIndirizzo(request.getParameter("indirizzo"));
                    az.setCitta(request.getParameter("citta"));
                    az.setCap(Integer.parseInt(request.getParameter("cap")));
                    az.setProvincia(request.getParameter("provincia"));
                    az.setRapprLeg(request.getParameter("rappresentante_legale"));
                    az.setPiva(Integer.parseInt(request.getParameter("piva")));
                    az.setForoCompetente(request.getParameter("foro_competente"));
                    az.setTematica(request.getParameter("tematica"));
                    az.setCorsoStudi(request.getParameter("corso_studio"));
                    az.setRespTirocini(responsabile_tirocini);
                    az.setUtente(ut);
                    az.setInizioConv(SecurityLayer.checkDate(request.getParameter("inizio_conv")));
                    az.setFineConv(SecurityLayer.checkDate(request.getParameter("fine_conv")));
                    int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().insertAzienda(az);
                    if (insert != 1) {
                        request.setAttribute("errore", "errore_inserimento");
                        request.setAttribute("messaggio", "Azienda già esistente!");
                        action_error(request, response);
                    }
                    //registrazione avvenuta con successo
                    request.setAttribute("MSG", "Grazie per la registrazione. \nPotrai eseguire l'accesso non appena l'admin confermerà la vostra richiesta di convenzionamento");
                    request.setAttribute("ICON", "fas fa-check");
                    request.setAttribute("alert", "success");
                    TemplateResult res = new TemplateResult(getServletContext());
                    res.activate("home_anonimo.ftl.html", request, response);
                }  else {
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I dati aziendali inseriti non sono validi. Riprova!");
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
            request.setAttribute("referrer", "registrazione.ftl.html");

            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }  

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
