/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Persona_imp;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class PersonaProxy extends Persona_imp{
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public PersonaProxy(DataLayer d){
        super();
        this.dirty = false;
        this.dataLayer = d;
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
    public void setEmail(String email) {
        super.setEmail(email);
        this.dirty = true;
    }

    @Override
    public void setTelefono(String t) {
        super.setTelefono(t);
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
    
    //metodi per proxy
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }
}
