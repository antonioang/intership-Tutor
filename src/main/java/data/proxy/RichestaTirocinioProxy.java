/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.RichiestaTirocinio_imp;
import framework.data.DataLayer;
import java.sql.Date;

/**
 *
 * @author jacopo
 */
public class RichestaTirocinioProxy extends RichiestaTirocinio_imp {
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int tutoreUniversitario_id;
    protected int studente_id;
    protected int tirocinio_id;
    
    public RichestaTirocinioProxy(DataLayer d){
        this.dirty = false;
        this.dataLayer = d;
        this.tutoreUniversitario_id = 0;
        this.studente_id = 0;
        this.tirocinio_id = 0;
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
        this.dirty = true;
    }

    @Override
    public void setSrcDocCandid(String src) {
        super.setSrcDocCandid(src);
        this.dirty = true;
    }

    @Override
    public void setDottorato(String d) {
        super.setDottorato(d);
        this.dirty = true;
    }

    @Override
    public void setSpecializzazione(String s) {
        super.setSpecializzazione(s);
        this.dirty = true;
    }

    @Override
    public void setLaurea(String l) {
        super.setLaurea(l);
        this.dirty = true;
    }

    @Override
    public void setDiploma(String d) {
        super.setDiploma(d);
        this.dirty = true;
    }

    @Override
    public void setDataInizio(Date di) {
        super.setDataInizio(di);
        this.dirty = true;
    }

    @Override
    public void setDataFine(Date df) {
        super.setDataFine(df);
        this.dirty = true;
    }

    @Override
    public void setStatoCandidatura(int sc) {
        super.setStatoCandidatura(sc);
        this.dirty = true;
    }

    @Override
    public void setCfu(int cfu) {
        super.setCfu(cfu);
        this.dirty = true;
    }

    @Override
    public void setStudente(int st) {
        super.setStudente(st);
        this.studente_id = st;
        this.dirty = true;
    }

    @Override
    public void setTirocinio(int tr) {
        super.setTirocinio(tr);
        this.tirocinio_id = tr;
        this.dirty = true;
    }

    @Override
    public void setTutoreUniversitario(int tu) {
        super.setTutoreUniversitario(tu);
        this.tutoreUniversitario_id = tu;
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
