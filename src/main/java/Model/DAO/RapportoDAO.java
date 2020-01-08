/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Rapporto;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface RapportoDAO {
    
    Rapporto createRapporto();
    
    Rapporto createRapporto(ResultSet rs);
    
    List<Rapporto> getRapporti(Studente st);
    
    List<Rapporto> getRapporti(Tirocinio t);
    
    Rapporto getRapporto(int id);
    
    int insertDocumentoRapporto(int st, int t, String src);
    
    
}
