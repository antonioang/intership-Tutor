/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.StudenteDAO;
import Model.Interfaces.Studente;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;

/**
 *
 * @author jacopo
 */
public class StudenteDAO_imp extends DAO implements StudenteDAO {

    public StudenteDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public Studente createStudente() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Studente createStudente(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Studente getStudente(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertStudente(Studente st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateStudente(Studente st) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
