/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.TirocinioDAO;
import Model.Interfaces.Azienda;
import Model.Interfaces.Tirocinio;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class TirocinioDAO_imp extends DAO implements TirocinioDAO {

    public TirocinioDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public Tirocinio createTirocinio() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tirocinio createTirocinio(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tirocinio> getTirocinio(Azienda az, boolean attiva) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tirocinio> getTirocinio(Azienda az) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tirocinio getTirocinio(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertTirocinio(Tirocinio ot) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateTirocinioAttiva(int id_ot, boolean attiva) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tirocinio> searchTirocinio(String durata, String titolo, String facilitazioni, String luogo, String settore, String obiettivi, String corsoStudio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
