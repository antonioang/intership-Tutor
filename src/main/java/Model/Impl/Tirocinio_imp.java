/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Tirocinio;

/**
 *
 * @author matti
 */
public class Tirocinio_imp implements Tirocinio{
    
    private String luogo, settore, orari, durata, titolo, obiettivo, modalita, facilitazioni;
    private int id, tutore_tirocini, azienda;
    public boolean approvato;
    
    public Tirocinio_imp (){
        this.luogo = "";
        this.settore = "";
        this.orari = "";
        this.durata = "";
        this.titolo = "";
        this.obiettivo = "";
        this.modalita = "";
        this.facilitazioni = "";
        this.id = 0;
        this.azienda = 0;
        this.tutore_tirocini = 0;
        this.approvato = false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getLuogo() {
        return this.luogo;
    }

    @Override
    public String getSettore() {
        return this.settore;
    }

    @Override
    public String getOrari() {
        return this.orari;
    }

    @Override
    public String getDurata() {
        return this.durata;
    }

    @Override
    public String getTitolo() {
        return this.titolo;
    }

    @Override
    public String getObiettivo() {
        return this.obiettivo;
    }

    @Override
    public String getModalita() {
        return this.modalita;
    }

    @Override
    public String getFacilitazioni() {
        return this.facilitazioni;
    }

    @Override
    public boolean getApprovato() {
        return this.approvato;
    }

    @Override
    public int getAzienda() {
        return this.azienda;
    }

    @Override
    public int getTutoreTirocinio() {
        return this.tutore_tirocini;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setLuogo(String l) {
        this.luogo = l;
    }

    @Override
    public void setSettore(String s) {
        this.settore = s;
    }

    @Override
    public void setOrari(String o) {
        this.orari = o;
    }

    @Override
    public void setDurata(String d) {
        this.durata = d;
    }

    @Override
    public void setTitolo(String t) {
        this.titolo = t;
    }

    @Override
    public void setObiettivo(String ob) {
        this.obiettivo = ob;
    }

    @Override
    public void setModalita(String m) {
        this.modalita = m;
    }

    @Override
    public void setFacilitazioni(String f) {
        this.facilitazioni = f;
    }

    @Override
    public void setApprovato(boolean at) {
        this.approvato = at;
    }

    @Override
    public void setAzienda(int id_azienda) {
        this.azienda = id_azienda;
    }

    @Override
    public void setTutoreTirocinio(int id_tutore) {
        this.tutore_tirocini = id_tutore;
    }
    
}
