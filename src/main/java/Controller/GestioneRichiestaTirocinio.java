/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Persona;
import Model.Interfaces.RichiestaTirocinio;
import Model.Interfaces.Studente;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.time.LocalDate;
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
public class GestioneRichiestaTirocinio extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        if(request.getParameter("tirocinio") != null && !request.getParameter("tirocinio").equals("0") && request.getParameter("studente") != null && !request.getParameter("studente").equals("0")){
            try {
                if(request.getParameter("accetta") != null){
                    action_accetta_richiesta(request, response);
                }
                else if(request.getParameter("rifiuta") != null){
                    action_rifiuta_richiesta(request, response);
                }
                else if(request.getParameter("imposta_date") != null){
                    action_imposta_date(request, response);
                }
                else if(request.getParameter("src") !=null){
                    action_update_doc_richiesta(request, response);
                }
                else{
                    action_default(request, response);
                }                
                //mostro il template
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("gestione_richiesta.ftl.html", request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }

        
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        try {
            //ottengo i dati dello studente, i dati del tutore universitario, e i dati della richiesta
            int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
            int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
            RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
            Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(id_studente);
            Persona tutore_uni = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getPersona(richiesta.getTutoreUniversitario());
            
            //setto i dati
            request.setAttribute("richiesta_tirocinio", richiesta);
            request.setAttribute("studente", studente);
            request.setAttribute("tutore_uni", tutore_uni);
            
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_imposta_date(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("data_inizio") != null && request.getParameter("data_fine") != null){
            try {
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
                int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
                RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
                LocalDate data_inizio = SecurityLayer.checkDate(request.getParameter("data_inizio"));
                LocalDate data_fine = SecurityLayer.checkDate(request.getParameter("data_fine"));
                
                int update = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updDataInizioDataFine(data_inizio, data_fine, richiesta.getId());
                if(update != 1){
                    request.setAttribute("errore", "errore_aggiornamento");
                    request.setAttribute("messaggio", "L'inserimento delle date inizio e fine della richiesta di tirocinio non è andata a buon fine. Riprova!");
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
    }
    
    private void action_update_doc_richiesta(HttpServletRequest request, HttpServletResponse response){
        try {
            int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
            int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
            String src = request.getParameter("src");
            RichiestaTirocinio richiesta = ((BaseDataLayer) request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
            int update = ((BaseDataLayer) request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updDocumentoRichiestaTirocinio(richiesta.getId(), src);
            if(update != 1){
                request.setAttribute("errore", "errore_aggiornamento");
                request.setAttribute("messaggio", "L'inserimento del documento di candidatura non è andato a buon fine. Riprova!");
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
    
    private void action_accetta_richiesta(HttpServletRequest request, HttpServletResponse response){
        try {
            int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
            int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
            RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
            ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updRichiestaTirocinioStato(richiesta.getId(), 2);
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_rifiuta_richiesta(HttpServletRequest request, HttpServletResponse response){
        try {
            int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
            int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
            RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
            ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().updRichiestaTirocinioStato(richiesta.getId(), 4);
        } catch (DataLayerException ex) {
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
