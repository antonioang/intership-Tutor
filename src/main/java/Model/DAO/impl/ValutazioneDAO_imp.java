/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO.impl;

import Model.Impl.Valutazione_imp;
import framework.data.DAO;
import framework.data.DataLayer;
import java.sql.ResultSet;
import java.util.List;
import Model.Interfaces.Valutazione;
import framework.data.DataLayerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.DAO.ValutazioneDAO;
import java.util.ArrayList;

/**
 *
 * @author jacopo
 */
public class ValutazioneDAO_imp extends DAO implements ValutazioneDAO {

    private PreparedStatement getValutazione, getValutazioneSt, getValutazioneAz;
    private PreparedStatement addValutazione, delValutazione;

    public ValutazioneDAO_imp(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataLayerException {
        try {
            getValutazione = connection.prepareStatement("SELECT * FROM valutazione_azienda WHERE studente = ? AND azienda = ?");
            getValutazioneSt = connection.prepareStatement("SELECT * FROM valutazione_azienda WHERE studente = ?");
            getValutazioneAz = connection.prepareStatement("SELECT * FROM valutazione_azienda WHERE azienda = ?");
            addValutazione = connection.prepareStatement("INSERT INTO valutazione_azienda (punteggio, studente, azienda) VALUES(?, ?, ?);");
            delValutazione = connection.prepareStatement("DELETE FROM valutazione_azienda WHERE studente = ? AND azienda = ?");
        } catch (SQLException ex) {
            throw new DataLayerException("Errore inizializzazione degli statement valutazione azienda", ex);
        }

    }

    @Override
    public int addValutazione(Valutazione v) throws DataLayerException {
        try {
            addValutazione.setInt(1, v.getPunteggio());
            addValutazione.setInt(2, v.getStudente());
            addValutazione.setInt(3, v.getAzienda());
            return addValutazione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante l'inserimento della valutazione", ex);
        }

    }

    @Override
    public List<Valutazione> getValutazioni(int az) throws DataLayerException {
        List<Valutazione> l = new ArrayList();
        try {
            getValutazioneAz.setInt(1, az);
            ResultSet rs = getValutazioneAz.executeQuery();
            try {
                while (rs.next()) {
                    l.add(createValutazione(rs));
                }
            } finally {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della valutazione azienda", ex);
        }
        return l;
    }

    @Override
    public List<Valutazione> getValutazioniByStudente(int st) throws DataLayerException {
        List<Valutazione> l = new ArrayList();
        try {
            getValutazioneSt.setInt(1, st);
            ResultSet rs = getValutazioneSt.executeQuery();
            try {
                while (rs.next()) {
                    l.add(createValutazione(rs));
                }
            } finally {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della valutazione studente", ex);
        }
        return l;
    }

    @Override
    public Valutazione createValutazione() {
        return new Valutazione_imp();
    }

    @Override
    public Valutazione createValutazione(ResultSet rs) throws DataLayerException {
        Valutazione v = createValutazione();
        try {
            v.setPunteggio(rs.getInt("punteggio"));
            v.setStudente(rs.getInt("studente"));
            v.setAzienda(rs.getInt("azienda"));
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la creazione della valutazione", ex);
        }
        return v;
    }

    @Override
    public Valutazione getValutazione(int id_az, int id_st) throws DataLayerException {
        try {
            getValutazione.setInt(2, id_az);
            getValutazione.setInt(1, id_st);
            ResultSet rs = getValutazione.executeQuery();
            try {
                if (rs.next()) {
                    return createValutazione(rs);
                }
            } finally {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della valutazione", ex);
        }
        return null;
    }

    @Override
    public int delValutazione(int id_az, int id_st) throws DataLayerException {
        try {
            delValutazione.setInt(1, id_az);
            delValutazione.setInt(2, id_st);
            delValutazione.executeUpdate();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante il recupero della valutazione", ex);
        }
        return 0;
    }

    @Override
    public void destroy() throws DataLayerException {
        try {
            delValutazione.close();
            getValutazione.close();
            getValutazioneAz.close();
            getValutazioneSt.close();
            delValutazione.close();
        } catch (SQLException ex) {
            throw new DataLayerException("Errore durante la chiusura degli statement", ex);
        }
        super.destroy();
    }

    @Override
    public void storeValutazione(Valutazione v) throws DataLayerException {

    }
}
