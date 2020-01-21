/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import framework.result.FailureResult;
import framework.security.SecurityLayer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author matti
 */
public class UploadDocumenti extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession s = SecurityLayer.checkSession(request);
        if (s!= null) {
            request.setAttribute("username", (String)s.getAttribute("username"));
            request.setAttribute("tipo", (String)s.getAttribute("tipo"));
        }
        
        if (request.getParameter("tipo")!=null && (request.getParameter("tipo").equals("1") || request.getParameter("tipo").equals("2"))) {
            if(request.getParameter("tipo").equals("1")){
                //upload convenzione
                action_upload_convenzione(request, response);
            }
            else{
                //upload richiesta tirocinio
                action_upload_richiesta(request, response);
            }
        }
        else{
            request.setAttribute("errore", "errore_caricamento");
            request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
            action_error(request, response);
        }
    }
    
    private void action_upload_convenzione(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("azienda")!=null && !request.getParameter("azienda").equals("0")){
            try {
                //ottengo l'id dell'azienda 
                int id_azienda = SecurityLayer.checkNumeric(request.getParameter("azienda"));

                Part file = request.getPart("file");
                //controllo che il file da caricare sia un pdf
                if (file.getContentType().equals("application/pdf")) {
                    //creo un file con un nome univoco
                    File uploaded = File.createTempFile("convenzione_",  id_azienda +".pdf", new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory")));
                    InputStream is = file.getInputStream();
                    OutputStream os = new FileOutputStream(uploaded);
                    //leggo il file dall'input stream e lo scrivo nell'outputstream
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) > 0) {
                        os.write(buffer, 0, read);
                    }
                    //rimando alla servlet per la gestione delle richieste di convenzione per effettuare update dellla src del documento di convenzione
                    response.sendRedirect("richieste_convenzione?convalida=si&az="+id_azienda+"&src="+uploaded.getName());
                }
            } catch (IOException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (ServletException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
    }
    
    private void action_upload_richiesta(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("studente")!=null && !request.getParameter("studente").equals("0") && 
            request.getParameter("tirocinio")!=null && !request.getParameter("tirocinio").equals("0")){
            
            //ottengo lo studente e il tirocinio dall'id
            int id_studente = SecurityLayer.checkNumeric(request.getParameter("studente"));
            int id_tirocinio = SecurityLayer.checkNumeric(request.getParameter("tirocinio"));
            try {
                Part file = request.getPart("file");
                //controllo che il file da caricare sia un pdf
                if (file.getContentType().equals("application/pdf")) {

                        //creo un file con un nome univoco
                        File uploaded = File.createTempFile("convenzione_",  id_studente + id_tirocinio +".pdf", new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory")));
                        InputStream is = file.getInputStream();
                        OutputStream os = new FileOutputStream(uploaded);
                        //leggo il file dall'input stream e lo scrivo nell'outputstream
                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = is.read(buffer)) > 0) {
                            os.write(buffer, 0, read);
                        }
                        //rimando alla servlet per la gestione della richiesta tirocinio per aggiornare la src del documento e sbloccare il tasto accetta
                        response.sendRedirect("gestione_candidati?upload="+uploaded.getName()+"&studente="+id_studente+"&tirocinio="+id_tirocinio);

                }
            } catch (IOException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            } catch (ServletException ex) {
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
