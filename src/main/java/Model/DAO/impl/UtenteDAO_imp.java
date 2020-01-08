/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.UtenteDAO;
import Model.Impl.Utente_imp;
import Model.Interfaces.Utente;
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
 * @author matti
 */
public class UtenteDAO_imp extends DAO implements UtenteDAO {
    
    private PreparedStatement sUtenteById, sUtenteByLogin, sUtenteByUser;
    private PreparedStatement iUtente, dUtente, uUtente;
    private PreparedStatement sCheckUtenteExist;
    
    public UtenteDAO_imp(DataLayer d){
        super(d);    
    }
    
    @Override
    public void init() throws DataLayerException{
        try {
            super.init();
            
            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            sUtenteById = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            sUtenteByLogin = connection.prepareStatement("SELECT * FROM utente WHERE username=? AND password=?");
            sUtenteByUser = connection.prepareStatement("SELECT * FROM utente WHERE username=?");
            sCheckUtenteExist = connection.prepareStatement("SELECT * FROM utente WHERE (username=?) or (email=?)");
            iUtente = connection.prepareStatement("INSERT INTO utente (email, username, pw, tipologia)"
                    + "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            dUtente = connection.prepareStatement("DELETE FROM utente WHERE id=?");
            uUtente = connection.prepareStatement("UPDATE utente SET email=?, username=?, pw=?, tipologia=? WHERE id=?");
        } catch (SQLException ex) {
            Logger.getLogger(UtenteDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public Utente createUtente() {
        return new Utente_imp();
    }

    @Override
    public Utente createUtente(ResultSet rs) throws DataLayerException {
        try {
            Utente ut = createUtente();
            ut.setUsername(rs.getString("username"));
            ut.setPassword(rs.getString("password"));
            ut.setEmail(rs.getString("email"));
            ut.setTipo(rs.getInt("tipo"));
            ut.setId(rs.getInt("id_utente"));
            return ut;
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to create Utente object form ResultSet", ex);
        }
    }

    @Override
    public Utente getUtente(int id_utente) throws DataLayerException {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Utente getUtente(String username, String password) throws DataLayerException {
        try {
            sUtenteByLogin.setString(1, username);
            sUtenteByLogin.setString(2, password);
            
            ResultSet rs = sUtenteByLogin.executeQuery();
            if(rs.next()){
                return createUtente(rs);
            }
            
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load Utente by username and password", ex);
        }
        return null;
    }

    @Override
    public Utente getUtentebyUsername(String username) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean utenteExists(String username, String email) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addUtente(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delUtente(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateUtente(Utente utente) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
