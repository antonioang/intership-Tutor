/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Utente;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public class Azienda_imp implements Azienda {
    
    private int id;
    private String ragioneSociale;
    private String indirizzo;
    private String citta;
    private int cap;
    private String provincia;
    private String rapprLeg;
    private String piva;
    private String foroCompetente;
    private String srcDocPath;
    private String tematica;
    private boolean statoConv;
    private String corsoStudi;
    private LocalDate inizioConv;
    private LocalDate fineConv;
    private int rappresentante_tirocini;
    private Utente u;
    
    public Azienda_imp(){
        this.id = 0;
        this.ragioneSociale = "";
        this.indirizzo = "";
        this.citta = "";
        this.cap = 0;
        this.provincia = "";
        this.rapprLeg = "";
        this.piva = "";
        this.foroCompetente = "";
        this.srcDocPath = "";
        this.tematica = "";
        this.statoConv = false;
        this.corsoStudi = "";
        this.inizioConv = null;
        this.fineConv = null;
        this.rappresentante_tirocini = 0;
        this.u = null;
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
    public String getPiva() {
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
    public LocalDate getInizioConv() {
        return inizioConv;
    }

    @Override
    public LocalDate getFineConv() {
        return fineConv;
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
    public void setPiva(String iva) {
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
    public void setInizioConv(LocalDate dataI) {
        this.inizioConv = dataI;
    }

    @Override
    public void setFineConv(LocalDate dataF) {
        this.fineConv = dataF;
    }

    @Override
    public int getRespTirocini() {
        return this.rappresentante_tirocini;
    }

    @Override
    public Utente getUtente() {
        return this.u;
    }

    @Override
    public void setRespTirocini(int id) {
        this.rappresentante_tirocini = id;
    }

    @Override
    public void setUtente(Utente u) {
        this.u = u;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
}
