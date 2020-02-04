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
 * @author matti
 */
public class RichiediTirocinio extends BaseController {
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("tirocinio") != null && !request.getParameter("tirocinio").equals("0")){
            try {
                HttpSession s = SecurityLayer.checkSession(request);
                if (s!= null) {
                    request.setAttribute("username", s.getAttribute("username"));
                    request.setAttribute("tipo", (int)s.getAttribute("tipo"));
                }
                if (request.getParameter("submit_richiesta") != null) {
                    action_submit_richiesta(request, response);
                }
                else if(request.getParameter("submit_tutore") != null){
                    action_crea_tutore(request, response);
                }
                else{
                    action_default(request, response);
                }
                
                //MOSTRO IL TEMPLATE
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("richiesta_tirocinio.ftl.html", request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
            
        }
        
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Persona> tutori_universitari = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getTutoriUniversitari();
            request.setAttribute("tutori_universitari", tutori_universitari);
            Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(SecurityLayer.checkNumeric(request.getParameter("tirocinio")));
            request.setAttribute("tirocinio", tirocinio);
                       
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_submit_richiesta(HttpServletRequest request, HttpServletResponse response){
        
        try {
            Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
            Studente studente = ((BaseDataLayer)request.getAttribute("datalayer")).getStudenteDAO().getStudenteByUtente(utente.getId());
            Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocinio(SecurityLayer.checkNumeric(request.getParameter("tirocinio")));
            
            //CREO IL TIROCINIO
            RichiestaTirocinio richiesta_tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().createRichiestaTirocinio();
            
            if(SecurityLayer.checkString(request.getParameter("dottorato")) && SecurityLayer.checkString(request.getParameter("laurea")) && SecurityLayer.checkString(request.getParameter("specializzazione")) &&            
                    SecurityLayer.checkString(request.getParameter("diploma")) && SecurityLayer.checkNumber(request.getParameter("cfu")) && SecurityLayer.checkNumber(request.getParameter("tutore")) ){
                
                richiesta_tirocinio.setDottorato(request.getParameter("dottorato"));
                richiesta_tirocinio.setLaurea(request.getParameter("laurea"));
                richiesta_tirocinio.setSpecializzazione(request.getParameter("specializzazione"));
                richiesta_tirocinio.setDiploma(request.getParameter("diploma"));
                richiesta_tirocinio.setCfu(SecurityLayer.checkNumeric(request.getParameter("cfu")));
                richiesta_tirocinio.setTutoreUniversitario(SecurityLayer.checkNumeric(request.getParameter("tutore")));
                richiesta_tirocinio.setStudente(studente.getId());
                richiesta_tirocinio.setTirocinio(tirocinio.getId());
                richiesta_tirocinio.setStatoCandidatura(1);
                
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getRichiestaTirocinioDAO().addRichiestaTirocinio(richiesta_tirocinio);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "La richiesta di tirocinio non è andata a buon fine. Riprova!");
                    action_error(request, response);
                }
                else{
                    //richiesta tirocinio inserita con successo
                    response.sendRedirect("tirocinio?id="+tirocinio.getId());
                }
                
            }
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_crea_tutore(HttpServletRequest request, HttpServletResponse response){
        //CREO IL TUTORE UNIVERSITARIO
        Persona tutore_uni = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().createPersona();
        
        //validazione input responsabile tirocini
        if (SecurityLayer.checkString(request.getParameter("nome_tt")) && SecurityLayer.checkString(request.getParameter("cognome_tt")) &&
                    SecurityLayer.checkEmail(request.getParameter("email_tt")) && SecurityLayer.checkTelNum(request.getParameter("telefono_tt"))){

            try {
                tutore_uni.setNome(request.getParameter("nome_tt"));
                tutore_uni.setCognome(request.getParameter("cognome_tt"));
                tutore_uni.setEmail(request.getParameter("email_tt"));
                tutore_uni.setTelefono(request.getParameter("telefono_tt"));
                tutore_uni.setTipo(3);
                
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().addPersona(tutore_uni);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Il tutore tirocini già esistente. Riprova!");
                    action_error(request, response);
                }
                else{
                    //tutore universitario creato con successo
                    request.setAttribute("new_tutore", 1);
                    request.setAttribute("messaggio", "Tutore universitario aggiunto con successo! Puoi scegliere adesso il tutore appena creato");
                    action_default(request, response);
                }
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
        else{
            request.setAttribute("errore", "errore_validazione");
            request.setAttribute("messaggio", "I campi del tutore tirocinio non sono corretti. Riprova!");
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
