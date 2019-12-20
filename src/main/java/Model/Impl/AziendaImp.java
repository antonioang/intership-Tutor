/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import java.sql.Date;

/**
 *
 * @author jacopo
 */
public class AziendaImp implements Azienda {
    private String ragioneSociale;
    private String indirizzo;
    private String citta;
    private int cap;
    private String provincia;
    private String rapprLeg;
    private int piva;
    private String foroCompetente;
    private String srcDocPath;
    private String tematica;
    private boolean statoConv;
    private String corsoStudi;
    private Date inizioConv;
    private Date fineConv;
    private Persona p;
    
    public AziendaImp(){
    this.ragioneSociale = "";
    this.indirizzo = "";
    this.citta = "";
    this.cap = 0;
    this.provincia = "";
    this.rapprLeg = "";
    this.piva = 0;
    this.foroCompetente = "";
    this.srcDocPath = "";
    this.tematica = "";
    this.statoConv = false;
    this.corsoStudi = "";
    this.inizioConv = null;
    this.fineConv = null;
    this.p = null;
}

    @Override
    public String getRagioneSociale() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return ragioneSociale;
    }

    @Override
    public String getIndirizzo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return indirizzo;
    }

    @Override
    public String getCitta() {
        return citta;
    }

    @Override
    public int getCap() {
        return cap;
    }

    @Override
    public String getProvincia() {
        return provincia;
    }

    @Override
    public String getRapprLeg() {
        return rapprLeg;
    }

    @Override
    public int getPiva() {
        return piva;
    }

    @Override
    public String getForoCompetente() {
        return foroCompetente;
    }

    @Override
    public String getSrcDocPath() {
        return srcDocPath;
    }

    @Override
    public String getTematica() {
        return tematica;
    }

    @Override
    public boolean getStatoConv() {
        return statoConv;
    }

    @Override
    public String getCorsoStudi() {
        return corsoStudi;
    }

    @Override
    public Date getInizioConv() {
        return inizioConv;
    }

    @Override
    public Date getFineConv() {
        return fineConv;
    }

    @Override
    public Persona getPersona() {
        return p;
    }

    @Override
    public void setRagioneSociale(String rs) {
       this.ragioneSociale = rs;
    }

    @Override
    public void setIndirizzo(String i) {
        this.indirizzo = i;
    }

    @Override
    public void setCitta(String citta) {
        this.citta = citta;
    }

    @Override
    public void setCap(int cap) {
        this.cap = cap;
    }

    @Override
    public void setProvincia(String pr) {
        this.provincia = pr;
    }

    @Override
    public void setRapprLeg(String rl) {
        this.rapprLeg = rl;
    }

    @Override
    public void setPiva(int iva) {
       this.piva = iva;
    }

    @Override
    public void setForoCompetente(String fr) {
        this.foroCompetente = fr;
    }

    @Override
    public void setSrcDocPath(String src) {
        this.srcDocPath = src;
    }

    @Override
    public void setTematica(String t) {
        this.tematica = t;
    }

    @Override
    public void setStatoConv(boolean st) {
        this.statoConv = st;
    }

    @Override
    public void setCorsoStudi(String cs) {
        this.corsoStudi = cs;
    }

    @Override
    public void setInizioConv(Date dataI) {
        this.inizioConv = dataI;
    }

    @Override
    public void setFineConv(Date dataF) {
        this.fineConv = dataF;
    }

    @Override
    public void setPersona(Persona p) {
        this.p = p;
    }
    
}
