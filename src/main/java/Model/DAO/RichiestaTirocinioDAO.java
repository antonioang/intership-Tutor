/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import java.sql.ResultSet;
import java.util.List;
import Model.Interfaces.RichiestaTirocinio;
import framework.data.DataLayerException;

/**
 *
 * @author jacopo
 */
public interface RichiestaTirocinioDAO {
    
    RichiestaTirocinio createRichiestaTirocinio();
    
    RichiestaTirocinio createRichiestaTirocinio(ResultSet rs) throws DataLayerException;
    
    RichiestaTirocinio getRichiestaTirocinio(int id_richiesta) throws DataLayerException;
    
    RichiestaTirocinio getRichiestaTirocinio(int id_tirocinio, int id_studente) throws DataLayerException;
    
    int addRichiestaTirocinio(RichiestaTirocinio r) throws DataLayerException;
    
    int updRichiestaTirocinioStato(int id_richiesta, int st) throws DataLayerException;
    
    int updDocumentoRichiestaTirocinio (int id_richiesta, String src) throws DataLayerException;
    
    List<RichiestaTirocinio> getRichiesteTirocinioByTirocinio(int id_tirocinio) throws DataLayerException;
}
