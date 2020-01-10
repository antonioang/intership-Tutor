/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Impl;

import Model.Interfaces.Persona;

/**
 *
 * @author matti
 */
public class Persona_imp implements Persona{
    
    private String nome, cognome, email;
    private int telefono, tipo, id;
    
    public Persona_imp(){
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.telefono = 0;
        this.tipo = 0;
        this.id = 0;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public int getTelefono() {
        return this.telefono;
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
    public void setNome(String n) {
        this.nome = n;
    }

    @Override
    public void setCognome(String c) {
        this.cognome = c;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setTelefono(int t) {
        this.telefono = t;
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
