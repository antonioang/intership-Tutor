/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interfaces;

import java.sql.Date;

/**
 *
 * @author jacopo
 */
public interface Richiede {
    String getSrcDocCandid();
    
    String getDottorato();
    
    String getSpecializzazione();
    
    String getLaurea();
    
    String getDiploma();
    
    Date getDataInizio();
    
    Date getDataFine();
    
    boolean getStatoCandidatura();
    
    int getCfu();
    
    void setSrcDocCandid(String src);
    
    void setDottorato(String d);
    
    void setSpecializzazione(String s);
    
    void setLaurea(String l);
    
    void setDiploma(String d);
    
    void setDataInizio(Date di);
    
    void setDataFine(Date df);
    
    void setStatoCandidatura(boolean sc);
    
    void setCfu(int cfu);
}
