/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Tirocinio;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.util.List;
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
            //ottengo gli ultimi tirocini inseriti
            List<Tirocinio> ultimi_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getLatestTirocini();
            
            //setto i dati necessari
            request.setAttribute("ultimi_tirocini", ultimi_tirocini);
            
            //mostro il template
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("visualizza_tirocini.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }

    private void action_search(HttpServletRequest request, HttpServletResponse response){
        if(SecurityLayer.checkString(request.getParameter("titolo")) && SecurityLayer.checkString(request.getParameter("facilitazioni")) &&
                SecurityLayer.checkString(request.getParameter("luogo")) && SecurityLayer.checkString(request.getParameter("settore")) && SecurityLayer.checkString(request.getParameter("obiettivi")) &&
                SecurityLayer.checkString(request.getParameter("corsoStudio"))){
            
            try {
                int durata;
                if(request.getParameter("durata") == null || request.getParameter("durata").equals("")){
                    durata = 0;
                }
                else{
                    durata = SecurityLayer.checkNumeric(request.getParameter("durata"));
                }
                String titolo = request.getParameter("titolo");
                String facilitazioni = request.getParameter("facilitazioni");
                String luogo = request.getParameter("luogo");
                String settore = request.getParameter("settore");
                String obiettivi = request.getParameter("obiettivi");
                String corso = request.getParameter("corsoStudio");
                
                //restituisco i risultati della ricerca
                List<Tirocinio> risultato = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().searchTirocinio(durata, titolo, facilitazioni, luogo, settore, obiettivi, corso);
                request.setAttribute("tirocini", risultato);
                
                //restituisco tutti i parametri della ricerca per precompilare la form
                request.setAttribute("durata", durata);
                request.setAttribute("titolo", titolo);
                request.setAttribute("facilitazioni", facilitazioni);
                request.setAttribute("luogo", luogo);
                request.setAttribute("settore", settore);
                request.setAttribute("obiettivi", obiettivi);
                request.setAttribute("corsoStudi", corso);
                
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }             
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
