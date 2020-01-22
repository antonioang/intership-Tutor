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
    int getOre();
    
    String getDescrizioneAtt();
    
    String getGiudizio();
    
    int getStudente();
    
    int getTirocinio();
    
    void setOre(int ore);
    
    void setDescrizioneAtt(String d);
    
    void setGiudizio(String g);
    
    void setStudente(int s);
    
    void setTirocinio(int t);
}
