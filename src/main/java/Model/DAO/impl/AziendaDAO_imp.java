/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.AziendaDAO;
import Model.Interfaces.Azienda;
import data.proxy.AziendaProxy;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jacopo
 */
public class AziendaDAO_imp extends DAO implements AziendaDAO{

    private PreparedStatement getAziendaById, getAziendaByUtente, getAziendaByStato, getAziendeConPiuTirocinanti;
    private PreparedStatement uAziendaByStato, uAziendaDoc, updAzienda;
    private PreparedStatement addAzienda, delAzienda, getValutazione;
    private PreparedStatement getBestAziende, getWorstAziende;
            
    public AziendaDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
       
        super.init();
        
        try {
            getAziendaById = connection.prepareStatement("SELECT * FROM azienda WHERE id_azienda = ?");
            getAziendaByUtente = connection.prepareStatement("SELECT * FROM azienda WHERE utente = ?");
            getAziendaByStato = connection.prepareStatement("SELECT * FROM azienda WHERE stato_conv = ?");
            getAziendeConPiuTirocinanti = connection.prepareStatement("SELECT*, count(*) as studenti FROM azienda JOIN tirocinio ON tirocinio.azienda = id_azienda "
                    + "JOIN richiesta_tirocinio ON richiesta_tirocinio.tirocinio = id_tirocinio WHERE stato_candidatura = 2 "
                    + "group by tirocinio.azienda ORDER BY studenti desc LIMIT 5");
            uAziendaByStato =  connection.prepareStatement("UPDATE azienda SET stato_conv = ? WHERE id_azienda = ?");
            uAziendaDoc = connection.prepareStatement("UPDATE azienda SET src_doc_conv = ? WHERE id_azienda = ?");
            updAzienda = connection.prepareStatement("UPDATE azienda\n" +
            "SET rag_sociale=?, indirizzo=?, citta=?, cap=?, provincia=?, rappr_leg=?, piva=?, "
            + "foro_competente=?, tematica=?, corso_studi=?, "
            + "inizio_conv=?, fine_conv=?, responsabile_tirocini=?, nome=?\n" +
            "WHERE id_azienda=?;");
            addAzienda = connection.prepareStatement("INSERT INTO azienda\n" +
            "(rag_sociale, indirizzo, citta, cap, provincia, rappr_leg, piva, foro_competente, "
            + "src_doc_conv, tematica, stato_conv, corso_studi, inizio_conv, fine_conv, "
            + "responsabile_tirocini, utente, nome)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            delAzienda = connection.prepareStatement("DELETE FROM azienda WHERE id_azienda=?");
            getValutazione = connection.prepareStatement("SELECT AVG(punteggio) AS media FROM valutazione_azienda where azienda = ?");
            getBestAziende = connection.prepareStatement("SELECT *, AVG(punteggio) as valutazione FROM valutazione_azienda "
                    + "JOIN azienda ON valutazione_azienda.azienda = id_azienda group by id_azienda ORDER BY valutazione desc LIMIT 5");
            getWorstAziende = connection.prepareStatement("SELECT *, AVG(punteggio) as valutazione FROM valutazione_azienda "
                    + "JOIN azienda ON valutazione_azienda.azienda = id_azienda group by id_azienda ORDER BY valutazione asc LIMIT 5");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore inizializzazione degli statement azienda", ex);
        }
    }

    @Override
    public AziendaProxy createAzienda() {
        return new AziendaProxy(getDataLayer());
    }

