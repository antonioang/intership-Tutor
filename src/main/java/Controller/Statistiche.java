/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class Statistiche extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        
        action_default(request, response);
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        try {
            //ottengo i dati
            HashMap<Persona, Integer> best_tutori_tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getBestTutoriTirocinio();
            HashMap<Persona, Integer> best_tutori_uni = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getBestTutoriUni();
            HashMap<Azienda, Integer> aziende_piu_tirocinanti = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendeConPiuTirocinanti();
            HashMap<Azienda, Float> best_aziende = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getBestAziende();
            HashMap<Azienda, Float> worst_aziende = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getWorstAziende();
            
            //setto i dati
            request.setAttribute("best_tutori_tirocinio", best_tutori_tirocinio);
            request.setAttribute("best_tutori_uni", best_tutori_uni);
            request.setAttribute("aziende_piu_tirocinanti", aziende_piu_tirocinanti);
            request.setAttribute("best_aziende", best_aziende);
            request.setAttribute("worst_aziende", worst_aziende);
            
            //mostro il template
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("statistiche.ftl.html", request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
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
