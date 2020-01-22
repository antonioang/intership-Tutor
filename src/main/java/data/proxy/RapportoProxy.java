/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Rapporto_imp;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class RapportoProxy extends Rapporto_imp {
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int studente_id;
    protected int tirocinio_id;
    
    public RapportoProxy(DataLayer d){
        this.dataLayer = d;
        this.dirty = false;
        this.studente_id = 0;
        this.tirocinio_id = 0;
    }
    @Override
    public void setOre(int ore) {
        super.setOre(ore);
        this.dirty = true;
    }

    @Override
    public void setDescrizioneAtt(String d) {
        super.setDescrizioneAtt(d);
        this.dirty = true;
    }

    @Override
    public void setGiudizio(String g) {
        super.setGiudizio(g);
        this.dirty = true;
    }

    @Override
    public void setSrcDocResoconto(String src) {
        super.setSrcDocResoconto(src);
        this.dirty = true;
    }

    @Override
    public void setStudente(int s) {
        super.setStudente(s);
        this.studente_id = s;
        this.dirty = true;
    }

    @Override
    public void setTirocinio(int t) {
        super.setTirocinio(t);
        this.tirocinio_id = t;
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
