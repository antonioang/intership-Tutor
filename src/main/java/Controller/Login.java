/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
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
public class Login extends BaseController {
    
    BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        
        try{
            HttpSession sessione = SecurityLayer.checkSession(request);
            if(sessione != null){
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("tipo", sessione.getAttribute("tipo"));
                response.sendRedirect("home");
            }
            if(request.getParameter("login") !=null){
                action_login(request, response);
            } else{
                action_default(request, response);
            }
        } catch (IOException ex){
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }    
    }
    
    private void action_default (HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException{
        System.out.println("action_default");
        TemplateResult res = new TemplateResult(getServletContext());
        
        //passamano del referrer (prima a login html poi alla servlet che valida il login)
        if (request.getParameter("referrer") != null) {
            request.setAttribute("referrer", request.getParameter("referrer") + "?" + request.getParameter("referrer_res"));
        }

        res.activate("login.ftl.html", request, response);
    }
    
    private void action_login (HttpServletRequest request, HttpServletResponse response) throws IOException{
        try{
            if(SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkString(request.getParameter("password"))){
                //Istanzio l'oggetto Utente
                Utente utente = ((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername(request.getParameter("username"));

                //Se l'utente è valido e la password è corretta creo una nuova Sessione
                
                if(utente != null && encryptor.checkPassword(request.getParameter("password"), utente.getPassword())){
                    SecurityLayer.createSession(request, utente.getUsername(), utente.getId(), utente.getTipo());
                    String nomeDaVisualizzare = "";
                    //Ottengo il nome da inserire nell'outline
                    switch (utente.getTipo()) {
                                //studente
                                case 1:
                                    Studente studente = ((BaseDataLayer) request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
                                    nomeDaVisualizzare = studente.getNome()+" "+studente.getCognome();
                                    break;

                                //azienda  
                                case 2:
                                    Azienda azienda = ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
                                    nomeDaVisualizzare = azienda.getNome();
                                    break;

                                //admin
                                case 3:
                                    nomeDaVisualizzare = utente.getUsername();
                                    break;
                        }
                    HttpSession sessione = SecurityLayer.checkSession(request);
                    sessione.setAttribute("nomeDaVisualizzare", nomeDaVisualizzare);
                    
                    //controllo se il referrer è definito
                    if(request.getParameter("referrer") == null){
                        response.sendRedirect("home");
                    } else{ //altrimenti rimando alla home page
                        response.sendRedirect(request.getParameter("referrer"));
                    }
                } else{
                    //errore credenziali di accesso
                    request.setAttribute("errore", "errore_autenticazione");
                    request.setAttribute("messaggio", "Errore durante la fase di Autenticazione, Username e/o Password non validi, Riprova");
                    action_error(request, response);
                }
            } else{
                //errore formato campi
                request.setAttribute("errore", "errore_autenticazione");
                request.setAttribute("messaggio", "Errore durante la fase di Autenticazione, controlla che i campi inseriti siano corretti e riprova");
                action_error(request, response);
            }
        } catch (DataLayerException ex){
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_error (HttpServletRequest request, HttpServletResponse response){
        if(request.getAttribute("eccezione") !=null){
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("eccezione"), request, response);
        } else{
            request.setAttribute("referrer", "login.ftl.html");
            System.out.println((String) request.getAttribute("errore"));
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }
    
}
