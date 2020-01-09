/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interfaces;

import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public interface Studente {
    
    int getId();
            
    String getNome();
    
    String getCognome();
    
    String getCodFiscale();
    
    LocalDate getDataNascita();
    
    String getCittaNascita();
    
    String getProvinciaNascita();
    
    String getCittaResidenza();
    
    String getProvinciaResidenza();
    
    int getCapResidenza();
    
    String getTelefono();
    
    String getCorsoLaurea();
    
    boolean getHandicap();
            
    Utente getUtente();
    
    void setId(int id);
    
    void setNome(String n);
    
    void setCognome(String c);
    
    void setCodFiscale(String cf);
    
    void setDataNascita(LocalDate d);
    
    void setCittaNascita(String cn);
    
    void setProvinciaNascita(String pn);
    
    void setCittaResidenza(String cr);
    
    void setProvinciaResidenza(String pr);
    
    void setCapResidenza(int cap);
    
    void setTelefono(String t);
    
    void setCorsoLaurea(String cl);
    
    void setHandicap(boolean handicap);
            
    void setUtente(Utente ut);
}
