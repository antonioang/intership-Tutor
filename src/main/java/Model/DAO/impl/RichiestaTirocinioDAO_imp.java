/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import Model.DAO.RichiestaTirocinioDAO;
import Model.Impl.RichiestaTirocinio_imp;
import Model.Interfaces.RichiestaTirocinio;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jacopo
 */
public class RichiestaTirocinioDAO_imp extends DAO implements RichiestaTirocinioDAO{
    
    private PreparedStatement addRichiestaTirocinio, updRichiestaTirocinioStato, updDocumentoRichiestaTirocinio;
    private PreparedStatement getRichiestaTirocinio, getRichiesteTirocinioByTirocinio, getRicTirByTirocinioStudente;
    
    public RichiestaTirocinioDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        try {
            addRichiestaTirocinio = connection.prepareStatement("INSERT INTO richiesta_tirocinio\n" +
                "(src_doc_candid, dottorato_ricerca, specializzazione, laurea, diploma, data_inizio, data_fine, stato_candidatura, cfu, tutore_universitario, studente, tirocinio)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            updRichiestaTirocinioStato = connection.prepareStatement("UPDATE richiesta_tirocinio SET stato_candidatura=? WHERE id_richiesta=?");
            updDocumentoRichiestaTirocinio = connection.prepareStatement("UPDATE richiesta_tirocinio src_doc_candid=? WHERE id_richiesta=?");
            getRichiestaTirocinio = connection.prepareStatement("SELECT * FROM richiesta_tirocinio WHERE id_richiesta=?");
            getRichiesteTirocinioByTirocinio = connection.prepareStatement("SELECT * FROM richiesta_tirocinio WHERE tirocinio=?");
            getRicTirByTirocinioStudente = connection.prepareStatement("SELECT * FROM richiesta_tirocinio WHERE tirocinio = ? AND studente = ?");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzazione degli statements",ex);
        }
    }

    @Override
    public RichiestaTirocinio createRichiestaTirocinio() {
        return new RichiestaTirocinio_imp();
    }

    @Override
    public RichiestaTirocinio createRichiestaTirocinio(ResultSet rs) throws DataLayerException {
        RichiestaTirocinio  rt = createRichiestaTirocinio();
        
        try {
            rt.setId(rs.getInt("id_richiesta"));
            rt.setSrcDocCandid(rs.getString("src_doc_candid"));
            rt.setDottorato(rs.getString("dottorato_ricerca"));
            rt.setSpecializzazione(rs.getString("specializzazione"));
            rt.setLaurea(rs.getString("laurea"));
            rt.setDiploma(rs.getString("diploma"));
            rt.setDataInizio(rs.getDate("data_inizio").toLocalDate());
            rt.setDataFine(rs.getDate("data_fine").toLocalDate());
            rt.setStatoCandidatura(rs.getInt("stato_candidatura"));
            rt.setCfu(rs.getInt("cfu"));
            rt.setStudente(rs.getInt("studente"));
            rt.setTutoreUniversitario(rs.getInt("tutore_universitario"));
            rt.setTirocinio(rs.getInt("tirocinio"));
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la creazione della richiesta di tirocinio");
        }
        return rt;
    }

    @Override
    public RichiestaTirocinio getRichiestaTirocinio(int id_richiesta) throws DataLayerException {
        try {
            getRichiestaTirocinio.setInt(1,id_richiesta);
            ResultSet rs = getRichiestaTirocinio.executeQuery();
            if(rs.next()){
                return createRichiestaTirocinio(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della richiesta tirocinio", ex);
        }
        return null;
    }

    @Override
    public int addRichiestaTirocinio(RichiestaTirocinio r) throws DataLayerException {
        try {
            addRichiestaTirocinio.setString(2, r.getDottorato());
            addRichiestaTirocinio.setString(3, r.getSpecializzazione());
            addRichiestaTirocinio.setString(4, r.getLaurea());
            addRichiestaTirocinio.setString(5, r.getDiploma());
            addRichiestaTirocinio.setInt(8, r.getStatoCandidatura());
            addRichiestaTirocinio.setInt(9, r.getCfu());
            addRichiestaTirocinio.setInt(10, r.getTutoreUniversitario());
            addRichiestaTirocinio.setInt(11, r.getStudente());
            addRichiestaTirocinio.setInt(12, r.getTirocinio());
             if (addRichiestaTirocinio.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addRichiestaTirocinio.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        r.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inserimento della richiesta tirocinio", ex);
        }
    }

    @Override
    public int updRichiestaTirocinioStato(int id_richiesta, int st) throws DataLayerException {
        try {
            updRichiestaTirocinioStato.setInt(1, st);
            updRichiestaTirocinioStato.setInt(2, id_richiesta);
            return updRichiestaTirocinioStato.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante lìaggiornamento dello stato della richiesta", ex);
        }
    }

    @Override
    public int updDocumentoRichiestaTirocinio(int id_richiesta, String src) throws DataLayerException {
        try {
            updDocumentoRichiestaTirocinio.setString(1, src);
            updDocumentoRichiestaTirocinio.setInt(2, id_richiesta);
            return updRichiestaTirocinioStato.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento del documento della richiesta", ex);
        }
    }

    @Override
    public List<RichiestaTirocinio> getRichiesteTirocinioByTirocinio(int id_tirocinio) throws DataLayerException {
        List<RichiestaTirocinio> lista = new ArrayList();
        try {
            getRichiesteTirocinioByTirocinio.setInt(1, id_tirocinio);
            ResultSet rs = getRichiesteTirocinioByTirocinio.executeQuery();
            while(rs.next()){
                lista.add(createRichiestaTirocinio(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero delle richieste tirocinio", ex);
        }
        return lista;
    }

    @Override
    public RichiestaTirocinio getRichiestaTirocinio(int id_tirocinio, int id_studente) throws DataLayerException {
        try {
            getRicTirByTirocinioStudente.setInt(1, id_tirocinio);
            getRicTirByTirocinioStudente.setInt(2, id_studente);
            ResultSet rs = getRicTirByTirocinioStudente.executeQuery();
            if(rs.next()){
                return createRichiestaTirocinio(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della richiesta tirocinio", ex);
        }
        return null;
    }

    @Override
    public void destroy()throws DataLayerException{
        try {
            addRichiestaTirocinio.close();
            updRichiestaTirocinioStato.close();
            updDocumentoRichiestaTirocinio.close();
            getRichiestaTirocinio.close();
            getRichiesteTirocinioByTirocinio.close();
            getRicTirByTirocinioStudente.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }
    
}
