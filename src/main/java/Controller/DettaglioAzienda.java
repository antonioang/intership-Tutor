/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Tirocinio;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class DettaglioAzienda extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("id") != null && !request.getParameter("id").equals("0")){
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("username", s.getAttribute("username"));
                request.setAttribute("tipo", (int)s.getAttribute("tipo"));
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
        try {
            //Ottengo l'azienda, la sua valutazione e la lista dei tirocini
            int id_azienda =Integer.parseInt(request.getParameter("id"));
            Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id_azienda);
            List<Tirocinio> tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocini(azienda.getId());
            float valutazione = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getValutazioneAzienda(id_azienda);
            
            request.setAttribute("valutazione", valutazione);
            request.setAttribute("azienda", azienda);
            request.setAttribute("tirocini", tirocini);
            
            //MOSTRO IL TEMPLATE
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("dettaglio_azienda.ftl.html", request, response);
            
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
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
