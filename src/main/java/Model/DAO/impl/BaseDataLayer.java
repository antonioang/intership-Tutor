/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.SQLException;
import javax.sql.DataSource;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Studente;
import Model.Interfaces.Test;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;

/**
 *
 * @author Antonio
 */
public class BaseDataLayer extends DataLayer {
    
    public BaseDataLayer(DataSource datasource) throws SQLException {
        super(datasource);
    }
        
    //inizializza i riferimenti ai file DAO
    @Override
    public void init() throws DataLayerException{
        registerDAO(Test.class, new TestDAO_Impl(this));
        registerDAO(Utente.class, new UtenteDAO_Impl(this));
        registerDAO(Azienda.class, new AziendaDAO_imp(this));
        registerDAO(Studente.class, new StudenteDAO_imp(this));
        registerDAO(Persona.class, new PersonaDAO_imp(this));
        registerDAO(Tirocinio.class, new TirocinioDAO_imp(this));
    }
    
    //metodo che restituisce il dao del testDAO
    public TestDAO_Impl getTestDAO() {
        return (TestDAO_Impl) getDAO(Test.class);
    }
    
    //metodo che restituisce il dao del testDAO
    public UtenteDAO_Impl getUtenteDAO() {
        return (UtenteDAO_Impl) getDAO(Utente.class);
    }
    
    public AziendaDAO_imp getAziendaDAO(){
        return (AziendaDAO_imp) getDAO(Azienda.class);
    }
    
    public StudenteDAO_imp getStudenteDAO(){
        return (StudenteDAO_imp) getDAO(Studente.class);
    }
}
