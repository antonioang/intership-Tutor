/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import Model.Impl.Test_Impl;
import Model.Impl.Utente_imp;
import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
import Model.Interfaces.Test;
import Model.Interfaces.Utente;
import data.proxy.TestProxy;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.result.TemplateResult;
import framework.result.TemplateManagerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Antonio
 */
public class ServletProvaTemplate extends HttpServlet {
    
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
        
        TemplateResult temp= new TemplateResult(getServletContext());
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ServletProvaTemplate</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ServletProvaTemplate at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            String temporary="Test Template";
            request.setAttribute("Test", temporary);
//            request.setAttribute("logged", "si");
//            request.setAttribute("tipologia", "azienda");
//            request.setAttribute("activeAziende", "active");

            BaseDataLayer dl= new BaseDataLayer(ds);
            try {
                
                dl.init();
                Studente st = dl.getStudenteDAO().getStudente(1);
                request.setAttribute("studente", st);
                Azienda az=dl.getAziendaDAO().getAzienda(1);
                request.setAttribute("azienda", az);
                List aziende_convenzionate=new ArrayList();
                request.setAttribute("aziende_convenzionate",aziende_convenzionate);
                List tirocini=new ArrayList();
                request.setAttribute("tirocini",tirocini);
               
                
            } catch (DataLayerException ex) {
                Logger.getLogger(ServletProvaTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            temp.activateNoOutline("modulo_resoconto.ftl.html",request,response);
        } catch(TemplateManagerException ex){
            Logger.getLogger(FailureResult.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletProvaTemplate.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Short description";
    }// </editor-fold>

}
