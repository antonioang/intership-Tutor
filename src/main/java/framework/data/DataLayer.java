/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Antonio
 */
public class DataLayer {
    
    private final DataSource datasource;
    private Connection connection;
    private final Map<Class,DAO> dao;
    
    public DataLayer(DataSource dataSource) throws SQLException{
        //super();
        this.datasource=dataSource;
        this.connection=dataSource.getConnection();
        this.dao=new HashMap<>();
    }

    public void init() throws DataLayerException {
        //call registerDAO on your own DAOs
        
    }
    
    public void registerDAO(Class entityClass,DAO dao) throws DataLayerException {
        this.dao.put(entityClass, dao);
        dao.init();
    }
    
    public DAO getDAO(Class entityClass) {
        return dao.get(entityClass);
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
