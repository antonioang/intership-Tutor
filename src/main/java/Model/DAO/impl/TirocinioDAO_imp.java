/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.TirocinioDAO;
import Model.Impl.Tirocinio_imp;
import Model.Interfaces.Tirocinio;
import framework.data.DAO;
import framework.data.DataLayer;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacopo
 */
public class TirocinioDAO_imp extends DAO implements TirocinioDAO {
    
    PreparedStatement getTirociniAttivi, getTirocini, getTirocinio;
    PreparedStatement addTirocinio, updTirocinio, delTirocinio;
    PreparedStatement updTirocinioApprovato, searchTirocinio;
    
    public TirocinioDAO_imp(DataLayer d) {
        super(d);
    }
    
    @Override
    public void init() throws DataLayerException{
        try {
            getTirociniAttivi = connection.prepareStatement("SELECT * FROM tirocinio WHERE azienda = ? AND approvato = ?");
            getTirocini = connection.prepareStatement("SELECT * FROM tirocinio WHERE azienda = ?");
            getTirocinio = connection.prepareStatement("SELECT * FROM tirocinio WHERE id_tirocinio = ?");
            addTirocinio = connection.prepareStatement("INSERT INTO tirocinio\n" +
                "(luogo, settore, orari, durata, titolo, obiettivo, modalita, facilitazioni, azienda, tutore_tirocini, approvato)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            updTirocinio = connection.prepareStatement("UPDATE tirocinio\n" +
                "SET luogo=?, settore=?, orari=?, durata=?, titolo=?, obiettivo=?, modalita=?, facilitazioni=?, azienda=?, tutore_tirocini=?, approvato=?\n" +
                "WHERE id_tirocinio=?");   
            delTirocinio = connection.prepareStatement("DELETE FROM tirocinio WHERE id_tirocinio=0;");
            updTirocinioApprovato = connection.prepareStatement("UPDATE tirocinio SET approvato=? WHERE id_tirocinio=?");
            searchTirocinio = connection.prepareStatement("SELECT * FROM tirocinio JOIN azienda ON tirocinio.azienda = azienda.id_azienda WHERE tirocinio.luogo like ? "
                    + "AND tirocinio.settore like ? AND tirocinio.titolo like ? AND tirocinio.obiettivi like ? AND tirocinio.durata like ? AND azienda.corso_studio like ?");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzaizione degli statement", ex);
        }
    }
    
    @Override
    public Tirocinio createTirocinio() {
        return new Tirocinio_imp();
    }

    @Override
    public Tirocinio createTirocinio(ResultSet rs) throws DataLayerException {
        Tirocinio t = createTirocinio();
        try { 
            t.setLuogo(rs.getString("luogo"));
            t.setSettore(rs.getString("settore"));
            t.setOrari(rs.getString("orari"));
            t.setDurata(rs.getString("durata"));
            t.setTitolo(rs.getString("titolo"));
            t.setObiettivo(rs.getString("obiettivo"));
            t.setModalita(rs.getString("modalita"));
            t.setFacilitazioni(rs.getString("facilitazioni"));
            t.setApprovato(rs.getBoolean("approvato"));
            t.setAzienda(rs.getInt("azienda"));
            t.setTutoreTirocini(rs.getInt("tutore_tirocini"));
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la creazione del tirocinio", ex);
        }
        return t;
    }

    @Override
    public List<Tirocinio> getTirociniAttivi(int az, boolean attiva) throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            getTirociniAttivi.setInt(1, az);
            getTirociniAttivi.setBoolean(2, attiva);
            ResultSet rs = getTirociniAttivi.executeQuery();
            
