/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.TestDAO;
import Model.Impl.Test_Impl;
import Model.Interfaces.Test;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Antonio
 */
public class TestDAO_Impl extends DAO implements TestDAO {
    private PreparedStatement DBtest;
    
    public TestDAO_Impl(DataLayer d){
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
    
    @Override
    public Test createTest() {
        return new Test_Impl();
    }
    
    @Override
    public Test createTest(ResultSet rs) throws DataLayerException{
        Test prova = createTest();
        try {
            prova.setTestString(rs.getString("prova1"));
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prova;
    }
    
    @Override
    public Test getTest() throws DataLayerException{
        try {
            ResultSet rset;
            rset = DBtest.executeQuery();
            if(rset.next()){
                return createTest(rset);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore con SQL"+ ex);
        }
        return null;
    }
}
