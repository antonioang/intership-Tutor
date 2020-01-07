/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface AziendaDAO {
    
    Azienda createAzienda();
    
    Azienda createAzienda(ResultSet rs);
    
    Azienda getAzienda(int id);
    
    Azienda getAzienda(String ut_username);
    
    List<Azienda> getAziendeByStato(int stato);
    
    int updateAziendaStato(int id_az, int stato);
    
    int insertAzienda(Azienda az);
    
    int deleteAzienda(int id_az);
    
    int updateAziendaDocumento(int id_az, String src);
    
    int getTirocinantiAttivi(Azienda az);
    
    int updateAzienda(Azienda az);
    }
