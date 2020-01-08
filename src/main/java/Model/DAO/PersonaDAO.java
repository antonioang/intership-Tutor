/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Persona;
import framework.data.DataLayerException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface PersonaDAO {
    
    Persona createPersona();
    
    Persona createPersona(ResultSet rs) throws DataLayerException;
    
    Persona getPersona(int id) throws DataLayerException;
    
    List<Persona> getPersona() throws DataLayerException;
    
    int insertPersona(Persona p) throws DataLayerException;
    
    int deletePersona(int id) throws DataLayerException;
    
    int UpdatePersona(Persona p) throws DataLayerException;
}
