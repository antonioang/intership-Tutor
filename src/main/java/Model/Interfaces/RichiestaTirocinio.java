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
public interface RichiestaTirocinio {
    
    int getId();
    
    String getSrcDocCandid();
    
    String getDottorato();
    
    String getSpecializzazione();
    
    String getLaurea();
    
    String getDiploma();
    
    Date getDataInizio();
    
    Date getDataFine();
    
    int getStudente();
    
    int getTirocinio();
    
    int getTutoreUniversitario();
    
    int getStatoCandidatura();
    
    int getCfu();
    
    void setId(int id);
    
    void setSrcDocCandid(String src);
    
    void setDottorato(String d);
    
    void setSpecializzazione(String s);
    
    void setLaurea(String l);
    
    void setDiploma(String d);
    
    void setDataInizio(Date di);
    
    void setDataFine(Date df);
    
    void setStatoCandidatura(int sc);
    
    void setCfu(int cfu);
    
    void setStudente(int st);
    
    void setTirocinio(int tr);
    
    void setTutoreUniversitario(int tu);
}
