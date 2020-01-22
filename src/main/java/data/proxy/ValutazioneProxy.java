/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Valutazione_imp;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class ValutazioneProxy extends Valutazione_imp {
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int azienda_id;
    protected int studente_id;
    
    public ValutazioneProxy(DataLayer d){
        this.dirty = false;
        this.dataLayer = d;
        this.azienda_id = 0;
        this.studente_id = 0;
    }
    @Override
    public void setPunteggio(int p) {
        super.setPunteggio(p);
        this.dirty = true;
    }

    @Override
    public void setAzienda(int a) {
        super.setAzienda(a);
        this.azienda_id = a;
        this.dirty = true;
    }

    @Override
    public void setStudente(int s) {
        super.setStudente(s);
        this.studente_id = s;
        this.dirty = true;
    }
    //METODI DEL PROXY
    //PROXY-ONLY METHODS
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
}
