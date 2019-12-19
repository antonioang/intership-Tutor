/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.SQLException;
import javax.sql.DataSource;
import Model.DAO.impl.TestDAO_Impl;

/**
 *
 * @author Antonio
 */
public class InternshipTutorDataLayer extends DataLayer {
    
    public InternshipTutorDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }
    
    //metodo che restituisce il dao del testDAO
    public TestDAO_Impl getTestDAO() {
        return (TestDAO_Impl) getDAO();
    }
    
    //inizializza i riferimenti ai file DAO
    @Override
    public void init() throws DataLayerException{
        registerDAO(new TestDAO_Impl(this));
    }
    
}
