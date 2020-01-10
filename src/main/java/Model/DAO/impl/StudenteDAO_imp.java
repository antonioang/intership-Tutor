/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.StudenteDAO;
import Model.Impl.Studente_imp;
import Model.Interfaces.Studente;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import framework.security.SecurityLayer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jacopo
 */
public class StudenteDAO_imp extends DAO implements StudenteDAO {
    private PreparedStatement addStudente, getStudentebyID;
            
    public StudenteDAO_imp(DataLayer d) {
        super(d);
    }
    public void init() throws DataLayerException{
        try {
            addStudente = connection.prepareStatement("INSERT INTO heroku_fb8c344fac20fe1.studente"
                    + "(nome, cognome, cod_fiscale, data_nascita, citta_nascita, provincia_nascita, citta_residenza, provincia_residenza, cap_residenza, telefono, corso_laurea, handicap, utente)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            getStudentebyID = connection.prepareStatement("SELECT * FROM studente WHERE id_studente = ?");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante inizializzazione degli statement", ex);
        }
    }
    
    @Override
    public Studente createStudente() {
        return new Studente_imp();
    }

    @Override
    public Studente createStudente(ResultSet rs) throws DataLayerException {
        try {
            Studente st = createStudente();
            st.setCapResidenza(rs.getInt("cap_residenza"));
            st.setCittaNascita(rs.getString("citta_nascita"));
            st.setCittaResidenza(rs.getString("citta_residenza"));
            st.setCodFiscale(rs.getString("cod_fiscale"));
            st.setCognome(rs.getString("cognome"));
            st.setCorsoLaurea(rs.getString("corso_laurea"));
            st.setDataNascita(SecurityLayer.checkDate(rs.getString("data_nascita")));
            st.setHandicap(SecurityLayer.checkBoolean(rs.getString("handicap")));
            st.setNome(rs.getString("nome"));
            st.setProvinciaNascita(rs.getString("provincia_nascita"));
            st.setProvinciaResidenza(rs.getString("provincia_residenza"));
            st.setTelefono(rs.getString("telefono"));
            return st;
        } catch (SQLException ex) {
            throw new DataLayerException("Impossibile creare oggetto Studente dal ResultSet", ex);
        }
    }

    @Override
    public Studente getStudente(int id) throws DataLayerException {
        try {
            getStudentebyID.setInt(1, id);
            ResultSet rs = getStudentebyID.executeQuery();
            if(rs.next()){
                return createStudente(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dello studente tramite l'id", ex);
        }
        return null;
    }

    @Override
    public int addStudente(Studente st) throws DataLayerException {
        try {
            addStudente.setString(1, st.getNome());
            addStudente.setString(2, st.getCognome());
            addStudente.setString(3, st.getCodFiscale());
            addStudente.setDate(4, java.sql.Date.valueOf(st.getDataNascita()));
            addStudente.setString(5, st.getCittaNascita());
            addStudente.setString(6, st.getProvinciaNascita());
            addStudente.setString(7, st.getCittaResidenza());
            addStudente.setString(8, st.getProvinciaResidenza());
            addStudente.setInt(9, st.getCapResidenza());
            addStudente.setString(10, st.getTelefono());
            addStudente.setString(11, st.getCorsoLaurea());
            addStudente.setBoolean(12, st.getHandicap());
            addStudente.setInt(13, st.getUtente().getId());
            if (addStudente.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addStudente.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        st.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore statement aggiunta studente", ex);
        }
    }

    @Override
    public int updateStudente(Studente st) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delStudente(Studente st) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
