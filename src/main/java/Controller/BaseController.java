/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO.impl.BaseDataLayer;
import framework.data.DataLayerException;
import framework.result.FailureResult;
import framework.security.SecurityLayer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author matti
 */
public abstract class BaseController extends HttpServlet {
    
    @Resource(name = "jdbc/herokuDB")
    private DataSource ds;
    
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException;
    
     private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //WARNING: never declare DB-related objects including references to Connection and Statement (as our data layer)
        //as class variables of a servlet. Since servlet instances are reused, concurrent requests may conflict on such
        //variables leading to unexpected results. To always have different connections and statements on a per-request
        //(i.e., per-thread) basis, declare them in the doGet, doPost etc. (or in methods called by them) and 
        //(possibly) pass such variables through the request.
            BaseDataLayer dl;
        try {
            // Obtain our environment naming context
//            Context initCtx;
//            initCtx = new InitialContext();
//            Context envCtx = (Context) initCtx.lookup("java:comp/env");
//            // Look up our data source
//            ds = (DataSource) envCtx.lookup("jdbc/herokuDB");      
            dl = new BaseDataLayer(ds);
            dl.init();
            request.setAttribute("datalayer", dl);
            processRequest(request, response);
            HttpSession s = SecurityLayer.checkSession(request);
            if (s!= null) {
                request.setAttribute("nome_utente", (String)s.getAttribute("username"));
                request.setAttribute("tipologia", (String)s.getAttribute("tipologia"));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace(); //debug
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        } catch (DataLayerException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        } catch (NamingException ex) {
//            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
//        }
           
    }
     
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

}
