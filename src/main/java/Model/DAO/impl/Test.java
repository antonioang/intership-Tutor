/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Antonio
 */
public class Test extends DAO {
    private PreparedStatement DBtest;
    
    public Test(DataLayer d){
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        try{
            DBtest = connection.prepareStatement(" SELECT * FROM test");
        } catch(SQLException ex){
            throw new DataLayerException("Error initializing internship tutor datalayer", ex);
        }
    }
    
    
    public String testDB() throws DataLayerException {
        String test="e no eh";
        
        try {
            ResultSet rset = DBtest.executeQuery();
            if(rset.next()){
                test=rset.getString("prova1");
            }
            
        } catch (SQLException ex){
                throw new DataLayerException("Error query", ex);
            }
        return test;
        
    }
    
}
