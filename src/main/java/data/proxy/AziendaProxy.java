/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Azienda_imp;
import framework.data.DataLayer;
import java.time.LocalDate;

/**
 *
 * @author jacopo
 */
public class AziendaProxy extends Azienda_imp {
        
    protected int responsabile_tirocinioId;
    protected int utenteId;
    protected DataLayer dataLayer;
    protected boolean dirty;
    
    public AziendaProxy(DataLayer d){
        super();
        this.dataLayer = d;
        this.dirty = false;
        this.responsabile_tirocinioId = 0;
        this.utenteId = 0;
    }
        
       
        
    @Override
    public void setNome(String n){
        super.setNome(n);
        this.dirty = true;
    }
    
    @Override
    public void setRagioneSociale(String rs) {
       super.setRagioneSociale(rs);
       this.dirty = true;
    }

    @Override
    public void setIndirizzo(String i) {
        super.setIndirizzo(i);
        this.dirty = true;
    }

    @Override
    public void setCitta(String citta) {
        super.setCitta(citta);
        this.dirty = true;
    }

    @Override
    public void setCap(int cap) {
        super.setCap(cap);
        this.dirty = true;
    }

    @Override
    public void setProvincia(String pr) {
        super.setProvincia(pr);
        this.dirty = true;
    }

    @Override
    public void setRapprLeg(String rl) {
        super.setRapprLeg(rl);
        this.dirty = true;
    }

    @Override
    public void setPiva(String iva) {
       super.setPiva(iva);
       this.dirty = true;
    }

    @Override
    public void setForoCompetente(String fr) {
        super.setForoCompetente(fr);
        this.dirty = true;
    }

    @Override
    public void setSrcDocPath(String src) {
        super.setSrcDocPath(src);
        this.dirty = true;
    }

    @Override
    public void setTematica(String t) {
        super.setTematica(t);
        this.dirty = true;
    }

    @Override
    public void setStatoConv(boolean st) {
        super.setStatoConv(st);
        this.dirty = true;
    }

    @Override
    public void setCorsoStudi(String cs) {
        super.setCorsoStudi(cs);
        this.dirty = true;
    }

    @Override
    public void setInizioConv(LocalDate dataI) {
        super.setInizioConv(dataI);
        this.dirty = true;
    }

    @Override
    public void setFineConv(LocalDate dataF) {
        super.setFineConv(dataF);
        this.dirty = true;
    }

    @Override
    public void setRespTirocini(int id) {
        super.setRespTirocini(id);
        this.responsabile_tirocinioId = id;
        this.dirty = true;
    }

    @Override
    public void setUtente(int u) {
        super.setUtente(u);
        this.utenteId = u;
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
