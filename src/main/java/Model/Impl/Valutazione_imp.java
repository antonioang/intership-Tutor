/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Azienda;
import Model.Interfaces.Studente;
import Model.Interfaces.Valutazione;

/**
 *
 * @author jacopo
 */
public class Valutazione_imp implements Valutazione {
    private int punteggio;
    private int st;
    private int az;
    
    public Valutazione_imp(){
        this.punteggio = 0;
        this.st = 0;
        this.az = 0;
    }

    @Override
    public int getPunteggio() {
        return this.punteggio;
    }

    @Override
    public int getAzienda() {
        return this.az;
    }

    @Override
    public int getStudente() {
        return this.st;
    }

    @Override
    public void setPunteggio(int p) {
        this.punteggio = p;
    }

    @Override
    public void setAzienda(int a) {
        this.az = a;
    }

    @Override
    public void setStudente(int s) {
        this.st = s;
    }
    
    
}
