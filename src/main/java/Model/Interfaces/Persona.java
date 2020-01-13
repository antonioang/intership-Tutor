/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Interfaces;

/**
 *
 * @author jacopo
 */
public interface Persona {
    //getter
    String getNome();
    
    String getCognome();
    
    String getEmail();
    
    String getTelefono();
    
    int getTipo();
    
    int getId();
    
    //setter
    void setNome(String n);
    
    void setCognome(String c);
    
    void setEmail(String email);
    
    void setTelefono(String t);
    
    void setTipo(int tipo);
    
    void setId(int id);
    
}
