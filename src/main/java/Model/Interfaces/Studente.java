/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interfaces;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public interface Studente {
    String getNome();
    
    String getCognome();
    
    String getCodFiscale();
    
    LocalDate getDataNascita();
    
    String getCittaNascita();
    
    String getProvinciaNascita();
    
    String getCittaResidenza();
    
    String getProvinciaResidenza();
    
    int getCapResidenza();
    
    int getTelefono();
    
    String getCorsoLaurea();
    
    boolean getHandicap();
    
    Persona getPersona();
    
    Tirocinio getTirocinio();
    
    void setNome(String n);
    
    void setCognome(String c);
    
    void setCodFiscale(String cf);
    
    void setDataNascita(LocalDate d);
    
    void setCittaNascita(String cn);
    
    void setProvinciaNascita(String pn);
    
    void setCittaResidenza(String cr);
    
    void setProvinciaResidenza(String pr);
    
    void setCapResidenza(int cap);
    
    void setTelefono(int t);
    
    void setCorsoLaurea(String cl);
    
    void setHandicap(boolean handicap);
    
    void setPersona(Persona p);
    
    void setTirocinio(Tirocinio t);
    
    void setUtente(Utente ut);
}
