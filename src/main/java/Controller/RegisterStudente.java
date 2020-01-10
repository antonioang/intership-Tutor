/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Studente;
import Model.Interfaces.Utente;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Mattia Lenza
 */
public class RegisterStudente extends BaseController{
    BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("nome_utente", (String)s.getAttribute("username"));
                request.setAttribute("tipologia", (String)s.getAttribute("tipologia"));
            }
            if (request.getParameter("register_studente") != null) {
                action_registrazione_studente(request, response);
            }
            else{
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("page_title", "Registrazione Studente");
                res.activate("registrazione_studente.ftl.html", request, response);
            }
        } catch (TemplateManagerException ex){
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_registrazione_studente(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException, TemplateManagerException {
        try {
            //CREO L'UTENTE
            Utente ut = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().createUtente();
            // validazione input dati dell'utente
            if (SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkString(request.getParameter("password")) &&
                    SecurityLayer.checkEmail(request.getParameter("email"))) {

                //Controllo se l'utente esiste già
                if (((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().utenteExists(request.getParameter("username"), request.getParameter("email"))){
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "Email già esistente. Riprova con un altro indirizzo email!");
                    action_error(request, response);
                    return;
                }

                /* encrypt password */
                String password = request.getParameter("password");
                String encryptedPassword = passwordEncryptor.encryptPassword(password);
                //setto i valori nell'oggetto utente
                ut.setUsername(request.getParameter("username"));
                ut.setPassword(encryptedPassword);
                ut.setEmail(request.getParameter("email"));
                ut.setTipo(1);
                //Inserisco l'utente nel DB
                int insert = ((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().addUtente(ut);
                if (insert != 1) {
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Errore inserimento studente. Riprova!");
                    action_error(request, response);
                    return;
                }
            } else {
                request.setAttribute("errore", "errore_validazione");
                request.setAttribute("messaggio", "I campi inseriti non sono corretti. Riprova!");
                action_error(request, response);
            }
            //Dopo aver creato l'oggetto utente creo un oggetto studente
            Studente st = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().createStudente();
             // validazione input dati dello studente
            if (SecurityLayer.checkString(request.getParameter("nome")) && SecurityLayer.checkString(request.getParameter("cognome")) &&
                    SecurityLayer.checkString(request.getParameter("codice_fiscale")) && SecurityLayer.checkBoolean(request.getParameter("handicap")) &&
                    SecurityLayer.isDate(request.getParameter("data_nascita")) && SecurityLayer.checkString(request.getParameter("citta_nascita")) &&
                    SecurityLayer.checkString(request.getParameter("provincia_nascita")) && SecurityLayer.checkString(request.getParameter("citta_residenza")) &&
                    SecurityLayer.checkString(request.getParameter("cap_residenza")) && SecurityLayer.checkString(request.getParameter("provincia_residenza")) &&
                    SecurityLayer.checkString(request.getParameter("telefono")) && SecurityLayer.checkString(request.getParameter("corso_laurea")))  {

                //setto i valori nell'oggetto studente
                st.setNome(request.getParameter("nome"));
                st.setCognome(request.getParameter("cognome"));
                st.setCodFiscale(request.getParameter("codice_fiscale"));
                st.setDataNascita(SecurityLayer.checkDate(request.getParameter("data_nascita")));
                st.setCittaNascita(request.getParameter("citta_nascita"));
                st.setProvinciaNascita(request.getParameter("provincia_nascita"));
                st.setCittaResidenza(request.getParameter("citta_residenza"));
                st.setCapResidenza(Integer.parseInt(request.getParameter("cap_residenza")));
                st.setProvinciaResidenza(request.getParameter("provincia_residenza"));
                st.setTelefono(request.getParameter("telefono"));
                st.setCorsoLaurea(request.getParameter("corso_laurea"));
                st.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                st.setUtente(ut);
                
                //Inserisco lo studente nel DB
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().addStudente(st);
                if (insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Ipossibile inserire lo studente nel DB. Riprova!");
                    //Cancello l'utente creato in precedenza
                    action_delete_ut(request, response, ut);
                    action_error(request, response);
                }
                
                //Registrazione avenuta con successo
                request.setAttribute("MSG", "Registrazione effettuata con successo!\nOra puoi accedere ed iniziare ad usare i nostri servizi");
                request.setAttribute("ICON", "fas fa-check");
                request.setAttribute("alert", "success");
                request.setAttribute("TITLE", "OK");
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("login.ftl.html", request, response);

            } else {
                //campi in input non validi
                request.setAttribute("errore", "errore_validazione");
                request.setAttribute("messaggio", "I campi inseriti non sono corretti. Riprova!");
                //Cancello l'utente creato in precedenza
                action_delete_ut(request, response, ut);
                action_error(request, response);
            }


        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
        
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute("eccezione") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("eccezione"), request, response);
        } else {
            request.setAttribute("referrer", "registrazione_studente.ftl.html");

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
}
