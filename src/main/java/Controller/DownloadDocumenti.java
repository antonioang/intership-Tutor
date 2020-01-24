/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import framework.result.FailureResult;
import framework.result.StreamResult;
import framework.security.SecurityLayer;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class DownloadDocumenti extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", (String)s.getAttribute("username"));
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        
         if (request.getParameter("nome_doc")!=null) {
             action_download(request, response);
         }
         else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
            action_error(request, response);
         }
        
    }
    
    private void action_download(HttpServletRequest request, HttpServletResponse response){
        try {
            //ottengo il nome del file dalla richiesta
            String nome_doc = request.getParameter("nome_doc");
            //creo un nuovo stream result
            StreamResult result = new StreamResult(getServletContext());
            //ottengo il file da scaricare
            File file_to_download = new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory") + File.separatorChar + nome_doc);
            //setto il content type del file
            request.setAttribute("contentType", "application/pdf");
            result.activate(file_to_download, request, response);
        } catch (IOException ex) {
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
