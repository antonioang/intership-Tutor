/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Antonio
 */
public class DataLayer {
    
    private final DataSource datasource;
    private Connection connection;
    private final List<DAO> dao;
    
    public DataLayer(DataSource dataSource) throws SQLException{
        super();
        this.datasource=dataSource;
        this.connection=dataSource.getConnection();
        this.dao=new ArrayList<DAO>();
    }

    public void init() throws DataLayerException {
        //call registerDAO on your own DAOs
    }
    
    public void registerDAO(DAO dao) throws DataLayerException {
        this.dao.add(dao);
        dao.init();
    }
    
    public DAO getDAO() {
        return dao.get(0);
    }
    
    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            //
        }
    }
    
    public DataSource getDatasource() {
    return datasource;
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
