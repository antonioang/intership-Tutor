/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;
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
 * @author Mattia Lenza
 */
public class Profilo extends BaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", (String) s.getAttribute("username"));
            request.setAttribute("tipo", s.getAttribute("tipo"));
            
            if (request.getParameter("modifica_profilo") != null) {
                action_modifica_profilo(request, response);
            }
            else{
                action_default(request, response);
            }
        }
        else{
            //sessione nulla
            request.setAttribute("errore", "sessione_invalida");
            request.setAttribute("title", "Sessione invailda");
            request.setAttribute("messaggio", "Non è stato possibile caricare la pagina. Riprova.");
            action_error(request, response);
        }
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        try {

            //OTTENGO L'OGGETTO UTENTE
            Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
            if(utente == null){
                request.setAttribute("errore", "errore_utente_non_presente");
                request.setAttribute("messaggio", "Utente non presente, effettuare il Login");
                action_error(request, response);
            }

            //SETTO IL PARAMETRO A SECONDA SE L'UTENTE CORRISPONDE AD UNO STUDENTE O AD UN'AZIENDA
            if((int) request.getAttribute("tipo") == 2){
                Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
                Persona responsabile_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getPersona(azienda.getRespTirocini());
                float valutazione = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getValutazioneAzienda(azienda.getId());
                
                request.setAttribute("azienda", azienda);
                request.setAttribute("utente", utente);
                request.setAttribute("responsabile_tirocini", responsabile_tirocini);
                request.setAttribute("valutazione", valutazione);
            }
            else if((int) request.getAttribute("tipo") == 1){
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
                List<Tirocinio> tirocini_sospeso = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniByStatoRichieste(studente.getId(), 1);
                List<Tirocinio> tirocini_attivi = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniByStatoRichieste(studente.getId(), 2);
                List<Tirocinio> tirocini_conclusi = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirociniByStatoRichieste(studente.getId(), 3);
                request.setAttribute("tirocini_sospesi", tirocini_sospeso.size());
                request.setAttribute("tirocini_attivi", tirocini_attivi.size());
                request.setAttribute("tirocini_conclusi", tirocini_conclusi.size());
                request.setAttribute("studente", studente);
                request.setAttribute("utente", utente);
            }
            else if((int) request.getAttribute("tipo") == 0){
                //ADMIN
            }
            else{
                request.setAttribute("errore", "errore_ottenimento_dati");
                request.setAttribute("messaggio", "Errore durante il recupero dei dati. Riprovare");
                action_error(request, response);
            }
            
            //MOSTRO IL TEMPLATE
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("profilo.ftl.html", request, response);

        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_modifica_profilo(HttpServletRequest request, HttpServletResponse response){
        if((int) request.getAttribute("tipo") == 1){
            try {
                //SE é UNO STUDENTE
                Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
                Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
                //validazione input dati dell'utente
                if(SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkEmail(request.getParameter("email"))){
                    utente.setUsername(request.getParameter("username"));
                    utente.setEmail(request.getParameter("email"));
                    utente.setTipo(1);
                    
                    //Aggiorno l'utente nel DB
                    ((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
                }
                else{
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I campi inseriti non sono corretti. Riprova!");
                    action_error(request, response);
                }
                
                //validazione input dati dello studente
                if (SecurityLayer.checkString(request.getParameter("nome")) && SecurityLayer.checkString(request.getParameter("cognome")) &&
                    SecurityLayer.checkString(request.getParameter("codice_fiscale")) && SecurityLayer.checkBoolean(request.getParameter("handicap")) &&
                    SecurityLayer.isDate(request.getParameter("data_nascita")) && SecurityLayer.checkString(request.getParameter("citta_nascita")) &&
                    SecurityLayer.checkString(request.getParameter("provincia_nascita")) && SecurityLayer.checkString(request.getParameter("citta_residenza")) &&
                    SecurityLayer.checkString(request.getParameter("cap_residenza")) && SecurityLayer.checkString(request.getParameter("provincia_residenza")) &&
                    SecurityLayer.checkString(request.getParameter("telefono")) && SecurityLayer.checkString(request.getParameter("corso_laurea")))  {

                    //setto i valori nell'oggetto studente
                    studente.setNome(request.getParameter("nome"));
                    studente.setCognome(request.getParameter("cognome"));
                    studente.setCodFiscale(request.getParameter("codice_fiscale"));
                    studente.setDataNascita(SecurityLayer.checkDate(request.getParameter("data_nascita")));
                    studente.setCittaNascita(request.getParameter("citta_nascita"));
                    studente.setProvinciaNascita(request.getParameter("provincia_nascita"));
                    studente.setCittaResidenza(request.getParameter("citta_residenza"));
                    studente.setCapResidenza(Integer.parseInt(request.getParameter("cap_residenza")));
                    studente.setProvinciaResidenza(request.getParameter("provincia_residenza"));
                    studente.setTelefono(request.getParameter("telefono"));
                    studente.setCorsoLaurea(request.getParameter("corso_laurea"));
                    studente.setHandicap(Boolean.valueOf(request.getParameter("handicap")));
                    studente.setUtente(utente.getId());
                    
                    //Inserisco lo studente nel DB
                    ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().storeStudente(studente);
                    response.sendRedirect("profilo");
                }
                else{
                    //campi in input non validi
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I campi inseriti non sono corretti. Riprova!");
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
        else if((int) request.getAttribute("tipo") == 2){
            try {
                //SE é UN'AZIENDA
                Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
                Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
                Persona responsabile_tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getPersona(azienda.getRespTirocini());
                
                //validazione input dati dell'utente
                if(SecurityLayer.checkString(request.getParameter("username")) && SecurityLayer.checkEmail(request.getParameter("email"))){
                    utente.setUsername(request.getParameter("username"));
                    utente.setEmail(request.getParameter("email"));
                    utente.setTipo(1);
                    
                    //Aggiorno l'utente nel DB
                    ((BaseDataLayer) request.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
                }
                else{
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I campi inseriti non sono corretti. Riprova!");
                    action_error(request, response);
                }
                
                //validazione input dati responsabile_tirocini
                if (SecurityLayer.checkString(request.getParameter("nome_rt")) && SecurityLayer.checkString(request.getParameter("cognome_rt")) &&
                        SecurityLayer.checkEmail(request.getParameter("email_rt")) && SecurityLayer.checkTelNum(request.getParameter("telefono_rt"))){

                    responsabile_tirocini.setNome(request.getParameter("nome_rt"));
                    responsabile_tirocini.setCognome(request.getParameter("cognome_rt"));
                    responsabile_tirocini.setEmail(request.getParameter("email_rt"));
                    responsabile_tirocini.setTelefono(request.getParameter("telefono_rt"));
                    
                    //Aggiorno il responsabile tirocini nel DB
                    ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().storePersona(responsabile_tirocini);
                    
                } else {
                    request.setAttribute("errore", "errore_validazione");
                    request.setAttribute("messaggio", "I campi del responsabile tirocini non sono corretti. Riprova!");
                    action_error(request, response);
                }
                
                //validazione input dati azienda
                if (SecurityLayer.checkString(request.getParameter("ragione_sociale")) && SecurityLayer.checkString(request.getParameter("indirizzo")) &&
                        SecurityLayer.checkString(request.getParameter("citta")) && SecurityLayer.checkCap(request.getParameter("cap")) &&
                        SecurityLayer.checkString(request.getParameter("provincia")) && SecurityLayer.checkString(request.getParameter("rappresentante_legale")) &&
                        SecurityLayer.checkString(request.getParameter("piva")) && SecurityLayer.checkString(request.getParameter("foro_competente")) &&
                        SecurityLayer.checkString(request.getParameter("tematica")) && SecurityLayer.checkString(request.getParameter("corso_studio")) &&
                        SecurityLayer.isDate(request.getParameter("inizio_conv")) && SecurityLayer.isDate(request.getParameter("fine_conv")) && 
                        SecurityLayer.checkString(request.getParameter("nome")) ){
                        
                    azienda.setNome(request.getParameter("nome"));
                    azienda.setRagioneSociale(request.getParameter("ragione_sociale"));
                    azienda.setIndirizzo(request.getParameter("indirizzo"));
                    azienda.setCitta(request.getParameter("citta"));
                    azienda.setCap(SecurityLayer.checkNumeric(request.getParameter("cap")));
                    azienda.setProvincia(request.getParameter("provincia"));
                    azienda.setRapprLeg(request.getParameter("rappresentante_legale"));
                    azienda.setPiva(request.getParameter("piva"));
                    azienda.setForoCompetente(request.getParameter("foro_competente"));
                    azienda.setTematica(request.getParameter("tematica"));
                    azienda.setCorsoStudi(request.getParameter("corso_studio"));
                    azienda.setInizioConv(SecurityLayer.checkDate(request.getParameter("inizio_conv")));
                    azienda.setFineConv(SecurityLayer.checkDate(request.getParameter("fine_conv")));
                    azienda.setRespTirocini(responsabile_tirocini.getId());
                    azienda.setUtente(utente.getId());
                    
                    //Aggiorno l'azienda nel DB
                    ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().storeAzienda(azienda);
                    response.sendRedirect("profilo");
                }
                
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
