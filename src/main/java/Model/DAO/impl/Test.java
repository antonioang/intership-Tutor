/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import framework.data.DataBase;
import framework.data.DataLayerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
public class Test {
    private static final String DBtest=" SELECT * FROM test";
    
    
    public static String testDB() throws SQLException{
        String test="e no eh";
        
        try {
            
            Connection connection = DataBase.getConnection();
            PreparedStatement ps =connection.prepareStatement(DBtest);
            ResultSet rset=ps.executeQuery();
            if(rset.next()){
                test=rset.getString("prova1");
            }
            
            } catch (DataLayerException ex){
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        return test;
        
    }
    
}
