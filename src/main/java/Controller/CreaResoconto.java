/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Rapporto;
import Model.Interfaces.Studente;
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
public class CreaResoconto extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        if(request.getParameter("tirocinio") != null && !request.getParameter("tirocinio").equals("0") && request.getParameter("studente") != null && !request.getParameter("studente").equals("0")){
            if(request.getParameter("submit_resoconto") != null){
                action_crea_resoconto(request, response);
            }
            else{
                action_default(request, response);
            }
        }
        else{
            request.setAttribute("errore", "errore_parametro");
            request.setAttribute("messaggio", "Impossibile caricare la pagina, parametri necessari non presenti. Riprova!");
            action_error(request, response);
        }
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        if(SecurityLayer.checkNumber(request.getParameter("tirocinio")) && SecurityLayer.checkNumber(request.getParameter("tirocinio"))){
            try {
                //ottengo il tirocinio e lo studente dall'id
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
                int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(id_studente);
                
                //setto i dati necessari
                request.setAttribute("tirocinio", tirocinio);
                request.setAttribute("studente", studente);
                
                //mostro il template
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("crea_resoconto.ftl.html", request, response);               
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
    }
    
    private void action_crea_resoconto(HttpServletRequest request, HttpServletResponse response) {
        try {
            if(SecurityLayer.checkString(request.getParameter("giudizio")) && SecurityLayer.checkString(request.getParameter("descrizione_att")) && SecurityLayer.checkNumber(request.getParameter("ore"))){
                //ottengo il tirocinio e lo studente dall'id
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
                int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(id_studente);
                
                Rapporto resoconto = ((BaseDataLayer)request.getAttribute("datalayer")).getRapportoDAO().createRapporto();
                resoconto.setDescrizioneAtt(request.getParameter("descrizione_att"));
                resoconto.setGiudizio(request.getParameter("giudizio"));
                resoconto.setOre(SecurityLayer.checkNumeric(request.getParameter("ore")));
                resoconto.setTirocinio(tirocinio.getId());
                resoconto.setStudente(studente.getId());
                
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getRapportoDAO().addRapporto(resoconto);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Il resoconto è già esistente. Riprova!");
                    action_error(request, response);
                }
            }            
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_error(HttpServletRequest request, HttpServletResponse response) {

        if (request.getAttribute("eccezione") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("eccezione"), request, response);
        } else {
            request.setAttribute("referrer", "registrazione_azienda.ftl.html");

            (new FailureResult(getServletContext())).activate((String) request.getAttribute("messaggio"), request, response);
        }
    }

}
