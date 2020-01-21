/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.TestDAO;
import Model.Impl.Test_Impl;
import Model.Interfaces.Test;
import data.proxy.TestProxy;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Antonio
 */
public class TestDAO_imp extends DAO implements TestDAO {
    private PreparedStatement DBtest, addTest, updTest;
    
    public TestDAO_imp(DataLayer d){
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        try{
            DBtest = connection.prepareStatement(" SELECT * FROM test");
            updTest = connection.prepareStatement("UPDATE test\n" +
            "SET prova1=?, id_test=?;");
            addTest = connection.prepareStatement("INSERT INTO test\n" +
            "(prova1, id_test)\n" +
            "VALUES(?, ?);" ,Statement.RETURN_GENERATED_KEYS);
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
    public TestProxy createTest() {
        return new TestProxy(getDataLayer());
    }
    
    @Override
    public TestProxy createTest(ResultSet rs) throws DataLayerException{
        TestProxy prova = createTest();
        try {
            prova.setTestString(rs.getString("prova1"));
        } catch (SQLException ex) {
            Logger.getLogger(TestDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @Override
    public void storeTest(Test test)throws DataLayerException{
        int key = test.getId();
        
        
        if(test.getId() > 0){
            if(test instanceof TestProxy && !((TestProxy) test).isDirty()){
                return;
            }
            try {
                updTest.setString(1, test.getTestString());
                updTest.setInt(2, test.getId());
                updTest.executeUpdate();
                System.out.println("update");
            } catch (SQLException ex) {
                 throw new DataLayerException("Errore durante l'update del test"+ ex);
            }
        }else{
            try {
                addTest.setString(1, test.getTestString());
                addTest.setInt(2, test.getId());
                System.out.println("inserimento");
            } catch (SQLException ex) {
                throw new DataLayerException("Errore durante l'inserimento del test"+ ex);
            }
          
            try {
                if(addTest.executeUpdate() == 1){
                    try (ResultSet keys = addTest.getGeneratedKeys()){
                        if(keys.next()){
                            key = keys.getInt(1);
                        }
                    }
                    test.setId(key);
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(TestDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
        if(test instanceof TestProxy){
            ((TestProxy) test).setDirty(false);
            System.out.println(((TestProxy) test).isDirty());
        }
        
    }
}
