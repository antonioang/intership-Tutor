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
    
    int getId();
    
    String getLuogo();
    
    String getSettore();
    
    String getOrari();
    
    String getDurata();
    
    String getTitolo();
    
    String getObiettivo();
    
    String getModalita();
    
    String getFacilitazioni();
    
    boolean getApprovato();
    
    int getAzienda();
    
    int getTutoreTirocini();
    
    void setId(int id);
    
    void setLuogo(String l);
    
    void setSettore(String s);
    
    void setOrari(String o);
    
    void setDurata(String d);
    
    void setTitolo(String t);
    
    void setObiettivo(String ob);
    
    void setModalita(String m);
    
    void setFacilitazioni(String f);
    
    void setApprovato(boolean at);
    
    void setAzienda(int id_azienda);
    
    void setTutoreTirocini(int id_tutore);
    
}
