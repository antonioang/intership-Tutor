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
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

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
            request.setAttribute("tipo", (int)s.getAttribute("tipo"));
        }
        if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
            try {
                int tipo = 0;
                int id_azienda = 0;
                int id_tirocinio = 0;
                int id_studente = 0;
                FileItem fileToUpload = null;
                
                //Vecchio metodo per estrarre le parti dalle form multipart
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
                Iterator<FileItem> iter = items.iterator();
                
                while (iter.hasNext()) {
                    FileItem item = iter.next();

                    if (item.isFormField() && item.getFieldName().equals("tipo")) {
                        tipo = SecurityLayer.checkNumeric(item.getString());
                    }
                    if (item.isFormField() && item.getFieldName().equals("azienda")) {
                        id_azienda = SecurityLayer.checkNumeric(item.getString());
                    }
                    if (item.isFormField() && item.getFieldName().equals("tirocinio")) {
                        id_tirocinio = SecurityLayer.checkNumeric(item.getString());
                    }
                    if (item.isFormField() && item.getFieldName().equals("studente")) {
                        id_studente = SecurityLayer.checkNumeric(item.getString());
                    }
                    if(item.getFieldName().equals("file") && item.getContentType().equals("application/pdf")){
                        System.out.println("trovato il file");
                        System.out.println(item.getContentType());
                        fileToUpload = item;
                    }
                }
                
                if (tipo == 1 || tipo == 2 ) {
                    if(tipo == 1){
                        //upload convenzione
                        action_upload_convenzione(request, response, id_azienda, fileToUpload);
                    }
                    else if(tipo == 2){
                        //upload richiesta tirocinio
                        action_upload_richiesta(request, response, id_studente, id_tirocinio, fileToUpload);
                    }
                }
                else{
                    request.setAttribute("errore", "errore_caricamento");
                    request.setAttribute("message", "Impossibile caricare la pagina! Riprova");
                    action_error(request, response);
                }
            } catch (FileUploadException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
        }
    }
    
    private void action_upload_convenzione(HttpServletRequest request, HttpServletResponse response, int id_azienda, FileItem fileToUpload){
            try {
                if(fileToUpload != null && id_azienda !=0){
                    //Controllo che esista la cartella per l'upload
                    File uploadDir = new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory"));
                    //Se non esiste la creo
                    if (!uploadDir.exists()) uploadDir.mkdir();
                    //Creo il file nel percorso specificato
                    File uploaded = new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory")+ File.separatorChar +"convenzione_" + id_azienda +".pdf");
                    //Ottengo l'input stream dall'elemento della form
                    InputStream is = fileToUpload.getInputStream();
                    //Ottengo l'output stream dal file creato in precedenza
                    OutputStream os = new FileOutputStream(uploaded);
                    //Leggo il file dall'input stream e lo scrivo nell'outputstream
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) > 0) {
                        os.write(buffer, 0, read);
                    }
                    //Rimando alla servlet per la gestione delle richieste di convenzione per effettuare update dellla src del documento di convenzione
                    response.sendRedirect("gestione_azienda?id="+id_azienda+"&src="+uploaded.getName());
                }  
            } catch (IOException ex) {
                request.setAttribute("eccezione", ex);
                action_error(request, response);
            }
    }
    
    private void action_upload_richiesta(HttpServletRequest request, HttpServletResponse response, int id_studente, int id_tirocinio, FileItem fileToUpload){
        try {
                if(fileToUpload != null && id_studente != 0 && id_tirocinio !=0){
                    //Controllo che esista la cartella per l'upload
                    File uploadDir = new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory"));
                    //Se non esiste la creo
                    if (!uploadDir.exists()) uploadDir.mkdir();
                    //Creo il file nel percorso specificato
                    File uploaded = new File(getServletContext().getRealPath("") + File.separatorChar + getServletContext().getInitParameter("uploads.directory")+ File.separatorChar +"candidatura_" + id_studente + id_tirocinio +".pdf");
                    //Ottengo l'input stream dall'elemento della form
                    InputStream is = fileToUpload.getInputStream();
                    //Ottengo l'output stream dal file creato in precedenza
                    OutputStream os = new FileOutputStream(uploaded);
                    //Leggo il file dall'input stream e lo scrivo nell'outputstream
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) > 0) {
                        os.write(buffer, 0, read);
                    }
                    log("upload: "+uploaded.getName());
                    //Rimando alla servlet per la gestione delle richieste di convenzione per effettuare update dellla src del documento di convenzione
                    response.sendRedirect("gestione_richiesta?tirocinio="+id_tirocinio+"&studente="+id_studente+"&src="+uploaded.getName());
                }  
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
