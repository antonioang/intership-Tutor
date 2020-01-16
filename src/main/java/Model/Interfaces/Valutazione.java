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
public interface Valutazione {
    int getPunteggio();
    
    int getAzienda();
    
    int getStudente();
    
    void setPunteggio(int p);
    
    void setAzienda(int a);
    
    void setStudente(int s);
}
