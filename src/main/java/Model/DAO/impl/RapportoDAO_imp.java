/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.RapportoDAO;
import Model.Impl.Rapporto_imp;
import Model.Interfaces.Rapporto;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jacopo
 */
public class RapportoDAO_imp extends DAO implements RapportoDAO {
    
     private PreparedStatement getRapporto, getRapportoByStudente, getRapportoByTirocinio;
     private PreparedStatement updateSrcDoc, addRapporto;
    
    public RapportoDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        super.init();
         try {
             addRapporto = connection.prepareStatement("INSERT INTO .rapporto_tirocinio (ore, descrizione_att, giudizio, src_doc_resoconto, studente, tirocinio)\n" +
                "VALUES(?, ?, ?, ?, ?, ?)");
             getRapporto = connection.prepareStatement("SELECT * FROM rapporto_tirocinio WHERE studente=? AND tirocinio=?");
             getRapportoByStudente = connection.prepareStatement("SELECT * FROM rapporto_tirocinio WHERE studente=?");
             getRapportoByTirocinio = connection.prepareStatement("SELECT * FROM rapporto_tirocinio WHERE tirocinio=?");
             updateSrcDoc = connection.prepareStatement("UPDATE rapporto_tirocinio SET src_doc_resoconto WHERE studente=? AND tirocinio=?");
         } catch (SQLException ex) {
              throw new DataLayerException("Errore durante l'inizializzazione degli statements",ex);
         }
    }

    @Override
    public Rapporto createRapporto() {
        return new Rapporto_imp();
    }

    
     @Override
    public Rapporto createRapporto(ResultSet rs) throws DataLayerException {
        Rapporto r = createRapporto();
         try {
             r.setOre(rs.getInt("ore"));
             r.setDescrizioneAtt(rs.getString("descrizione"));
             r.setGiudizio(rs.getString("giudizio"));
             r.setSrcDocResoconto(rs.getString("src_doc_resoconto"));
             r.setStudente(rs.getInt("studente"));
             r.setTirocinio(rs.getInt("tirocinio"));
             
         } catch (SQLException ex) {
              throw new DataLayerException("Errore durante creazione del rapporto tirocinio", ex);
         }
        return r;
    }

    @Override
    public List<Rapporto> getRapporti(int st) throws DataLayerException  {
        List<Rapporto> list = new ArrayList();
         try {
             getRapportoByStudente.setInt(1, st);
             ResultSet rs = getRapportoByStudente.executeQuery();
             if(rs.next()){
                 list.add(createRapporto(rs));
             }
         } catch (SQLException ex) {
             throw new DataLayerException("Errore durante il recupero dei rapporti studenti", ex);
         }
        return list;
    }

    @Override
    public List<Rapporto> getRapportiTirocini(int t) throws DataLayerException  {
        List<Rapporto> list = new ArrayList();
         try {
             getRapportoByTirocinio.setInt(1, t);
             ResultSet rs = getRapportoByTirocinio.executeQuery();
             if(rs.next()){
                 list.add(createRapporto(rs));
             }
         } catch (SQLException ex) {
             throw new DataLayerException("Errore durante il recupero dei rapporti tirocini", ex);
         }
        return list;
    }

    @Override
    public Rapporto getRapporto(int id_studente, int id_tirocinio) throws DataLayerException {
         try {
             getRapporto.setInt(1, id_studente);
             getRapporto.setInt(2, id_tirocinio);
             ResultSet rs = getRapporto.executeQuery();
             if(rs.next()){
                 return createRapporto(rs);
             }
         } catch (SQLException ex) {
              throw new DataLayerException("Errore durante il recupero del rapporto", ex);
         }
         return null;
    }


    @Override
    public int addRapporto(Rapporto rp) throws DataLayerException {
         try {
             addRapporto.setInt(1, rp.getOre());
             addRapporto.setString(2, rp.getDescrizioneAtt());
             addRapporto.setString(3, rp.getGiudizio());
             addRapporto.setString(4, rp.getSrcDocResoconto());
             addRapporto.setInt(5, rp.getStudente());
             addRapporto.setInt(6, rp.getStudente());
             return addRapporto.executeUpdate();
         } catch (SQLException ex) {
             throw new DataLayerException("Errore durante l'inserimento del rapporto", ex);
         }
    }
    
     @Override
    public void destroy()throws DataLayerException{
        try {
            getRapporto.close();
            getRapportoByStudente.close();
            getRapportoByTirocinio.close();
            updateSrcDoc.close();
            addRapporto.close();
            
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }


   
     @Override
    public void storeRapporto(Rapporto rapporto){
        
    }
}
