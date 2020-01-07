/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Utente;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Login extends HttpServlet {
    
    BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        
        try{
            HttpSession sessione = SecurityLayer.checkSession(request);
            if(sessione != null){
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("tipo", sessione.getAttribute("tipo"));
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
        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("pagina", "login");
        
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
                   
                    //controllo se il referrer è definito
                    if(request.getParameter("referrer") !=null){
                        //0 = admin, 1 = studente, 2 = azienda
                        int tipo = utente.getTipo();
                        switch(tipo){
                            case 0:
                                //admin
                                break;
                            case 1:
                                //studente
                                response.sendRedirect(request.getParameter("referrer"));
                                break;
                            case 2:
                                //azienda
                                response.sendRedirect("home-azienda");
                                break;
                            default:
                                //default code
                        }
                        
                    } else{ //altrimenti rimando alla home page
                        response.sendRedirect("home");
                    }
                } else{
                    //errore credenziali di accesso
                    request.setAttribute("messaggio", "errore_autenticazione");
                    request.setAttribute("errore", "Errore durante la fase di Autenticazione, Username e/o Password non validi, Riprova");
                    action_error(request, response);
                }
            } else{
                //errore formato campi
                request.setAttribute("messaggio", "errore_autenticazione");
                request.setAttribute("errore", "Errore durante la fase di Autenticazione, controlla che i campi inseriti siano corretti e riprova");
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
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }
    
}