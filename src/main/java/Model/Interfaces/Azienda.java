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
public interface Azienda {
    //getter
    String getRagioneSociale();
    
    String getIndirizzo();
    
    String getCitta();
    
    int getCap();
    
    String getProvincia();
    
    String getRapprLeg();
    
    int getPiva();
    
    String getForoCompetente();
    
    String getSrcDocPath();
    
    String getTematica();
    
    boolean getStatoConv();
    
    String getCorsoStudi();
    
    Date getInizioConv();
    
    Date getFineConv();
    
    Persona getPersona();
    
    //setter
    void setRagioneSociale(String rs);
    
    void setIndirizzo(String i);
    
    void setCitta(String citta);
     
    void setCap(int cap);
    
    void setProvincia(String pr);
    
    void setRapprLeg(String rl);
    
    void setPiva(int iva);
    
    void setForoCompetente(String fr);
    
    void setSrcDocPath(String src);
    
    void setTematica(String t);
    
    void setStatoConv(boolean st);
    
    void setCorsoStudi(String cs);
    
    void setInizioConv(Date dataI);
    
    void setFineConv(Date dataF);
    
    void setPersona (Persona p);
}
