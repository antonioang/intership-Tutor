/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Utente_imp;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class UtenteProxy extends Utente_imp {
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public UtenteProxy(DataLayer d){
        super();
        this.dirty = false;
        this.dataLayer = d;
    }
    
    @Override
    public void setUsername(String user) {
        super.setUsername(user);
        this.dirty = true;
    }

    @Override
    public void setPassword(String psw) {
       super.setPassword(psw);
       this.dirty = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.dirty = true;
    }

    @Override
    public void setTipo(int tipo) {
        super.setTipo(tipo);
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

