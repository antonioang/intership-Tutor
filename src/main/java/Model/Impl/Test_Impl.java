/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Test;

/**
 *
 * @author matti
 */
public class Test_Impl implements Test{
    
    private String prova;
    private int id;
    
    public Test_Impl(){
        this.prova = "";
    }
    
    @Override
    public String getTestString(){
        return this.prova;
    }
    
    @Override
    public void setTestString(String stringa) {
        this.prova = stringa;
    }
    
    @Override
    public int getId(){
        return this.id;
    }
    
    @Override
    public void setId(int i){
        this.id = i;
    }
    
}
