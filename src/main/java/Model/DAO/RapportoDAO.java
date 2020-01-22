/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Rapporto;
import framework.data.DataLayerException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface RapportoDAO {
    
    Rapporto createRapporto() ;
    
    Rapporto createRapporto(ResultSet rs) throws DataLayerException;
    
    int addRapporto(Rapporto rp) throws DataLayerException;
    
    List<Rapporto> getRapporti(int st) throws DataLayerException;
    
    List<Rapporto> getRapportiTirocini(int t) throws DataLayerException;
    
    Rapporto getRapporto(int id_studente, int id_tirocinio) throws DataLayerException;
    
    void storeRapporto(Rapporto rapporto) throws DataLayerException;
}
