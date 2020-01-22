/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.TestDAO;
import Model.DAO.impl.BaseDataLayer;
import Model.DAO.impl.TestDAO_imp;
import Model.Interfaces.Persona;
import Model.Interfaces.Studente;
import Model.Interfaces.Test;
import Model.Interfaces.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author matti
 */
public class provaServlet extends BaseController {
    
    @Resource(name = "jdbc/herokuDB")
    private DataSource ds;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {              
        response.setContentType("text/html;charset=UTF-8");
        //Ottengo il writer dalla risposta
        PrintWriter w = response.getWriter();
        String testDB="";
        try{
            BaseDataLayer dl= (BaseDataLayer)request.getAttribute("datalayer");
            Persona responsabile_tirocini = dl.getPersonaDAO().createPersona();
            responsabile_tirocini.setNome("nomeSettato");
            w.println(responsabile_tirocini.getNome());
            //Utente ut = dl.getUtenteDAO().getUtente("mariogang", "marioflex");
            //commentino
            //w.println(ut.getEmail());
        } catch(Exception ex){
            ex.printStackTrace();
        }
         
        w.println("<HTML>");
        w.println("<HEAD>");
        w.println("<TITLE>Servlet di Prova</TITLE>");
        w.println("</HEAD>");
        w.println("<BODY>");
        w.println("<H2>");
        w.println("Test Servlet e Sessione");
        w.println("</H2>");
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword("admin");
        w.println(encryptedPassword);
        
        //Ottengo la sessione (se esiste) dalla richiesta
        HttpSession sessione = request.getSession(true);
 
        //Se la sessione è stata 
        if(sessione.isNew()){
            System.out.println("Nuova Sessione");
            sessione.setAttribute("pagine", 1);
        }
        else{
            //ottengo il numero di pagine visualizzate dalla sessione
            if(null == sessione.getAttribute("pagine") ){
                System.out.println("Attributo vuoto, c'è un errore e la sessione sarà invalidata");
                sessione.setAttribute("pagine", 1);
            }
            else{
                int a  = ((Integer) sessione.getAttribute("pagine"));
                //incremento il numero di pagine visualizzate
                sessione.setAttribute("pagine", a+1);
            }
            if(sessione.getAttribute("username") !=null){
                w.println(sessione.getAttribute("username"));
                w.println(request.getAttribute("referrer"));
                w.println("<br>");
            }
            else{
                w.println("username non presente");
                w.println("<br>");
            }
        }
        
        //Se hai visualizzato dieci o più pagine invalida la sessione
        if( ((Integer) sessione.getAttribute("pagine")) > 10){
            w.println("Hai visitato 10 pagine, la sessione sarà invalidata");
            sessione.invalidate();
        }
        else{
            w.println("Numero di pagine visitate: "+sessione.getAttribute("pagine"));
            w.println("</BODY>");
            w.println("</HTML>");
        }
    }

}
