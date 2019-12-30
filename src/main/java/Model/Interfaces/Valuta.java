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
public interface Valuta {
    int getPunteggio();
    
    Azienda getAzienda();
    
    Studente getStudente();
    
    void setPunteggio(int p);
    
    void setAzienda(Azienda a);
    
    void setStudente(Studente s);
}
