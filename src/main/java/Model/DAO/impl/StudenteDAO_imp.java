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
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jacopo
 */
public class StudenteDAO_imp extends DAO implements StudenteDAO {
    private PreparedStatement getStudentebyID, getStudenteByUtente, getStudentiByTirocinioAccettato, getStudentiByTirocinioSospeso, getStudentiByTirocinioRifiutato;
    private PreparedStatement addStudente, updStudente, delStudente;
    
    public StudenteDAO_imp(DataLayer d) {
        super(d);
    }
    public void init() throws DataLayerException{
        try {
            addStudente = connection.prepareStatement("INSERT INTO heroku_fb8c344fac20fe1.studente"
                    + "(nome, cognome, cod_fiscale, data_nascita, citta_nascita, provincia_nascita, citta_residenza, provincia_residenza, cap_residenza, telefono, corso_laurea, handicap, utente)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            getStudentebyID = connection.prepareStatement("SELECT * FROM studente WHERE id_studente = ?");
            getStudenteByUtente = connection.prepareStatement("SELECT * FROM studente WHERE utente = ?");
            updStudente = connection.prepareStatement("UPDATE heroku_fb8c344fac20fe1.studente\n" +
                "SET nome=?, cognome=?, cod_fiscale=?, data_nascita=?, citta_nascita=?, provincia_nascita=?, citta_residenza=?, provincia_residenza=?, cap_residenza=?, telefono=?, corso_laurea=?, handicap=?, utente=?\n" +
                "WHERE id_studente=?");
            getStudentiByTirocinioAccettato = connection.prepareStatement("SELECT * FROM studente JOIN richiesta_tirocinio ON studente.id_studente = richiesta_tirocinio.studente WHERE richiesta_tirocinio.tirocinio = ? AND stato_candidatura = 2");
            getStudentiByTirocinioSospeso = connection.prepareStatement("SELECT * FROM studente JOIN richiesta_tirocinio ON studente.id_studente = richiesta_tirocinio.studente WHERE richiesta_tirocinio.tirocinio = ? AND stato_candidatura = 1");
            getStudentiByTirocinioRifiutato = connection.prepareStatement("SELECT * FROM studente JOIN richiesta_tirocinio ON studente.id_studente = richiesta_tirocinio.studente WHERE richiesta_tirocinio.tirocinio = ? AND stato_candidatura = 4");
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
            st.setId(rs.getInt("id_studente"));
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
    public int updStudente(Studente st) throws DataLayerException {
        try {
            updStudente.setString(1, st.getNome());
            updStudente.setString(2, st.getCognome());
            updStudente.setString(3, st.getCodFiscale());
            updStudente.setDate(4, java.sql.Date.valueOf(st.getDataNascita()));
            updStudente.setString(5, st.getCittaNascita());
            updStudente.setString(6, st.getProvinciaNascita());
            updStudente.setString(7, st.getCittaResidenza());
            updStudente.setString(8, st.getProvinciaResidenza());
            updStudente.setInt(9, st.getCapResidenza());
            updStudente.setString(10, st.getTelefono());
            updStudente.setString(11, st.getCorsoLaurea());
            updStudente.setBoolean(12, st.getHandicap());
            updStudente.setInt(13, st.getUtente().getId());
            updStudente.setInt(14, st.getId());           
            return updStudente.executeUpdate(); 
            
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento dati studente", ex);
        }
    }

    @Override
    public int delStudente(Studente st) throws DataLayerException {
        try {
            delStudente.setInt(1,st.getId());
            return delStudente.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la cancellazione dello studente", ex);
        }
    }

    @Override
    public Studente getStudenteByUtente(int id_utente) throws DataLayerException {
        try {
            getStudenteByUtente.setInt(1, id_utente);
            ResultSet rs = getStudenteByUtente.executeQuery();
            if(rs.next()){
               return createStudente(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dello studente tramite l'utente", ex);
        }
        return null;
    }

    @Override
    public List<Studente> getStudentiByTirocinioAccettato(int id_tirocinio) throws DataLayerException {
        List<Studente> lista = new ArrayList();
        try {
            getStudentiByTirocinioAccettato.setInt(1, id_tirocinio);
            ResultSet rs = getStudentiByTirocinioAccettato.executeQuery();
            while(rs.next()){
                lista.add(createStudente(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero degli studenti associati al tirocinio");
        }
        return lista;
    }

    @Override
    public List<Studente> getStudentiByTirocinioSospeso(int id_tirocinio) throws DataLayerException {
        List<Studente> lista = new ArrayList();
        try {
            getStudentiByTirocinioSospeso.setInt(1, id_tirocinio);
            ResultSet rs = getStudentiByTirocinioSospeso.executeQuery();
            while(rs.next()){
                lista.add(createStudente(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero degli studenti associati al tirocinio");
        }
        return lista;
    }

    @Override
    public List<Studente> getStudentiByTirocinioRifiutato(int id_tirocinio) throws DataLayerException {
        List<Studente> lista = new ArrayList();
        try {
            getStudentiByTirocinioRifiutato.setInt(1, id_tirocinio);
            ResultSet rs = getStudentiByTirocinioRifiutato.executeQuery();
            while(rs.next()){
                lista.add(createStudente(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero degli studenti associati al tirocinio");
        }
        return lista;
    }

     @Override
    public void destroy()throws DataLayerException{
        try {
            getStudentiByTirocinioRifiutato.close();
            getStudentebyID.close();
            getStudenteByUtente.close();
            getStudentiByTirocinioAccettato.close();
            getStudentiByTirocinioSospeso.close();
            addStudente.close();
            delStudente.close();
            updStudente.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }
    @Override
    public void storeStudente(Studente st) throws DataLayerException{
        
    } 
    
}
