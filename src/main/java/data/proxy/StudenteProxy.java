/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Studente_imp;
import Model.Interfaces.Utente;
import framework.data.DataLayer;
import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public class StudenteProxy extends Studente_imp {
    protected boolean dirty;
    protected DataLayer dataLayer;
    protected int utenteId;
    
    public StudenteProxy(DataLayer d){
        this.dataLayer = d;
        this.dirty = false;
        this.utenteId = 0;
    }
    
    @Override
    public void setNome(String n) {
        super.setNome(n);
        this.dirty = true;
    }

    @Override
    public void setCognome(String c) {
        super.setCognome(c);
        this.dirty = true;
    }

    @Override
    public void setCodFiscale(String cf) {
        super.setCodFiscale(cf);
        this.dirty = true;
    }

    @Override
    public void setDataNascita(LocalDate d) {
        super.setDataNascita(d);
        this.dirty = true;
    }

    @Override
    public void setCittaNascita(String cn) {
        super.setCittaNascita(cn);
        this.dirty = true;
    }

    @Override
    public void setProvinciaNascita(String pn) {
        super.setProvinciaNascita(pn);
        this.dirty = true;
    }

    @Override
    public void setCittaResidenza(String cr) {
        super.setCittaResidenza(cr);
        this.dirty = true;
    }

    @Override
    public void setProvinciaResidenza(String pr) {
        super.setProvinciaResidenza(pr);
        this.dirty = true;
    }

    @Override
    public void setCapResidenza(int cap) {
        super.setCapResidenza(cap);
        this.dirty = true;
    }

    @Override
    public void setTelefono(String t) {
        super.setTelefono(t);
        this.dirty = true;
    }

    @Override
    public void setCorsoLaurea(String cl) {
        super.setCorsoLaurea(cl);
        this.dirty = true;
    }

    @Override
    public void setHandicap(boolean handicap) {
        super.setHandicap(handicap);
        this.dirty = true;
    }

    @Override
    public void setUtente(Utente ut) {
        super.setUtente(ut);
        this.utenteId = ut.getId();
        this.dirty = true;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        this.dirty = true;
    }
    
    //METODI DEL PROXY
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
   
    
}
