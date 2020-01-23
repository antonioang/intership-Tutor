/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.PersonaDAO;
import Model.Impl.Persona_imp;
import Model.Interfaces.Persona;
import data.proxy.PersonaProxy;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class PersonaDAO_imp extends DAO implements PersonaDAO {
    
    private PreparedStatement addPersona, delPersona, updPersona; 
    private PreparedStatement getPersona, getTutoriTirocinio, getTutoriUniversitari, getResponsabiliTirocini;
    
    public PersonaDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        try {
            addPersona = connection.prepareStatement("INSERT INTO persona\n" +
                "(nome, cognome, email, telefono, tipo)\n" +
                "VALUES(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            delPersona = connection.prepareStatement("DELETE FROM persona WHERE id_persona = ?");
            updPersona = connection.prepareStatement("UPDATE persona\n" +
                "SET nome=?, cognome=?, email=?, telefono=?, tipo=?\n" +
                "WHERE id_persona=?;");
            getPersona = connection.prepareStatement("SELECT * FROM persona WHERE id_persona = ?");
            getResponsabiliTirocini = connection.prepareStatement("SELECT * FROM persona WHERE tipo = 1");
            getTutoriTirocinio = connection.prepareStatement("SELECT * FROM persona WHERE tipo = 2");
            getTutoriUniversitari = connection.prepareStatement("SELECT * FROM persona WHERE tipo = 3");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzazione degli Statement", ex);
        }
    }
    
    @Override
    public PersonaProxy createPersona(){
        return new PersonaProxy(getDataLayer());
    }

    @Override
    public PersonaProxy createPersona(ResultSet rs) throws DataLayerException {
        try {
            PersonaProxy p = createPersona();
            p.setNome(rs.getString("nome"));
            p.setCognome(rs.getString("cognome"));
            p.setEmail(rs.getString("email"));
            p.setTelefono(rs.getString("telefono"));
            p.setTipo(rs.getInt("tipo"));
            p.setId(rs.getInt("id_persona"));
            return p;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la creazione della persona", ex);
        }
    }

    @Override
    public Persona getPersona(int id) throws DataLayerException {
        try {
            getPersona.setInt(1, id);
            ResultSet rs = getPersona.executeQuery();
            if(rs.next()){
                return createPersona(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della persona", ex);
        }
        return null;
    }

    @Override
    public int addPersona(Persona p) throws DataLayerException {
        try {
            addPersona.setString(1, p.getNome());
            addPersona.setString(2, p.getCognome());
            addPersona.setString(3, p.getEmail());
            addPersona.setString(4, p.getTelefono());
            addPersona.setInt(5, p.getTipo());
            if (addPersona.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addPersona.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        p.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inserimento della Persona", ex);
        }
    }

    @Override
    public int delPersona(int id) throws DataLayerException {
        try {
            delPersona.setInt(1, id);
            if (addPersona.executeUpdate() == 1) {
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la cancellazione della persona", ex);
        }
    }

    @Override
    public int updPersona(Persona p) throws DataLayerException {
        try {
            updPersona.setString(1, p.getNome());
            updPersona.setString(2, p.getCognome());
            updPersona.setString(3, p.getEmail());
            updPersona.setString(4, p.getTelefono());
            updPersona.setInt(5, p.getTipo());
            updPersona.setInt(6, p.getId());
            return updPersona.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento della persona", ex);
        }
    }

    @Override
    public List<Persona> getTutoriTirocinio() throws DataLayerException {
        List<Persona> lista = new ArrayList();
        try {
            ResultSet rs = getTutoriTirocinio.executeQuery();
            while(rs.next()){
                lista.add(createPersona(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tutori", ex);
        }
        return lista;
    }
    
    @Override
    public void destroy()throws DataLayerException{
        try {
            addPersona.close();
            delPersona.close();
            updPersona.close();
            getPersona.close();
            getTutoriTirocinio.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public List<Persona> getTutoriUniversitari() throws DataLayerException {
        List<Persona> lista = new ArrayList();
        try {
            ResultSet rs = getTutoriUniversitari.executeQuery();
            while(rs.next()){
                lista.add(createPersona(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tutori", ex);
        }
        return lista;
    }

    @Override
    public List<Persona> getResponsabiliTirocini() throws DataLayerException {
        List<Persona> lista = new ArrayList();
        try {
            ResultSet rs = getResponsabiliTirocini.executeQuery();
            while(rs.next()){
                lista.add(createPersona(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tutori", ex);
        }
        return lista;
    }
    
    @Override
    public void storePersona(Persona p) throws DataLayerException{
        
        if (p.getId() > 0){//Controllo se esiste un istanza dell'oggetto
            if(p instanceof PersonaProxy && !((PersonaProxy) p).isDirty()){
                return;//se l'oggetto è un istanza di utente proxy e dirty è false usciamo dal metodo
            }
             //update se l'oggetto è stato modificato 
                updPersona(p);
            
           }else{
            addPersona(p);
        }
        if(p instanceof PersonaProxy){
            ((PersonaProxy) p).setDirty(false);
        }
    }
    
}
