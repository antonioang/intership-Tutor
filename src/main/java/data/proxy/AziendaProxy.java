/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.DAO.PersonaDAO;
import Model.Impl.Azienda_imp;
import Model.Interfaces.Persona;
import Model.Interfaces.RichiestaTirocinio;
import Model.Interfaces.Utente;
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
    this.dataLayer = d;
    this.dirty = false;
    this.responsabile_tirocinioId = 0;
    this.utenteId = 0;
    }
        
       
        
    @Override
    public void setNome(String n){
        super.setNome(n);
        this.dirty = false;
    }
    
    @Override
    public void setRagioneSociale(String rs) {
       super.setRagioneSociale(rs);
       this.dirty = false;
    }

    @Override
    public void setIndirizzo(String i) {
        this.dirty = false;
    }

    @Override
    public void setCitta(String citta) {
        super.setCitta(citta);
        this.dirty = false;
    }

    @Override
    public void setCap(int cap) {
        this.dirty = false;
    }

    @Override
    public void setProvincia(String pr) {
        super.setProvincia(pr);
        this.dirty = false;
    }

    @Override
    public void setRapprLeg(String rl) {
        super.setRapprLeg(rl);
        this.dirty = false;
    }

    @Override
    public void setPiva(String iva) {
       super.setPiva(iva);
       this.dirty = false;
    }

    @Override
    public void setForoCompetente(String fr) {
        super.setForoCompetente(fr);
        this.dirty = false;
    }

    @Override
    public void setSrcDocPath(String src) {
        super.setSrcDocPath(src);
        this.dirty = false;
    }

    @Override
    public void setTematica(String t) {
        super.setTematica(t);
        this.dirty = false;
    }

    @Override
    public void setStatoConv(boolean st) {
        super.setStatoConv(st);
        this.dirty = false;
    }

    @Override
    public void setCorsoStudi(String cs) {
        super.setCorsoStudi(cs);
        this.dirty = false;
    }

    @Override
    public void setInizioConv(LocalDate dataI) {
        super.setInizioConv(dataI);
        this.dirty = false;
    }

    @Override
    public void setFineConv(LocalDate dataF) {
        super.setFineConv(dataF);
        this.dirty = false;
    }

    @Override
    public void setRespTirocini(int id) {
        super.setRespTirocini(id);
        this.dirty = false;
    }

    @Override
    public void setUtente(int u) {
        super.setUtente(u);
        this.utenteId = u;
        this.dirty = false;
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
        this.dirty = false;
    }
    
    //METODI DEL PROXY
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
        
}
