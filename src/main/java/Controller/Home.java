/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class Home extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        try {
            TemplateResult res;
            if (s!= null) {
                request.setAttribute("nome_utente", (String) s.getAttribute("username"));
                request.setAttribute("tipo", s.getAttribute("tipo"));
                switch((int) request.getAttribute("tipo")){
                    case 0:
                        //ADMIN

                        //setto i dati necessari

                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        res.activate("homepage_admin.ftl.html", request, response);
                        break;
                    case 1:
                        //STUDENTE

                        //setto i dati necessari

                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        request.setAttribute("activeHome", "active");
                        res.activate("homepage_studente.ftl.html", request, response);
                        break;
                    case 2:
                        //AZIENDA

                        //setto i dati necessari

                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        request.setAttribute("activeHome", "active");
                        res.activate("homepage_azienda.ftl.html", request, response);
                        break;
                    default:
                        //default-code
                }
            }
            else{
                 //ANONIMO
                res = new TemplateResult(getServletContext());
                request.setAttribute("activeHome", "active");
                res.activate("index.ftl.html", request, response);   
            }
        } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
        }
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response){
        if(request.getAttribute("eccezione") != null){
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("eccezione"), request, response);
        }
        else{
            System.out.println((String) request.getAttribute("errore"));
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }


}
