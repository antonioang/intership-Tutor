/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.ValutaDAO;
import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
import Model.Interfaces.Valuta;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class ValutaDAO_imp extends DAO implements ValutaDAO{

    public ValutaDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public int insertValuta(Valuta v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Valuta> getValutazioni(Azienda az) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Valuta> getValutazioni(Studente st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Valuta createValuta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Valuta createValuta(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Valuta getValuta(int id_az, int id_st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteValuta(int id_az, int id_st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
