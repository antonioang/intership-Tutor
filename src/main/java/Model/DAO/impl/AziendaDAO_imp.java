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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author jacopo
 */
public class AziendaDAO_imp extends DAO implements AziendaDAO{

    private PreparedStatement sAziendaById, sAziendaByUtenteUsername, sAziendaByStato, sTirocinantiAttivi;
    private PreparedStatement uAziendaByStato, uAziendaDoc, uAziendaById;
    private PreparedStatement iAziendaById, dAziendaById;
    
    public AziendaDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
       
        super.init();
        
        try {
            sAziendaById = connection.prepareStatement("SELECT * FROM azienda WHERE id_azienda = ?");
            sAziendaByUtenteUsername = connection.prepareStatement("");
            sAziendaByStato = connection.prepareStatement("SELECT * FROM azienda WHERE stato_conv = ?");
            sTirocinantiAttivi = connection.prepareStatement("SELECT * FROM azienda WHERE responsabile_tirocinio = ?");
            uAziendaByStato =  connection.prepareStatement("UPDATE aziendaSET stato_conv= ? WHERE id_azienda= ?");
            uAziendaDoc = connection.prepareStatement("UPDATE azienda SET src_doc_conv=? WHERE id_azienda=?");
            uAziendaById = connection.prepareStatement("UPDATE azienda\n" +
            "SET rag_sociale=?, indirizzo=?, citta=?, cap=?, provincia=?, rappr_leg=?, piva=?, "
            + "foro_competente=?, src_doc_conv=?, tematica=?, stato_conv=?, corso_studi=?, "
            + "inizio_conv=?, fine_conv=?, responsabile_tirocini=?\n" +
            "WHERE id_azienda=?;");
            iAziendaById = connection.prepareStatement("INSERT INTO azienda\n" +
            "(rag_sociale, indirizzo, citta, cap, provincia, rappr_leg, piva, foro_competente, "
            + "src_doc_conv, tematica, stato_conv, corso_studi, inizio_conv, fine_conv, "
            + "responsabile_tirocini)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            dAziendaById = connection.prepareStatement("DELETE FROM azienda WHERE id_azienda=?");
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
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
            //az.setRespTirocini(rs.getInt("responsabile_tirocini"));
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return az;
        
    }

    @Override
    public Azienda getAzienda(int id) throws DataLayerException {
        try {
            sAziendaById.setInt(1, id);
            ResultSet rs = sAziendaById.executeQuery();
            if(rs.next()){
                return createAzienda(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Azienda getAzienda(String ut_username) throws DataLayerException {
        try {
            sAziendaByUtenteUsername.setString(1, ut_username);
            ResultSet rs = sAziendaByUtenteUsername.executeQuery();
            if(rs.next()){
            return createAzienda(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Azienda> getAziendeByStato(int stato) throws DataLayerException {
        List<Azienda> lista = new ArrayList();
        try {
            sAziendaByStato.setInt(1, stato);
            ResultSet rs = sAziendaByStato.executeQuery();
            while (rs.next()){
                lista.add((Azienda) getAzienda(rs.getInt("id_azienda"))); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public int updateAziendaStato(int id_az, int stato) throws DataLayerException {
        try {
            uAziendaByStato.setInt(1, id_az);
            uAziendaByStato.setInt(12, stato);
            return uAziendaByStato.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AziendaDAO_imp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int insertAzienda(Azienda az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteAzienda(int id_az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAziendaDocumento(int id_az, String src) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTirocinantiAttivi(Azienda az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAzienda(Azienda az) throws DataLayerException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
