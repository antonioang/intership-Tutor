/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import framework.result.FailureResult;
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
public class Logout extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("username", (String)s.getAttribute("username"));
                request.setAttribute("tipo", s.getAttribute("tipo"));
            }
            action_logout(request, response);
        } catch (IOException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        SecurityLayer.disposeSession(request);
        //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
        if (request.getParameter("referrer") != null) {
            response.sendRedirect(request.getParameter("referrer"));
        } else {
            response.sendRedirect("home");
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
