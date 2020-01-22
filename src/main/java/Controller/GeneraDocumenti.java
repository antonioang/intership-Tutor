/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Rapporto;
import Model.Interfaces.RichiestaTirocinio;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateManagerException;
import framework.result.TemplateResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class GeneraDocumenti extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", (String)s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        if(request.getParameter("tipo") != null && (request.getParameter("tipo").equals("1") || request.getParameter("tipo").equals("2") || request.getParameter("tipo").equals("3"))){
         int tipo = SecurityLayer.checkNumeric(request.getParameter("tipo"));
            switch (tipo) {
                    //modulo di richiesta di convezione
                    case 1:
                        action_genera_richiesta_convenzione(request, response);
                        break;
                        
                    //modulo di richiesta di tirocinio   
                    case 2:
                        action_genera_richiesta_tirocinio(request, response);
                        break;
                        
                    //modulo resoconto tirocinio
                    case 3:
                        action_genera_resoconto(request, response);
                        break;
            }
        }
        else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
            action_error(request, response);
        }
        
    }
    
    private void action_genera_richiesta_convenzione(HttpServletRequest request, HttpServletResponse response){
         
         if(request.getParameter("azienda")!=null && !request.getParameter("azienda").equals("0")){
             try {
                //ottengo l'azienda dall'id
                int id_azienda = SecurityLayer.checkNumeric(request.getParameter("azienda"));
                Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(id_azienda);
                Persona responsabile_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getPersona(azienda.getRespTirocini());
                //calcolo la differenza in mesi dall'inizio alla fine della convenzione
                LocalDate inizio = azienda.getInizioConv();
                LocalDate fine = azienda.getFineConv();
                long durataInMesi = ChronoUnit.MONTHS.between(inizio, fine);
                System.out.println(durataInMesi);
                
                //setto i dati necessari 
                request.setAttribute("azienda", azienda);
                request.setAttribute("responsabile_tirocini", responsabile_tirocini);
                request.setAttribute("durata_conv", durataInMesi);
                
                //mostro il template
                TemplateResult res = new TemplateResult(getServletContext());
                res.activateNoOutline("modulo_convenzione.ftl.html", request, response);  
             } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
             } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
             }
         }
         else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
            action_error(request, response);
         }
    }
    
    private void action_genera_richiesta_tirocinio(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("studente")!=null && !request.getParameter("studente").equals("0") && 
                request.getParameter("tirocinio")!=null && !request.getParameter("tirocinio").equals("0")){
            try {
                //ottengo lo studente e il tirocinio dall'id
                int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
                int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudente(id_studente);
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(id_tirocinio);
                RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
                Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(tirocinio.getAzienda());
                LocalDate inizio = richiesta.getDataInizio().toLocalDate();
                LocalDate fine = richiesta.getDataFine().toLocalDate();
                long durataInMesi = ChronoUnit.MONTHS.between(inizio, fine);
                //setto i dati necessari 
                request.setAttribute("durata_mesi", durataInMesi);
                request.setAttribute("studente", studente);
                request.setAttribute("tirocinio", tirocinio);
                request.setAttribute("richiesta_tirocinio", richiesta);
                request.setAttribute("azienda", azienda);
                //mostro il template
                TemplateResult res = new TemplateResult(getServletContext());
                res.activateNoOutline("modulo_richiesta_tirocinio.ftl.html", request, response);  
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
            
        }
        else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
            action_error(request, response);
        }
    }
    
    private void action_genera_resoconto(HttpServletRequest request, HttpServletResponse response){
         if(request.getParameter("studente")!=null && !request.getParameter("studente").equals("0") && 
                request.getParameter("tirocinio")!=null && !request.getParameter("tirocinio").equals("0")){
            
             try {
                 //ottengo il resoconto dallo studente e dal tirocinio
                 int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
                 int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
                 Rapporto resoconto = ((BaseDataLayer)request.getAttribute("datalayer")).getRapportoDAO().getRapporto(id_studente, id_tirocinio);
                 RichiestaTirocinio richiesta_tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, id_studente);
                 //setto i dati necessari
                 request.setAttribute("resoconto", resoconto);
                 request.setAttribute("richiesta_tirocinio", richiesta_tirocinio);
                 //mostro il template
                 TemplateResult res = new TemplateResult(getServletContext());
                 res.activateNoOutline("modulo_resoconto.ftl.html", request, response);  
             } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
             } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
             }
         }
         else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
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
