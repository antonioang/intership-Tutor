/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
import Model.Interfaces.Valuta;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author jacopo
 */
public interface ValutaDAO {
    
    int insertValuta(Valuta v);
    
    List<Valuta> getValutazioni(Azienda az);
     
    List<Valuta> getValutazioni(Studente st);
    
    Valuta createValuta();
    
    Valuta createValuta(ResultSet rs);
    
    Valuta getValuta(int id_az, int id_st);
    
    int deleteValuta(int id_az, int id_st);
}
