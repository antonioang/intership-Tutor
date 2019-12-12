/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Antonio
 */
public class DataBase {
    
    private static DataSource ds;
    
    static{
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/herokuDB");
        } catch (NamingException ex) {
          Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "ERRORE INIZIALIZZAZIONE DATABASE", ex);
        }
    }
    
    public static Connection getConnection() throws DataLayerException {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            throw new DataLayerException("ERRORE CONNESSIONE DATABASE", ex);
        }
    }
    
}