    @Override
    public Azienda createAzienda(ResultSet rs) throws DataLayerException {
        Azienda az = createAzienda();
        try {
            az.setNome(rs.getString("nome"));
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
            az.setId(rs.getInt("id_azienda"));
            az.setUtente(rs.getInt("utente"));
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
    public Azienda getAziendaByUtente(int id_utente) throws DataLayerException {
        try {
            getAziendaByUtente.setInt(1, id_utente);
            ResultSet rs = getAziendaByUtente.executeQuery();
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
                lista.add(createAzienda(rs)); 
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dell'azienda", ex);
        }
        return lista;
    }

    @Override
    public int updateAziendaStato(int id_az, int stato) throws DataLayerException {
        try {
            uAziendaByStato.setInt(1, stato);
            uAziendaByStato.setInt(2, id_az);
            return uAziendaByStato.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore l'aggiornamento dello stato della convenzione", ex);
        }
    }

    @Override
    public int addAzienda(Azienda az) throws DataLayerException {
        try {
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
            addAzienda.setInt(16, az.getUtente());
            addAzienda.setString(17, az.getNome());
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
    public int updAzienda(Azienda az) throws DataLayerException {
        try {
            updAzienda.setString(1, az.getRagioneSociale());
            updAzienda.setString(2, az.getIndirizzo());
            updAzienda.setString(3, az.getCitta());
            updAzienda.setInt(4, az.getCap());
            updAzienda.setString(5, az.getProvincia());
            updAzienda.setString(6, az.getRapprLeg());
            updAzienda.setString(7, az.getPiva());
            updAzienda.setString(8, az.getForoCompetente());
            updAzienda.setString(9, az.getTematica());
            updAzienda.setString(10, az.getCorsoStudi());
            updAzienda.setDate(11, java.sql.Date.valueOf(az.getInizioConv()));
            updAzienda.setDate(12, java.sql.Date.valueOf(az.getFineConv()));
            updAzienda.setInt(13, az.getRespTirocini());
            updAzienda.setString(14, az.getNome());
            updAzienda.setInt(15, az.getId());
            return updAzienda.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento dell'azienda", ex);
        }
    }

    @Override
    public void destroy()throws DataLayerException{
        try {
            getAziendaById.close();
            getAziendaByUtente.close();
            getAziendaByStato.close();
            uAziendaByStato.close();
            uAziendaDoc.close();
            updAzienda.close();
            addAzienda.close();
            delAzienda.close();
            getAziendeConPiuTirocinanti.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public float getValutazioneAzienda(int azienda) throws DataLayerException {
        try {
            getValutazione.setInt(1, azienda);
            ResultSet rs = getValutazione.executeQuery();
            if(rs.next()){
                return rs.getFloat("media");
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il calcolo della valutazione", ex);
        }
        return 0;
    }
    
    @Override
    public void storeAzienda(Azienda az)throws DataLayerException{
        
        if (az.getId() > 0){//Controllo se esiste un istanza dell'oggetto
            if(az instanceof AziendaProxy && !((AziendaProxy) az).isDirty()){
                return;//se l'oggetto è un istanza di utente proxy e dirty è false usciamo dal metodo
            }
                //update dell'oggetto
                updAzienda(az);
                System.out.println("aggiornata");

           }else{
               //insert dell'oggetto
                addAzienda(az);            
        }
        if(az instanceof AziendaProxy){
            ((AziendaProxy) az).setDirty(false);
        }
    }

    @Override
    public HashMap<Azienda, Integer> getAziendeConPiuTirocinanti() throws DataLayerException {
        try {
            HashMap aziende = new HashMap<Azienda, Integer>();
            ResultSet rs = getAziendeConPiuTirocinanti.executeQuery();
            while(rs.next()){
                aziende.put(createAzienda(rs), rs.getString("studenti"));
            }
            return aziende;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero delle aziende con più tirocinanti ", ex);
        }
    }

    @Override
    public List<Azienda> getBestAziende() throws DataLayerException {
        try {
            List<Azienda> lista = new ArrayList();
            ResultSet rs = getBestAziende.executeQuery();
            while(rs.next()){
                lista.add(createAzienda(rs));
            }
            return lista;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero delle aziende migliori ", ex);
        }
    }

    @Override
    public List<Azienda> getWorstAziende() throws DataLayerException {
        try {
            List<Azienda> lista = new ArrayList();
            ResultSet rs = getWorstAziende.executeQuery();
            while(rs.next()){
                lista.add(createAzienda(rs));
            }
            return lista;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero delle aziende peggiori ", ex);
        }
    }
    
}
