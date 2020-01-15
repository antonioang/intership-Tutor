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
public class VisualizzaTirocini extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        if(request.getParameter("search") != null){
            action_search(request,response);
        }
        action_default(request, response);

    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Registrazione Azienda");
            res.activate("registrazione_azienda.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }

    private void action_search(HttpServletRequest request, HttpServletResponse response){
        //durata, titolo,  facilitazioni, luogo, settore, obiettivi, corsoStudio
        if(SecurityLayer.checkNumber(request.getParameter("durata")) && SecurityLayer.checkString(request.getParameter("titolo")) && SecurityLayer.checkString(request.getParameter("facilitazioni")) &&
                SecurityLayer.checkString(request.getParameter("luogo")) && SecurityLayer.checkString(request.getParameter("settore")) && SecurityLayer.checkString(request.getParameter("obiettivi")) &&
                SecurityLayer.checkString(request.getParameter("corsoStudio"))){
            
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
