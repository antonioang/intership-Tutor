/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Richiede;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface RichiedeDAO {
    
    Richiede createRichiede();
    
    Richiede createRichiede(ResultSet rs);
    
    Richiede getRichiede(int id);
    
    int insertRichiede(Richiede r);
    
    int updateRichiedeStato(int id, int st);
    
    int updateDocumento(int id, String src);
}
