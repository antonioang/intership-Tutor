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
    
    private PreparedStatement UtenteById, UtenteByLogin, UtenteByUser;
    private PreparedStatement addUtente, delUtente, updUtente;
    private PreparedStatement CheckUtenteExist;
    
    public UtenteDAO_imp(DataLayer d){
        super(d);    
    }
    
    @Override
    public void init() throws DataLayerException{
        try {
            super.init();
            
            //precompiliamo tutte le query utilizzate nella classe
            //precompile all the queries uses in this class
            UtenteById = connection.prepareStatement("SELECT * FROM utente WHERE id=?");
            UtenteByLogin = connection.prepareStatement("SELECT * FROM utente WHERE username=? AND password=?");
            UtenteByUser = connection.prepareStatement("SELECT * FROM utente WHERE username=?");
            CheckUtenteExist = connection.prepareStatement("SELECT * FROM utente WHERE (username=?) or (email=?)");
            addUtente = connection.prepareStatement("INSERT INTO utente (email, username, password, tipo)"
                    + "values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            delUtente = connection.prepareStatement("DELETE FROM utente\n" +
                    "WHERE id_utente=?;");
            updUtente = connection.prepareStatement("UPDATE utente\n" +
                "SET username=?, email=?, tipo=?\n" +
                "WHERE id_utente=?;");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante inizializzazione degli statement", ex);
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
        try {
            UtenteById.setInt(1, id_utente);
            ResultSet rs = UtenteById.executeQuery();
            if(rs.next()){
              return createUtente(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dello studente", ex);
        }
        return null;
    }

    @Override
    public Utente getUtente(String username, String password) throws DataLayerException {
        try {
            UtenteByLogin.setString(1, username);
            UtenteByLogin.setString(2, password);
            
            ResultSet rs = UtenteByLogin.executeQuery();
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
        try {
            UtenteByUser.setString(1, username);
            ResultSet rs = UtenteByUser.executeQuery();
            if(rs.next()){
                return createUtente(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to load Utente by username", ex);
        }
        return null;
    }

    @Override
    public boolean utenteExists(String username, String email) throws DataLayerException {
        try {
            CheckUtenteExist.setString(1, username);
            CheckUtenteExist.setString(2, email);
            ResultSet rs = CheckUtenteExist.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Utente doesn't exists", ex);
        }
        return false;
    }

    @Override
    public int addUtente(Utente utente) throws DataLayerException {
        try {
            addUtente.setString(1, utente.getEmail());
            addUtente.setString(2, utente.getUsername());
            addUtente.setString(3, utente.getPassword());
            addUtente.setInt(4, utente.getTipo());
            if (addUtente.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addUtente.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        utente.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Unable to add new utente", ex);
        }
    }

    @Override
    public int delUtente(Utente utente) throws DataLayerException {
        try {
            delUtente.setInt(1, utente.getId());      
            return delUtente.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore nella cancellazione dell'utente", ex);
        }
    }

    @Override
    public int updUtente(Utente utente) throws DataLayerException {
        try {
            updUtente.setString(1, utente.getUsername());
            updUtente.setString(3, utente.getEmail());
            updUtente.setInt(4, utente.getTipo());
            updUtente.setInt(5, utente.getId());
            return updUtente.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento dati utente", ex);
        }
    }
    
    @Override
    public void destroy() throws DataLayerException{
        //anche chiudere i PreparedStamenent è una buona pratica...
        //also closing PreparedStamenents is a good practice...
        try {
            addUtente.close();
            delUtente.close();
            updUtente.close();
            UtenteById.close();
            UtenteByLogin.close();
            UtenteByLogin.close();
            UtenteByUser.close();
            CheckUtenteExist.close();
            
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }
}
