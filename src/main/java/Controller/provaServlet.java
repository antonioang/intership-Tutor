/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author matti
 */
public class provaServlet extends HttpServlet {

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
        //Ottengo la sessione (se esiste) dalla richiesta
        HttpSession sessione = request.getSession(true);
        //Ottengo il writer dalla risposta
        PrintWriter w = response.getWriter();
        //Stampo la prima parte di output
        try{
            w.println("<HTML>");
            w.println("<HEAD>");
            w.println("<TITLE>Servlet di Prova</TITLE>");
            w.println("</HEAD>");
            w.println("<BODY>");
            w.println("<H2>");
            w.println("Test Servlet e Sessione");
            w.println("</H2>");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        //Se la sessione è stata 
        if(sessione.isNew()){
            sessione.setAttribute("pagine", 1);
        }
        //ottengo il numero di pagine visualizzate dalla sessione
        int a  = ((Integer) sessione.getAttribute("pagine"));
        //incremento il numero di pagine visualizzate
        sessione.setAttribute("pagine", a+1);
        //Se hai visualizzato dieci o più pagine invalida la sessione
        if( ((Integer) sessione.getAttribute("pagine")) > 10){
            w.println("Hai visitato 10 pagine, la sessione sarà invalidata");
            sessione.invalidate();
        }
        else{
            w.println("Numero di pagine visitate: "+a);
            w.println("</BODY>");
            w.println("</HTML>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
                        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet di prova";
    }// </editor-fold>

}
