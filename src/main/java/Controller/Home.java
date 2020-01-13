/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Interfaces.Azienda;
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
public class Home extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        try {
            TemplateResult res;
            if (s!= null) {
                request.setAttribute("username", (String) s.getAttribute("username"));
                request.setAttribute("tipo", s.getAttribute("tipo"));
                switch((int) request.getAttribute("tipo")){
                    case 0:
                        //ADMIN

                        //setto i dati necessari

                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        res.activate("homepage_admin.ftl.html", request, response);
                        break;
                    case 1:
                        //STUDENTE

                        //setto i dati necessari

                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        request.setAttribute("activeHome", "active");
                        res.activate("homepage_studente.ftl.html", request, response);
                        break;
                    case 2:
                        //AZIENDA

                        //setto i dati necessari
                        Utente utente = ((BaseDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtentebyUsername((String) request.getAttribute("username"));
                        Azienda azienda = ((BaseDataLayer)request.getAttribute("datalayer")).getAziendaDAO().getAziendaByUtente(utente.getId());
                        List<Tirocinio> tirocini = ((BaseDataLayer)request.getAttribute("datalayer")).getTirocinioDAO().getTirocini(azienda.getId());
                        request.setAttribute("tirocini", tirocini);
                        
                        //mostro il template
                        res = new TemplateResult(getServletContext());
                        request.setAttribute("activeHome", "active");
                        res.activate("homepage_azienda.ftl.html", request, response);
                        break;
                    default:
                        //default-code
                }
            }
            else{
                 //ANONIMO
                res = new TemplateResult(getServletContext());
                request.setAttribute("activeHome", "active");
                res.activate("index.ftl.html", request, response);   
            }
        } catch (TemplateManagerException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
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
