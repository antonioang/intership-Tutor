/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.RichiedeDAO;
import Model.Interfaces.Richiede;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;

/**
 *
 * @author jacopo
 */
public class RichiedeDAO_imp extends DAO implements RichiedeDAO{

    public RichiedeDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public Richiede createRichiede() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Richiede createRichiede(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Richiede getRichiede(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertRichiede(Richiede r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateRichiedeStato(int id, int st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateDocumento(int id, String src) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
