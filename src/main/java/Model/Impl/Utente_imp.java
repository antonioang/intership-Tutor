/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Utente;
import java.sql.ResultSet;

/**
 *
 * @author matti
 */
public class Utente_imp implements Utente {
    
    private String username, password, email;
    private int id, tipo;
    
    public Utente_imp(){
        this.username = "";
        this.password = "";
        this.email = "";
        this.tipo = 0;
        this.id = 0;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public int getTipo() {
        return this.tipo;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setUsername(String user) {
        this.username = user;
    }

    @Override
    public void setPassword(String psw) {
       this.password = psw;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    
}
