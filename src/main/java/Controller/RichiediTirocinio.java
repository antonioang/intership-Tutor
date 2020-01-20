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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class RichiediTirocinio extends BaseController {
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("id_tirocinio") != null && !request.getParameter("id_tirocinio").equals("0")){
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("username", s.getAttribute("username"));
                request.setAttribute("tipo", (int)s.getAttribute("tipo"));
            }
            if (request.getParameter("submit_richiesta") != null) {
                action_submit_richiesta(request, response);
            }
            else if(request.getParameter("submit_tutore") != null){
                action_crea_tutore(request, response);
            }
            else{
                action_default(request, response);
            }

        }
        
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        try {
            //MOSTRO IL TEMPLATE
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("richiesta_tirocinio.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_submit_richiesta(HttpServletRequest request, HttpServletResponse response){
        
    }
    
    private void action_crea_tutore(HttpServletRequest request, HttpServletResponse response){
        
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
