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
public interface Utente {
    String getUsername();
    
    String getPassword();
    
    String getEmail();
    
    int getTipo();
    
    int getId();
    
    void setUsername(String user);
    
    void setPassword(String psw);
    
    void setEmail(String email);
    
    void setTipo(int tipo);
    
    void setId(int id);
    
}