            while(rs.next()){
                lista.add(createTirocinio(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tirocini attivi", ex);
        }
        return lista;
    }

    @Override
    public List<Tirocinio> getTirocini(int az) throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            getTirocini.setInt(1, az);
            ResultSet rs = getTirocini.executeQuery();
            while(rs.next()){
                lista.add(createTirocinio(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tirocini dell'azienda");
        }
        return lista;
    }

    @Override
    public Tirocinio getTirocinio(int id) throws DataLayerException {
        try {
            getTirocinio.setInt(1, id);
            ResultSet rs = getTirocinio.executeQuery();
            if(rs.next()){
                return createTirocinio(rs);
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore dutante il recupero del tirocinio");
        }
        return null;
    }

    @Override
    public int addTirocinio(Tirocinio t) throws DataLayerException {
        try {
            addTirocinio.setString(1, t.getLuogo());
            addTirocinio.setString(2, t.getSettore());
            addTirocinio.setString(3, t.getOrari());
            addTirocinio.setString(4, t.getDurata());
            addTirocinio.setString(5, t.getTitolo());
            addTirocinio.setString(6, t.getObiettivo());
            addTirocinio.setString(7, t.getModalita());
            addTirocinio.setString(8, t.getFacilitazioni());
            addTirocinio.setInt(9, t.getAzienda());
            addTirocinio.setInt(10, t.getTutoreTirocini());
            addTirocinio.setBoolean(11, t.getApprovato());
            
            if (addTirocinio.executeUpdate() == 1) {
                //per leggere la chiave generata dal database
                //per il record appena inserito, usiamo il metodo
                //getGeneratedKeys sullo statement.
                //to read the generated record key from the database
                //we use the getGeneratedKeys method on the same statement
                try (ResultSet keys = addTirocinio.getGeneratedKeys()) {
                    //il valore restituito è un ResultSet con un record
                    //per ciascuna chiave generata (uno solo nel nostro caso)
                    //the returned value is a ResultSet with a distinct record for
                    //each generated key (only one in our case)
                    if (keys.next()) {
                        //i campi del record sono le componenti della chiave
                        //(nel nostro caso, un solo intero)
                        //the record fields are the key componenets
                        //(a single integer in our case)
                        t.setId(keys.getInt(1));
                        //aggiornaimo la chiave in caso di inserimento
                        //after an insert, uopdate the object key
                    }
                }
                return 1;
            } else { 
                return 0;
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inserimento del tirocinio", ex);
        }
    }

    @Override
    public int updTirocinio(Tirocinio t) throws DataLayerException {
        try {
            updTirocinio.setString(1, t.getLuogo());
            updTirocinio.setString(2, t.getSettore());
            updTirocinio.setString(3, t.getOrari());
            updTirocinio.setString(4, t.getDurata());
            updTirocinio.setString(5, t.getTitolo());
            updTirocinio.setString(6, t.getObiettivo());
            updTirocinio.setString(7, t.getModalita());
            updTirocinio.setString(8, t.getFacilitazioni());
            updTirocinio.setInt(9, t.getAzienda());
            updTirocinio.setInt(10, t.getTutoreTirocini());
            updTirocinio.setBoolean(11, t.getApprovato());
            updTirocinio.setInt(12, t.getId());
            return updTirocinio.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento del tirocinio", ex);
        }
    }
    
    @Override
    public int delTirocinio(int id) throws DataLayerException {
        try {
            delTirocinio.setInt(1, id);
            return delTirocinio.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la cancellazione del Tirocinio", ex);
        }
    }
    
    @Override
    public int updTirocinioApprovato(int i, boolean bln) throws DataLayerException {
        try {
            updTirocinioApprovato.setBoolean(1, bln);
            updTirocinioApprovato.setInt(2, i);
            return updTirocinioApprovato.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento dello stato del tirocinio", ex);
        }
    }

    @Override
    public List<Tirocinio> searchTirocinio(String durata, String titolo, String facilitazioni, String luogo, String settore, String obiettivi, String corsoStudio) throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            searchTirocinio.setString(1, luogo);
            searchTirocinio.setString(2, settore);
            searchTirocinio.setString(3, titolo);
            searchTirocinio.setString(4, obiettivi);
            searchTirocinio.setString(5, durata);
            searchTirocinio.setString(6, corsoStudio);
            
            ResultSet rs = searchTirocinio.executeQuery();
            while(rs.next()){
                lista.add(createTirocinio(rs));
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tirocini", ex);
        }
        return lista;
    }

}
