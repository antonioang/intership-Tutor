/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
import java.sql.ResultSet;
import java.util.List;
import Model.Interfaces.Valutazione;
import framework.data.DataLayerException;

/**
 *
 * @author matti
 */
public interface ValutazioneDAO {
    
    Valutazione createValutazione() throws DataLayerException;
    
    Valutazione createValutazione(ResultSet rs)throws DataLayerException;
    
    List<Valutazione> getValutazioni(int az)throws DataLayerException;
    
    List<Valutazione> getValutazioniByStudente(int st)throws DataLayerException;
    
    Valutazione getValutazione(int id_az, int id_st)throws DataLayerException;
    
    int deleteValutazione(int id_az, int id_st)throws DataLayerException;
    
    int insertValutazione(Valutazione v)throws DataLayerException;
}