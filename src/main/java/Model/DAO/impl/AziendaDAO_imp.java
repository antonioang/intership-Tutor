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
            + "responsabile_tirocini)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            az.setPiva(rs.getInt("piva"));
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
        //java.sql.Date.valueOf();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
