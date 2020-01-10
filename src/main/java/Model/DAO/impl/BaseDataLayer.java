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
import Model.DAO.impl.TestDAO_imp;
import Model.Interfaces.Azienda;
import Model.Interfaces.Persona;
import Model.Interfaces.Rapporto;
import Model.Interfaces.Richiede;
import Model.Interfaces.Studente;
import Model.Interfaces.Test;
import Model.Interfaces.Tirocinio;
import Model.Interfaces.Utente;
import Model.Interfaces.Valuta;

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
        registerDAO(Test.class, new TestDAO_imp(this));
        registerDAO(Utente.class, new UtenteDAO_imp(this));
        registerDAO(Azienda.class, new AziendaDAO_imp(this));
        registerDAO(Studente.class, new StudenteDAO_imp(this));
        registerDAO(Persona.class, new PersonaDAO_imp(this));
        registerDAO(Tirocinio.class, new TirocinioDAO_imp(this));
        registerDAO(Rapporto.class, new RapportoDAO_imp(this));
        registerDAO(Richiede.class, new RichiedeDAO_imp(this));
        registerDAO(Valuta.class, new ValutaDAO_imp(this));
    }
    
    //metodo che restituisce il dao del test
    public TestDAO_imp getTestDAO() {
        return (TestDAO_imp) getDAO(Test.class);
    }
    
    //metodo che restituisce il dao dell'utente
    public UtenteDAO_imp getUtenteDAO() {
        return (UtenteDAO_imp) getDAO(Utente.class);
    }
    
    //metodo che restituisce il dao dello studente
    public StudenteDAO_imp getStudenteDAO() {
        return (StudenteDAO_imp) getDAO(Studente.class);
    }
    
    public AziendaDAO_imp getAziendaDAO(){
        return (AziendaDAO_imp) getDAO(Azienda.class);
    }
    
    public PersonaDAO_imp getPersonaDAO(){
        return (PersonaDAO_imp) getDAO(Persona.class);
    }
    
    public TirocinioDAO_imp getTirocinioDAO(){
        return (TirocinioDAO_imp) getDAO(Tirocinio.class);
    }
    
    public RapportoDAO_imp getRapportoDAO(){
        return (RapportoDAO_imp) getDAO(Rapporto.class);
    }
    
    public RichiedeDAO_imp getRichiedeDAO(){
        return (RichiedeDAO_imp) getDAO(Richiede.class);
    }
    
    public ValutaDAO_imp getValutaDAO(){
        return (ValutaDAO_imp) getDAO(Valuta.class);
    }
}
