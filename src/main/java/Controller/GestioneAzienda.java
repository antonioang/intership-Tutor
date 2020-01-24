/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
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
public class GestioneAzienda extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessione = SecurityLayer.checkSession(request);
        if(sessione != null){
            request.setAttribute("username", sessione.getAttribute("username"));
            request.setAttribute("tipo", sessione.getAttribute("tipo"));
        }
        
        if(request.getParameter("id") != null && !request.getParameter("id").equals("0")){
            try {
                if(request.getParameter("action") != null){
                    String azione = request.getParameter("action");
                    if(azione.equals("accetta")){
                        System.out.println("porto la nonna");
                        action_approva_azienda(request, response);
                    }
                    else if(azione.equals("rifiuta")){
                        action_rifiuta_azienda(request, response);
                    }
                }
                else if(request.getParameter("src") !=null){
                    action_upd_doc_convenzione(request, response);
                }
                else{
                    action_default(request, response);
                }
                
                //mostro il template
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("gestione_azienda.ftl.html", request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
    }
        
    private void action_approva_azienda (HttpServletRequest request, HttpServletResponse response){
        try {
            //ottengo l'azienda dall'id
            System.out.println("dentro");
            int id_azienda = SecurityLayer.checkNumeric(request.getParameter("id"));
            ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().updateAziendaStato(id_azienda, 1);
            response.sendRedirect("home");
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_rifiuta_azienda (HttpServletRequest request, HttpServletResponse response){
        try {
            //ottengo l'azienda dall'id
            int id_azienda = SecurityLayer.checkNumeric(request.getParameter("id"));
            Azienda azienda = ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id_azienda);
            ((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().delUtente(azienda.getUtente());
            ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().delAzienda(id_azienda);
            response.sendRedirect("home");
            
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_upd_doc_convenzione(HttpServletRequest request, HttpServletResponse response){
        try {
            int id_azienda = SecurityLayer.checkNumeric(request.getParameter("id"));
            String src = request.getParameter("src");
            int update = ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().updAziendaDocumento(id_azienda, src);
            if(update != 1){
                request.setAttribute("errore", "errore_aggiornamento");
                request.setAttribute("messaggio", "L'inserimento del documento di convenzione non è andato a buon fine. Riprova!");
                action_error(request, response);
            }
            else{
                action_default(request, response);
            }
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_default (HttpServletRequest request, HttpServletResponse response){
        try {
            //ottengo l'azienda dall'id
            int id_azienda = SecurityLayer.checkNumeric(request.getParameter("id"));
            Azienda azienda = ((BaseDataLayer) request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id_azienda);
            //setto i dati necessari
            request.setAttribute("azienda", azienda);
        } catch (DataLayerException ex) {
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
