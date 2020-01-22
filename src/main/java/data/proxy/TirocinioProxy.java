/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Tirocinio_imp;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class TirocinioProxy extends Tirocinio_imp{
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int azienda_id;
    protected int persona_id;
    
    public TirocinioProxy(DataLayer d){
        this.dataLayer = d;
        this.dirty = false;
        this.azienda_id = 0;
        this.persona_id = 0;
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
        this.dirty = true;
    }

    @Override
    public void setLuogo(String l) {
        super.setLuogo(l);
        this.dirty = true;
    }

    @Override
    public void setSettore(String s) {
        super.setSettore(s);
        this.dirty = true;
    }

    @Override
    public void setOrari(String o) {
        super.setOrari(o);
        this.dirty = true;
    }

    @Override
    public void setDurata(int d) {
        super.setDurata(d);
        this.dirty = true;
    }

    @Override
    public void setTitolo(String t) {
        super.setTitolo(t);
        this.dirty = true;
    }

    @Override
    public void setObiettivo(String ob) {
        super.setObiettivo(ob);
        this.dirty = true;
    }

    @Override
    public void setModalita(String m) {
        super.setModalita(m);
        this.dirty = true;
    }

    @Override
    public void setFacilitazioni(String f) {
        super.setFacilitazioni(f);
        this.dirty = true;
    }

    @Override
    public void setVisibile(boolean vb) {
        super.setVisibile(vb);
        this.dirty = true;
    }

    @Override
    public void setAzienda(int id_azienda) {
        super.setAzienda(id_azienda);
        this.azienda_id = id_azienda;
        this.dirty = true;
    }

    @Override
    public void setTutoreTirocinio(int id_tutore) {
        super.setTutoreTirocinio(id_tutore);
        this.persona_id = id_tutore;
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
