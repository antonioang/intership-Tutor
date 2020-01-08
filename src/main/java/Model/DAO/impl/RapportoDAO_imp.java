/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.RapportoDAO;
import Model.Interfaces.Rapporto;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class RapportoDAO_imp extends DAO implements RapportoDAO {

    public RapportoDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public Rapporto createRapporto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rapporto createRapporto(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rapporto> getRapporti(Studente st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rapporto> getRapporti(Tirocinio t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rapporto getRapporto(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertDocumentoRapporto(int st, int t, String src) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
