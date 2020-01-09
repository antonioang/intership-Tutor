/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Persona;
import Model.Interfaces.Studente;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;
import java.time.LocalDate;

/**
 *
 * @author matti
 */
public class Studente_imp implements Studente {
    
    private String nome, cognome, codFiscale, cittaNascita, provNascita, cittaRes, provRes, corsoLaurea, tel;
    private int id, capResidenza;
    private boolean handicap;
    private Tirocinio tirocinio;
    private LocalDate dataNascita;
    private Utente utente;
    
    public Studente_imp(){
        this.id = 0;
        this.nome = "";
        this.cognome = "";
        this.codFiscale = "";
        this.cittaNascita = "";
        this.provNascita = "";
        this.cittaRes = "";
        this.provRes = "";
        this.corsoLaurea = "";
        this.capResidenza = 0;
        this.tel = "";
        this.handicap = false;
        this.tirocinio = null;
        this.dataNascita = null;
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
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public String getCodFiscale() {
        return this.codFiscale;
    }

    @Override
    public LocalDate getDataNascita() {
        return this.dataNascita;
    }

    @Override
    public String getCittaNascita() {
        return this.cittaNascita;
    }

    @Override
    public String getProvinciaNascita() {
        return this.provNascita;
    }
    
    @Override
    public String getCittaResidenza() {
        return this.cittaRes;
    }

    @Override
    public String getProvinciaResidenza() {
        return this.provRes;
    }

    @Override
    public int getCapResidenza() {
        return this.capResidenza;
    }

    @Override
    public String getTelefono() {
        return this.tel;
    }

    @Override
    public String getCorsoLaurea() {
        return this.corsoLaurea;
    }

    @Override
    public boolean getHandicap() {
        return this.handicap;
    }
    
    @Override
    public Utente getUtente() {
        return this.utente;
    }

    @Override
    public void setNome(String n) {
        this.nome = n;
    }

    @Override
    public void setCognome(String c) {
        this.cognome = c;
    }

    @Override
    public void setCodFiscale(String cf) {
        this.codFiscale = cf;
    }

    @Override
    public void setDataNascita(LocalDate d) {
        this.dataNascita = d;
    }

    @Override
    public void setCittaNascita(String cn) {
        this.cittaNascita = cn;
    }

    @Override
    public void setProvinciaNascita(String pn) {
        this.provNascita = pn;
    }

    @Override
    public void setCittaResidenza(String cr) {
        this.cittaRes = cr;
    }

    @Override
    public void setProvinciaResidenza(String pr) {
        this.provRes = pr;
    }

    @Override
    public void setCapResidenza(int cap) {
        this.capResidenza = cap;
    }

    @Override
    public void setTelefono(String t) {
        this.tel = t;
    }

    @Override
    public void setCorsoLaurea(String cl) {
        this.corsoLaurea = cl;
    }

    @Override
    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    @Override
    public void setUtente(Utente ut) {
        this.utente = ut;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
