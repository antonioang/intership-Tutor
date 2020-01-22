/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Studente;
import framework.data.DataLayerException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface StudenteDAO {
    
    Studente createStudente();

    Studente createStudente(ResultSet rs) throws DataLayerException;

    Studente getStudente(int id) throws DataLayerException;
    
    Studente getStudenteByUtente(int id_utente) throws DataLayerException;
    
    List<Studente> getStudentiByTirocinioAccettato(int id_tirocinio) throws DataLayerException;
    
    List<Studente> getStudentiByTirocinioSospeso(int id_tirocinio) throws DataLayerException;
    
    List<Studente> getStudentiByTirocinioRifiutato(int id_tirocinio) throws DataLayerException;

    int addStudente(Studente st) throws DataLayerException;

    int updStudente(Studente st) throws DataLayerException;
    
    int delStudente(Studente st) throws DataLayerException;
    
    void storeStudente(Studente st) throws DataLayerException;
}
