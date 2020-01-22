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
    private String nome;
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
    private int u;
    private int p;
    
    public Azienda_imp(){
        this.id = 0;
        this.nome = "";
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
        this.u = 0;
        this.p = 0;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getNome() {
        return this.nome;
    }
    
    @Override
    public String getRagioneSociale() {
        return this.ragioneSociale;
    }

    @Override
    public String getIndirizzo() {
        return this.indirizzo;
    }

    @Override
    public String getCitta() {
        return this.citta;
    }

    @Override
    public int getCap() {
        return this.cap;
    }

    @Override
    public String getProvincia() {
        return this.provincia;
    }

    @Override
    public String getRapprLeg() {
        return this.rapprLeg;
    }

    @Override
    public String getPiva() {
        return this.piva;
    }

    @Override
    public String getForoCompetente() {
        return this.foroCompetente;
    }

    @Override
    public String getSrcDocPath() {
        return this.srcDocPath;
    }

    @Override
    public String getTematica() {
        return this.tematica;
    }

    @Override
    public boolean getStatoConv() {
        return this.statoConv;
    }

    @Override
    public String getCorsoStudi() {
        return this.corsoStudi;
    }

    @Override
    public LocalDate getInizioConv() {
        return this.inizioConv;
    }

    @Override
    public LocalDate getFineConv() {
        return this.fineConv;
    }
    
    @Override
    public void setNome(String n){
        this.nome = n;
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
    public int getUtente() {
        return this.u;
    }

    @Override
    public void setRespTirocini(int id) {
        this.rappresentante_tirocini = id;
    }

    @Override
    public void setUtente(int u) {
        this.u = u;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
}
