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
public interface Tirocinio {
    String getLuogo();
    
    String getSettore();
    
    String getOrari();
    
    String getDurata();
    
    String getTitolo();
    
    String getObiettivo();
    
    String getModalita();
    
    String getFalicitazioni();
    
    boolean getAttivo();
    
    Azienda getAzienda();
    
    Persona getPersona();
    
    void setLuogo(String l);
    
    void setSettore(String s);
    
    void setOrari(String o);
    
    void setDurata(String d);
    
    void setTitolo(String t);
    
    void setObiettivo(String ob);
    
    void setModalita(String m);
    
    void setFacilitazioni(String f);
    
    void setAttivo(boolean at);
    
    void setAzienda(Azienda a);
    
    void setPersona(Persona p);
}
