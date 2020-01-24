/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.RichiestaTirocinio;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;
import Model.Interfaces.Valutazione;
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
            if(request.getParameter("visibile") != null){
                action_change_visibile(request, response);
            }
            else if(request.getParameter("valutazione") != null){
                action_valuta_azienda(request, response);
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
                        //admin e azienda
                        List<Studente> accettati = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioAccettato(id_tirocinio);
                        List<Studente> rifiutati = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioRifiutato(id_tirocinio);
                        List<Studente> sospeso = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudentiByTirocinioSospeso(id_tirocinio);
                       
                        //setto i dati necessari
                        request.setAttribute("studenti_candidati", accettati);
                        request.setAttribute("studenti_rifiutati", rifiutati);
                        request.setAttribute("studenti_sospeso", sospeso);
                    }
                    else{
                        //studente
                        Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername( (String) request.getAttribute("username"));
                        Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
                        RichiestaTirocinio richiesta = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().getRichiestaTirocinio(id_tirocinio, studente.getId());
                        request.setAttribute("studente", studente);
                        request.setAttribute("richiesta_tirocinio", richiesta);
                        //controlla lo stato
                        switch (richiesta.getStatoCandidatura()) {
                                //tirocinio richiesto
                                case 1:
                                    request.setAttribute("stato_tirocinio", 1);
                                    break;

                                //tirocinio attivo   
                                case 2:
                                    request.setAttribute("stato_tirocinio", 2);
                                    break;

                                //tirocinio concluso
                                case 3:
                                    request.setAttribute("stato_tirocinio", 3);
                                    break;
                                //tirocinio rifiutato
                                case 4:
                                    request.setAttribute("stato_tirocinio", 4);
                                    break;    
                        }
                    }
                }
                else{
                    //anonimo
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
    
    private void action_valuta_azienda(HttpServletRequest request, HttpServletResponse response){
        if(SecurityLayer.checkNumber(request.getParameter("valutazione"))){
            try {
                int rating = SecurityLayer.checkNumeric(request.getParameter("valutazione"));
                Valutazione valutazione = ((BaseDataLayer)request.getAttribute("datalayer")).getValutazioneDAO().createValutazione();
                Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(SecurityLayer.checkNumeric(request.getParameter("id")));
                Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAzienda(tirocinio.getAzienda());
                Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String)request.getAttribute("username"));
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
                
                valutazione.setAzienda(azienda.getId());
                valutazione.setStudente(studente.getId());
                valutazione.setPunteggio(rating);
                
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getValutazioneDAO().addValutazione(valutazione);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "L'inserimento della valutazione non è andato a buon fine. Riprova!");
                    action_error(request, response);
                }
                else{
                    //valutazione inserita con successo
                    request.setAttribute("valutazione", rating);
                    action_default(request, response);
                }
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
        else{
            request.setAttribute("errore", "errore_parametro");
            request.setAttribute("messaggio", "Il parametro non è del formato corretto. Riprova!");
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
