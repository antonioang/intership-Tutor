/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;
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
public class CreaTirocinio extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", (String) s.getAttribute("username"));
            request.setAttribute("tipo", s.getAttribute("tipo"));
        }

        if(request.getParameter("submit_tirocinio") != null){
            action_crea_tirocinio(request, response);
        }
        else if(request.getParameter("submit_tutore") != null){
            action_crea_tutore(request, response);
        }
        else{
            try {
                action_default(request, response);
            } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
        
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException{
        try {
            Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
            Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
            List<Persona> lista = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().getTutoriTirocinio();
            request.setAttribute("tutori_tirocinio", lista);
            
            //MOSTRO IL TEMPLATE
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("crea_tirocinio.ftl.html", request, response);
        } catch (DataLayerException ex) {
            request.setAttribute("eccezione", ex);
            action_error(request, response);
        }
    }
    
    private void action_crea_tirocinio(HttpServletRequest request, HttpServletResponse response){
        
        try {
            Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
            Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
            
            //CREO IL TIROCINIO
            Tirocinio tirocinio = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().createTirocinio();
            
            //validazione input tirocinio
            if(SecurityLayer.checkString(request.getParameter("luogo")) && SecurityLayer.checkString(request.getParameter("settore")) && SecurityLayer.checkString(request.getParameter("orari")) &&
                    SecurityLayer.checkNumber(request.getParameter("durata")) && SecurityLayer.checkString(request.getParameter("titolo")) && SecurityLayer.checkString(request.getParameter("obiettivo")) &&
                    SecurityLayer.checkString(request.getParameter("modalita")) && SecurityLayer.checkString(request.getParameter("facilitazioni"))){
                
                tirocinio.setLuogo(request.getParameter("luogo"));
                tirocinio.setSettore(request.getParameter("settore"));
                tirocinio.setOrari(request.getParameter("orari"));
                tirocinio.setDurata(Integer.parseInt(request.getParameter("durata")));
                tirocinio.setTitolo(request.getParameter("titolo"));
                tirocinio.setObiettivo(request.getParameter("obiettivo"));
                tirocinio.setModalita(request.getParameter("modalita"));
                tirocinio.setFacilitazioni(request.getParameter("facilitazioni"));
                tirocinio.setAzienda(azienda.getId());
                tirocinio.setTutoreTirocinio(Integer.parseInt(request.getParameter("tutore")));
                tirocinio.setVisibile(true);
                System.out.println(request.getParameter("tutore"));
                
                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().addTirocinio(tirocinio);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Il tirocinio è già esistente. Riprova!");
                    action_error(request, response);
                }
                else{
                    //Tirocinio aggiunto con successo
                    response.sendRedirect("home");
                }
            }
            else{
                request.setAttribute("errore", "errore_validazione");
                request.setAttribute("messaggio", "I campi del tirocinio non sono corretti. Riprova!");
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
    
    private void action_crea_tutore(HttpServletRequest request, HttpServletResponse response){
        //CREO IL TUTORE DEL TIROCINIO
        Persona tutore = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().createPersona();

        //validazione input responsabile tirocini
        if (SecurityLayer.checkString(request.getParameter("nome_tt")) && SecurityLayer.checkString(request.getParameter("cognome_tt")) &&
                    SecurityLayer.checkEmail(request.getParameter("email_tt")) && SecurityLayer.checkTelNum(request.getParameter("telefono_tt"))){

            try {
                tutore.setNome(request.getParameter("nome_tt"));
                tutore.setCognome(request.getParameter("cognome_tt"));
                tutore.setEmail(request.getParameter("email_tt"));
                tutore.setTelefono(request.getParameter("telefono_tt"));
                tutore.setTipo(2);

                int insert = ((BaseDataLayer)request.getAttribute("datalayer")).getPersonaDAO().addPersona(tutore);
                if(insert != 1){
                    request.setAttribute("errore", "errore_inserimento");
                    request.setAttribute("messaggio", "Il tutore tirocinio già esistente. Riprova!");
                    action_error(request, response);
                }
                else{
                    //tutore tirocinio creato con successo
                    request.setAttribute("new_tutore", 1);
                    request.setAttribute("messaggio", "Tutore tirocinio aggiunto con successo! Puoi scegliere adesso il tutore appena creato");
                    action_default(request, response);
                }
            } catch (DataLayerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (TemplateManagerException ex) {
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
