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
    
    List<Persona> getTutoriTirocinio() throws DataLayerException;
    
    List<Persona> getTutoriUniversitari() throws DataLayerException;
    
    List<Persona> getResponsabiliTirocini() throws DataLayerException;
        
    int addPersona(Persona p) throws DataLayerException;
    
    int delPersona(int id) throws DataLayerException;
    
    int updPersona(Persona p) throws DataLayerException;
    
}
