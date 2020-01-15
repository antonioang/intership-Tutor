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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class DettaglioTirocinio extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("id") != null && !request.getParameter("id").equals("0")){
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("nome_utente", s.getAttribute("username"));
                request.setAttribute("tipologia", (String)s.getAttribute("tipologia"));
            }
            if(request.getParameter("id_studente") != null && request.getParameter("action") != null){
                action_gestisci_candidato(request, response);
            }
            action_default(request, response);
        }
        else{
            request.setAttribute("errore", "errore_recupero_tirocinio");
            request.setAttribute("message", "Impossibile trovare il tirocinio");
            action_error(request, response);
        }
    }
    
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        //check input
        if(SecurityLayer.checkNumber(request.getParameter("id"))){
            try {
                //ottengo il tirocinio dall'id
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("id"));
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                request.setAttribute("tirocinio", tirocinio);
                //MOSTRO IL TEMPLATE
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("dettaglio_tirocinio.ftl.html", request, response);
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
        
    }
    
    private void action_gestisci_candidato(HttpServletRequest request, HttpServletResponse response){
        int id_studente = SecurityLayer.checkNumeric(request.getParameter("id_studente"));
        boolean action = SecurityLayer.checkBoolean(request.getParameter("action"));
        
        if(action){
            //Approva candidato
            
        }
        else{
            //Rifiuta candidato
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
