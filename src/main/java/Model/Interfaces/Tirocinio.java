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
    
    int getDurata();
    
    String getTitolo();
    
    String getObiettivo();
    
    String getModalita();
    
    boolean getFacilitazioni();
    
    boolean getVisibile();
    
    int getAzienda();
    
    int getTutoreTirocinio();
    
    void setId(int id);
    
    void setLuogo(String l);
    
    void setSettore(String s);
    
    void setOrari(String o);
    
    void setDurata(int d);
    
    void setTitolo(String t);
    
    void setObiettivo(String ob);
    
    void setModalita(String m);
    
    void setFacilitazioni(boolean f);
    
    void setVisibile(boolean vb);
    
    void setAzienda(int id_azienda);
    
    void setTutoreTirocinio(int id_tutore);
    
}
