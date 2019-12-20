/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interfaces;

/**
 *
 * @author jacopo
 */
public interface Rapporto {
    String getOre();
    
    String getDescrizioneAtt();
    
    String getGiudizio();
    
    String getSrcDocResoconto();
    
    Studente getStudente();
    
    Tirocinio getTirocinio();
    
    void setOre(String ore);
    
    void setDescrizioneAtt(String d);
    
    void setGiudizio(String g);
    
    void setSrcDocResoconto(String src);
    
    void setStudente(Studente s);
    
    void setTirocinio(Tirocinio t);
}
