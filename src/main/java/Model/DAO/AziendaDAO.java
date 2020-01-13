/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import framework.data.DataLayerException;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface AziendaDAO {
    
    Azienda createAzienda();
    
    Azienda createAzienda(ResultSet rs) throws DataLayerException;
    
    Azienda getAzienda(int id) throws DataLayerException;
    
    Azienda getAziendaByUtente(int id_utente) throws DataLayerException;
    
    List<Azienda> getAziendeByStato(int stato) throws DataLayerException;
        
    int updateAziendaStato(int id_az, int stato) throws DataLayerException;
    
    int addAzienda(Azienda az) throws DataLayerException;
    
    int delAzienda(int id_az) throws DataLayerException;
    
    int updAziendaDocumento(int id_az, String src) throws DataLayerException;
    
    int getTirocinantiAttivi(Azienda az) throws DataLayerException;
    
    int updAzienda(Azienda az) throws DataLayerException;
    
}
