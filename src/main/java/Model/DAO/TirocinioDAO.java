/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import Model.Interfaces.Tirocinio;
import framework.data.DataLayerException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface TirocinioDAO {
    
    Tirocinio createTirocinio();
    
    Tirocinio createTirocinio(ResultSet rs) throws DataLayerException;
    
    List<Tirocinio> getTirociniVisibili (int az, boolean visibile) throws DataLayerException;
    
    List<Tirocinio> getTirocini(int az) throws DataLayerException;
    
    Tirocinio getTirocinio(int id) throws DataLayerException;
    
    int addTirocinio(Tirocinio ot) throws DataLayerException;
    
    int updTirocinio(Tirocinio ot) throws DataLayerException;
    
    int delTirocinio(int id) throws DataLayerException;
    
    int updTirocinioVisibile(int id_ot, boolean visibile) throws DataLayerException;
       
    List<Tirocinio> searchTirocinio(int durata, String titolo, String facilitazioni, String luogo, String settore, String obiettivi, String corsoStudio) throws DataLayerException;
    
    void storeTirocinio(Tirocinio tirocinio) throws DataLayerException;
}
