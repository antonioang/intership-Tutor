/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.proxy;

import Model.Impl.Test_Impl;
import framework.data.DataLayer;

/**
 *
 * @author jacopo
 */
public class TestProxy extends Test_Impl{
    protected boolean dirty;
    protected DataLayer dataLayer;
    
    public TestProxy(DataLayer d){
        super();
        this.dirty = false;
        this.dataLayer = d;
    }
    
     @Override
    public void setTestString(String stringa) {
        super.setTestString(stringa);
        this.dirty = true;
    }
    
    @Override
    public void setId(int i){
        super.setId(i);
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
