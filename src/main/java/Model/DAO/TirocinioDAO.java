/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import Model.Interfaces.Tirocinio;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface TirocinioDAO {
    
    Tirocinio createTirocinio();
    
    Tirocinio createTirocinio(ResultSet rs);
    
    List<Tirocinio> getTirocinio(Azienda az, boolean attiva);
    
    List<Tirocinio> getTirocinio(Azienda az);
    
    Tirocinio getTirocinio(int id);
    
    int insertTirocinio(Tirocinio ot);
    
    int updateTirocinioAttiva(int id_ot, boolean attiva);
    
    List<Tirocinio> searchTirocinio(String durata, String titolo, String facilitazioni, String luogo, String settore, String obiettivi, String corsoStudio);
}
