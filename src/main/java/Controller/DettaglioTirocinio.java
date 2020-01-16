/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.RichiestaTirocinio;
import Model.Interfaces.Studente;
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
public class DettaglioTirocinio extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameter("id") != null && !request.getParameter("id").equals("0")){
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("username", s.getAttribute("username"));
                request.setAttribute("tipo", (int)s.getAttribute("tipo"));
            }
            if(request.getParameter("id_studente") != null && request.getParameter("action") != null){
                action_gestisci_candidato(request, response);
            }
            else if(request.getParameter("visibile") != null){
                action_change_visibile(request, response);
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
                //ottengo il tirocinio dall'id e la lista degli studenti
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("id"));
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                request.setAttribute("tirocinio", tirocinio);
                
                //mostro la lista degli studenti candidati, rifiutati o in sospeso solo se l'utente è un'azienda o un amministratore
                if(request.getAttribute("tipo") !=null){
                    if((int) request.getAttribute("tipo") == 2 || (int) request.getAttribute("tipo") == 3){
                        List<Studente> accettati = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioAccettato(id_tirocinio);
                        List<Studente> rifiutati = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioRifiutato(id_tirocinio);
                        List<Studente> sospeso = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioSospeso(id_tirocinio);
                        request.setAttribute("studenti_candidati", accettati);
                        request.setAttribute("studenti_rifiutati", rifiutati);
                        request.setAttribute("studenti_sospeso", sospeso);
                    }
                }
                
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
        try {
            if(SecurityLayer.checkNumber(request.getParameter("id")) && SecurityLayer.checkNumber(request.getParameter("id"))){
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("id"));
                int id_studente = SecurityLayer.checkNumeric(request.getParameter("id_studente"));
                RichiestaTirocinio rt = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);

                boolean action = false;
                //controllo che il valore del parametro action sia effettivamente un booleano
                if(SecurityLayer.checkBoolean(request.getParameter("action"))){
                    action = SecurityLayer.stringToBoolean(request.getParameter("action"));
                }
                else{
                    request.setAttribute("errore", "errore_azione_candidato");
                    request.setAttribute("messaggio", "Errore durante l'accettazione o la rimozione del candidato. Riprovare");
                    action_error(request, response);
                }

                if(action){
                    //Approva candidato
                    ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updRichiestaTirocinioStato(rt.getId(), 1);
                    response.sendRedirect("tirocinio?id="+id_tirocinio);
                }
                else{
                    //Rifiuta candidato
                    ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updRichiestaTirocinioStato(rt.getId(), 2);
                    response.sendRedirect("tirocinio?id="+id_tirocinio);
                }
            }
            else{
                request.setAttribute("errore", "errore_parametro_errato");
                request.setAttribute("messaggio", "Errore durante il caricamento della pagina. Riprovare");
                action_error(request, response);
            }
            
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
        
    }
    
    private void action_change_visibile(HttpServletRequest request, HttpServletResponse response){
        
        if(SecurityLayer.checkString(request.getParameter("visibile")) && SecurityLayer.checkNumber(request.getParameter("id")) ){
            try {
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("id"));
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().updTirocinioVisibile(id_tirocinio, !tirocinio.getVisibile());
                response.sendRedirect("tirocinio?id="+id_tirocinio);
                
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (IOException ex) {
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
