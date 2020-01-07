/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Studente;
import java.sql.ResultSet;

/**
 *
 * @author jacopo
 */
public interface StudenteDAO {
    
Studente createStudente();

Studente createStudente(ResultSet rs);

Studente getStudente(int id);

int insertStudente(Studente st);
 
int updateStudente(Studente st);
}
