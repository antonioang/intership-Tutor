/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Utente;
import framework.data.DataLayerException;
import java.sql.ResultSet;

/**
 *
 * @author matti
 */
public interface UtenteDAO {
    
    Utente createUtente();
    
    Utente createUtente(ResultSet rs) throws DataLayerException;
    
    Utente getUtente(int id_utente) throws DataLayerException;
    
    Utente getUtente(String username, String password) throws DataLayerException;
    
    Utente getUtentebyUsername(String username) throws DataLayerException;
    
    boolean utenteExists(String username, String email) throws DataLayerException;
    
    int addUtente(Utente utente) throws DataLayerException;
    
    int delUtente(int utente) throws DataLayerException;
    
    int updUtente(Utente utente) throws DataLayerException;
    
    void  storeUtente(Utente utente) throws DataLayerException;
}
