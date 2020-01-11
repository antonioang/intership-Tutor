/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.AziendaDAO;
import Model.Impl.Azienda_imp;
import Model.Interfaces.Azienda;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author jacopo
 */
public class AziendaDAO_imp extends DAO implements AziendaDAO{

    private PreparedStatement getAziendaById, getAziendaByUtenteUsername, getAziendaByStato, getTirocinantiAttivi;
    private PreparedStatement uAziendaByStato, uAziendaDoc, updAzienda;
    private PreparedStatement addAzienda, delAzienda;
    
    public AziendaDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
       
        super.init();
        
        try {
            getAziendaById = connection.prepareStatement("SELECT * FROM azienda WHERE id_azienda = ?");
            getAziendaByUtenteUsername = connection.prepareStatement("SELECT * FROM azienda JOIN utente ON azienda.utente=id_utente WHERE username=?");
            getAziendaByStato = connection.prepareStatement("SELECT * FROM azienda WHERE stato_conv = ?");
            getTirocinantiAttivi = connection.prepareStatement("SELECT * FROM azienda WHERE responsabile_tirocinio = ?");
            uAziendaByStato =  connection.prepareStatement("UPDATE aziendaSET stato_conv= ? WHERE id_azienda= ?");
            uAziendaDoc = connection.prepareStatement("UPDATE azienda SET src_doc_conv=? WHERE id_azienda=?");
            updAzienda = connection.prepareStatement("UPDATE azienda\n" +
            "SET rag_sociale=?, indirizzo=?, citta=?, cap=?, provincia=?, rappr_leg=?, piva=?, "
            + "foro_competente=?, src_doc_conv=?, tematica=?, stato_conv=?, corso_studi=?, "
            + "inizio_conv=?, fine_conv=?, responsabile_tirocini=?\n" +
            "WHERE id_azienda=?;");
            addAzienda = connection.prepareStatement("INSERT INTO azienda\n" +
            "(rag_sociale, indirizzo, citta, cap, provincia, rappr_leg, piva, foro_competente, "
            + "src_doc_conv, tematica, stato_conv, corso_studi, inizio_conv, fine_conv, "
            + "responsabile_tirocini, utente)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            delAzienda = connection.prepareStatement("DELETE FROM azienda WHERE id_azienda=?");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore inizializzazione degli statement azienda", ex);
        }
    }

    @Override
    public Azienda createAzienda() {
        return new Azienda_imp();
    }

    @Override
    public Azienda createAzienda(ResultSet rs) throws DataLayerException {
        Azienda az = createAzienda();
        try {
            az.setRagioneSociale(rs.getString("rag_sociale"));
            az.setIndirizzo(rs.getString("indirizzo"));
            az.setCitta(rs.getString("citta"));
            az.setCap(rs.getInt("cap"));
            az.setProvincia(rs.getString("provincia"));
            az.setRapprLeg(rs.getString("rappr_leg"));
            az.setPiva(rs.getString("piva"));
            az.setForoCompetente(rs.getString("foro_competente"));
            az.setSrcDocPath(rs.getString("src_doc_conv"));
            az.setTematica(rs.getString("tematica"));
            az.setStatoConv(rs.getBoolean("stato_conv"));
            az.setCorsoStudi(rs.getString("corso_studi"));
            az.setInizioConv(rs.getDate("inizio_conv").toLocalDate());
            az.setFineConv(rs.getDate("fine_conv").toLocalDate());
            az.setRespTirocini(rs.getInt("responsabile_tirocini"));
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante creazione azienda", ex);
        }
        return az;        
    }

    @Override
    public Azienda getAzienda(int id) throws DataLayerException {
        try {
            getAziendaById.setInt(1, id);
            ResultSet rs = getAziendaById.executeQuery();
            if(rs.next()){
                return createAzienda(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dell'azienda", ex);
        }
        return null;
    }

    @Override
    public Azienda getAzienda(String ut_username) throws DataLayerException {
        try {
            getAziendaByUtenteUsername.setString(1, ut_username);
            ResultSet rs = getAziendaByUtenteUsername.executeQuery();
            if(rs.next()){
                return createAzienda(rs);
            }
            return null;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dell'azienda", ex);
        }
    }

    @Override
    public List<Azienda> getAziendeByStato(int stato) throws DataLayerException {
        List<Azienda> lista = new ArrayList();
        try {
            getAziendaByStato.setInt(1, stato);
            ResultSet rs = getAziendaByStato.executeQuery();
            while (rs.next()){
                lista.add((Azienda) getAzienda(rs.getInt("id_azienda"))); 
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dell'azienda", ex);
        }
        return lista;
    }

    @Override
    public int updateAziendaStato(int id_az, int stato) throws DataLayerException {
        try {
            uAziendaByStato.setInt(1, id_az);
            uAziendaByStato.setInt(2, stato);
            return uAziendaByStato.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dell'azienda", ex);
        }
    }

    @Override
    public int addAzienda(Azienda az) throws DataLayerException {
        try {
            //java.sql.Date.valueOf();
            addAzienda.setString(1, az.getRagioneSociale());
            addAzienda.setString(2, az.getIndirizzo());
            addAzienda.setString(3, az.getCitta());
            addAzienda.setInt(4, az.getCap());
            addAzienda.setString(5, az.getProvincia());
            addAzienda.setString(6, az.getRapprLeg());
            addAzienda.setString(7, az.getPiva());
            addAzienda.setString(8, az.getForoCompetente());
            addAzienda.setString(9, az.getSrcDocPath());
            addAzienda.setString(10, az.getTematica());
            addAzienda.setBoolean(11, az.getStatoConv());
            addAzienda.setString(12, az.getCorsoStudi());
            addAzienda.setDate(13, java.sql.Date.valueOf(az.getInizioConv()));
            addAzienda.setDate(14, java.sql.Date.valueOf(az.getFineConv()));
            addAzienda.setInt(15, az.getRespTirocini());
            addAzienda.setInt(16, az.getUtente().getId());
            if (addAzienda.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addAzienda.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        az.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inserimento dell'azienda", ex);
        }
    }

    @Override
    public int delAzienda(int id_az) throws DataLayerException {
        try {
            delAzienda.setInt(1, id_az);
            return delAzienda.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la cancellazione dell'azienda", ex);
        }
    }

    @Override
    public int updAziendaDocumento(int id_az, String src) throws DataLayerException {
        try {
            uAziendaDoc.setInt(2, id_az);
            uAziendaDoc.setString(1, src);
            return uAziendaDoc.executeUpdate();
        } catch (SQLException ex) {
           throw new DataLayerException("Update documento azienda fallito",ex);
        }    
    }

    @Override
    public int getTirocinantiAttivi(Azienda az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updAzienda(Azienda az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
