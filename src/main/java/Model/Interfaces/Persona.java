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
    
    int getTelefono();
    
    boolean getTipo();
    
    //setter
    void setNome(String n);
    
    void setCognome(String c);
    
    void setEmail(String email);
    
    void setTelefono(int t);
    
    void setTipo(boolean tipo);
}
