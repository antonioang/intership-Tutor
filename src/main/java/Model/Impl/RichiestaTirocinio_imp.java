/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.RichiestaTirocinio;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author matti
 */
public class RichiestaTirocinio_imp implements RichiestaTirocinio{
    
    private String srcDocCandid, dottorato, specializzazione, laurea, diploma;
    private LocalDate data_inizio, data_fine;
    private int stato, cfu, tutore_uni, studente, tirocinio, id;
    
    public RichiestaTirocinio_imp(){
        this.srcDocCandid = "";
        this.dottorato = "";
        this.specializzazione = "";
        this.laurea = "";
        this.diploma = "";
        this.data_inizio = null;
        this.data_fine = null;
        this.stato = 0;
        this.cfu = 0;
        this.tutore_uni = 0;
        this.studente = 0;
        this.tirocinio = 0;
        this.id = 0;     
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getSrcDocCandid() {
        return this.srcDocCandid;
    }

    @Override
    public String getDottorato() {
        return this.dottorato;
    }

    @Override
    public String getSpecializzazione() {
        return this.specializzazione;
    }

    @Override
    public String getLaurea() {
        return this.laurea;
    }

    @Override
    public String getDiploma() {
        return this.diploma;
    }

    @Override
    public LocalDate getDataInizio() {
        return this.data_inizio;
    }

    @Override
    public LocalDate getDataFine() {
        return this.data_fine;
    }

    @Override
    public int getStudente() {
        return this.studente;
    }

    @Override
    public int getTirocinio() {
        return this.tirocinio;
    }

    @Override
    public int getTutoreUniversitario() {
        return this.tutore_uni;
    }

    @Override
    public int getStatoCandidatura() {
        return this.stato;
    }

    @Override
    public int getCfu() {
        return this.cfu;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setSrcDocCandid(String src) {
        this.srcDocCandid = src;
    }

    @Override
    public void setDottorato(String d) {
        this.dottorato = d;
    }

    @Override
    public void setSpecializzazione(String s) {
        this.specializzazione = s;
    }

    @Override
    public void setLaurea(String l) {
        this.laurea = l;
    }

    @Override
    public void setDiploma(String d) {
        this.diploma = d;
    }

    @Override
    public void setDataInizio(LocalDate di) {
        this.data_inizio = di;
    }

    @Override
    public void setDataFine(LocalDate df) {
        this.data_fine = df;
    }

    @Override
    public void setStatoCandidatura(int sc) {
        this.stato = sc;
    }

    @Override
    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    @Override
    public void setStudente(int st) {
        this.studente = st;
    }

    @Override
    public void setTirocinio(int tr) {
        this.tirocinio = tr;
    }

    @Override
    public void setTutoreUniversitario(int tu) {
        this.tutore_uni = tu;
    }
    
}
