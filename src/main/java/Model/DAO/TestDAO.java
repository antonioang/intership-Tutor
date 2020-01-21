/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Test;
import framework.data.DataLayerException;
import java.sql.ResultSet;

/**
 *
 * @author matti
 */
public interface TestDAO {
       
    Test createTest();
    
    Test createTest(ResultSet rs) throws DataLayerException;
    
    Test getTest() throws DataLayerException;
    
    void storeTest(Test test) throws DataLayerException;
    
}
