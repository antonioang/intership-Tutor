/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Persona;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface PersonaDAO {
    
    Persona createPersona();
    
    Persona createPersona(ResultSet rs);
    
    Persona getPersona(int id);
    
    List<Persona> getPersona();
    
    int insertPersona(Persona p);
    
    int deletePersona(int id);
    
    int UpdatePersona(Persona p);
}
