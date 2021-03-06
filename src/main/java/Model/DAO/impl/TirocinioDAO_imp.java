/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.DAO.TirocinioDAO;
import Model.Interfaces.Tirocinio;
import data.proxy.TirocinioProxy;
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

    PreparedStatement getTirociniVisibili, getTirocini, getTirocinio, getLatestTirocini;
    PreparedStatement addTirocinio, updTirocinio, delTirocinio;
    PreparedStatement updTirocinioVisibile, searchTirocinio, getTirociniByStatoRichieste;

    public TirocinioDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataLayerException {
        try {
            getTirociniVisibili = connection.prepareStatement("SELECT * FROM tirocinio WHERE azienda = ? AND visibile = ?");
            getTirocini = connection.prepareStatement("SELECT * FROM tirocinio WHERE azienda = ?");
            getTirocinio = connection.prepareStatement("SELECT * FROM tirocinio WHERE id_tirocinio = ?");
            addTirocinio = connection.prepareStatement("INSERT INTO tirocinio\n"
                    + "(luogo, settore, orari, durata, titolo, obiettivo, modalita, facilitazioni, azienda, tutore_tirocinio, visibile)\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            updTirocinio = connection.prepareStatement("UPDATE tirocinio\n"
                    + "SET luogo=?, settore=?, orari=?, durata=?, titolo=?, obiettivo=?, modalita=?, facilitazioni=?, azienda=?, tutore_tirocinio=?, visibile=?\n"
                    + "WHERE id_tirocinio=?");
            delTirocinio = connection.prepareStatement("DELETE FROM tirocinio WHERE id_tirocinio=0;");
            updTirocinioVisibile = connection.prepareStatement("UPDATE tirocinio SET visibile=? WHERE id_tirocinio=?");
            searchTirocinio = connection.prepareStatement("SELECT * FROM tirocinio JOIN azienda ON tirocinio.azienda = azienda.id_azienda WHERE tirocinio.luogo like ? "
                    + "OR tirocinio.settore like ? OR tirocinio.titolo like ? OR tirocinio.obiettivo like ? OR tirocinio.durata like ? OR azienda.corso_studi like ? OR facilitazioni like ?");
            getTirociniByStatoRichieste = connection.prepareStatement("SELECT * FROM tirocinio JOIN richiesta_tirocinio ON richiesta_tirocinio.tirocinio = id_tirocinio WHERE richiesta_tirocinio.studente = ? && richiesta_tirocinio.stato_candidatura = ?");
            getLatestTirocini = connection.prepareStatement("SELECT * FROM tirocinio ORDER BY id_tirocinio DESC LIMIT 10");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inizializzaizione degli statement", ex);
        }
    }

    @Override
    public TirocinioProxy createTirocinio() {
        return new TirocinioProxy(getDataLayer());
    }

    @Override
    public TirocinioProxy createTirocinio(ResultSet rs) throws DataLayerException {
        TirocinioProxy t = createTirocinio();
        try {
            t.setLuogo(rs.getString("luogo"));
            t.setSettore(rs.getString("settore"));
            t.setOrari(rs.getString("orari"));
            t.setDurata(rs.getInt("durata"));
            t.setTitolo(rs.getString("titolo"));
            t.setObiettivo(rs.getString("obiettivo"));
            t.setModalita(rs.getString("modalita"));
            t.setFacilitazioni(rs.getString("facilitazioni"));
            t.setVisibile(rs.getBoolean("visibile"));
            t.setAzienda(rs.getInt("azienda"));
            t.setTutoreTirocinio(rs.getInt("tutore_tirocinio"));
            t.setId(rs.getInt("id_tirocinio"));
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la creazione del tirocinio", ex);
        }
        return t;
    }

    @Override
    public List<Tirocinio> getTirociniVisibili(int az, boolean visibile) throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            getTirociniVisibili.setInt(1, az);
            getTirociniVisibili.setBoolean(2, visibile);
            ResultSet rs = getTirociniVisibili.executeQuery();
            try {
                while (rs.next()) {
                    lista.add(createTirocinio(rs));
                }
            } finally {
                rs.close();
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
            try {
                while (rs.next()) {
                    lista.add(createTirocinio(rs));
                }
            } finally {
                rs.close();
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
            try {
                if (rs.next()) {
                    return createTirocinio(rs);
                }
            } finally {
                rs.close();
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
            addTirocinio.setString(5, t.getTitolo());
            addTirocinio.setString(6, t.getObiettivo());
            addTirocinio.setString(7, t.getModalita());
            addTirocinio.setString(8, t.getFacilitazioni());
            addTirocinio.setInt(4, t.getDurata());
            addTirocinio.setInt(9, t.getAzienda());
            addTirocinio.setInt(10, t.getTutoreTirocinio());
            addTirocinio.setBoolean(11, t.getVisibile());

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
            updTirocinio.setString(5, t.getTitolo());
            updTirocinio.setString(6, t.getObiettivo());
            updTirocinio.setString(7, t.getModalita());
            updTirocinio.setString(8, t.getFacilitazioni());
            updTirocinio.setInt(4, t.getDurata());
            updTirocinio.setInt(9, t.getAzienda());
            updTirocinio.setInt(10, t.getTutoreTirocinio());
            updTirocinio.setBoolean(11, t.getVisibile());
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
    public int updTirocinioVisibile(int i, boolean bln) throws DataLayerException {
        try {
            updTirocinioVisibile.setBoolean(1, bln);
            updTirocinioVisibile.setInt(2, i);
            return updTirocinioVisibile.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'aggiornamento dello stato del tirocinio", ex);
        }
    }

    @Override
    public List<Tirocinio> searchTirocinio(int durata, String titolo, String facilitazioni, String luogo, String settore, String obiettivi, String corsoStudio) throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            searchTirocinio.setString(1, luogo);
            searchTirocinio.setString(2, settore);
            searchTirocinio.setString(3, titolo);
            searchTirocinio.setString(4, obiettivi);
            searchTirocinio.setInt(5, durata);
            searchTirocinio.setString(6, corsoStudio);
            searchTirocinio.setString(7, facilitazioni);
            ResultSet rs = searchTirocinio.executeQuery();
            try {
                while (rs.next()) {
                    lista.add(createTirocinio(rs));
                }
            } finally {
                rs.close();
            }

        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero dei tirocini", ex);
        }
        return lista;
    }

    @Override
    public void destroy() throws DataLayerException {
        try {
            addTirocinio.close();
            delTirocinio.close();
            getTirocini.close();
            getTirociniVisibili.close();
            getTirocinio.close();
            searchTirocinio.close();
            updTirocinio.close();
            updTirocinioVisibile.close();
            getTirociniByStatoRichieste.close();
            getLatestTirocini.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public List<Tirocinio> getTirociniByStatoRichieste(int id_studente, int stato) throws DataLayerException {
        try {
            List<Tirocinio> lista = new ArrayList();
            getTirociniByStatoRichieste.setInt(1, id_studente);
            getTirociniByStatoRichieste.setInt(2, stato);
            ResultSet rs = getTirociniByStatoRichieste.executeQuery();
            try {
                while (rs.next()) {
                    lista.add(createTirocinio(rs));
                }
            } finally {
                rs.close();
            }
            return lista;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero delle richieste tirocinio", ex);
        }
    }

    @Override
    public void storeTirocinio(Tirocinio tirocinio) throws DataLayerException {

        if (tirocinio.getId() > 0) {//Controllo se esiste un istanza dell'oggetto
            if (tirocinio instanceof TirocinioProxy && !((TirocinioProxy) tirocinio).isDirty()) {
                return;//se l'oggetto è un istanza di utente proxy e dirty è false usciamo dal metodo
            }
            updTirocinio(tirocinio);
        } else {
            addTirocinio(tirocinio);
        }
        if (tirocinio instanceof TirocinioProxy) {
            ((TirocinioProxy) tirocinio).setDirty(false);
        }
    }

    @Override
    public List<Tirocinio> getLatestTirocini() throws DataLayerException {
        List<Tirocinio> lista = new ArrayList();
        try {
            ResultSet rs = getLatestTirocini.executeQuery();
            try {
                while (rs.next()) {
                    lista.add(createTirocinio(rs));
                }
            } finally {
                rs.close();
            }
            return lista;
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero degli ultimi tirocini inseriti ", ex);
        }

    }
}
