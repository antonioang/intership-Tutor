/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Rapporto;
import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public class Rapporto_imp implements Rapporto {
    
    private int ore;
    private String descrizione_att;
    private String giudizio;
    private int st;
    private int t;
    private LocalDate data;
    
    public Rapporto_imp(){
        this.ore = 0;
        this.descrizione_att = "";
        this.giudizio = "";
        this.st = 0;
        this.t = 0;
        this.data = null;
}

    @Override
    public int getOre() {
        return this.ore;
    }

    @Override
    public String getDescrizioneAtt() {
        return this.descrizione_att;
    }

    @Override
    public String getGiudizio() {
        return this.giudizio;
    }

    @Override
    public int getStudente() {
        return this.st;
    }

    @Override
    public int getTirocinio() {
        return this.t;
    }
    
    @Override
    public LocalDate getData() {
        return this.data;
    }

    @Override
    public void setOre(int ore) {
        this.ore = ore;
    }

    @Override
    public void setDescrizioneAtt(String d) {
        this.descrizione_att = d;
    }

    @Override
    public void setGiudizio(String g) {
        this.giudizio = g;
    }

    @Override
    public void setStudente(int s) {
        this.st = s;
    }

    @Override
    public void setTirocinio(int t) {
        this.t = t;
    }

    @Override
    public void setData(LocalDate data) {
        this.data = data;
    }
    
}
