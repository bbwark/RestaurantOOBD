package Model.DAO.Interfaces;

import Model.DTO.Cameriere;
import Model.DTO.Cliente;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;
import java.util.Date;

public interface TavolataDAO {

    Date getDataArrivoById();
    ArrayList<Cameriere> getCamerieriPrenotazioneById();
    Tavolo getTavoloPrenotazioneById();
    ArrayList<Cliente> getClientiPrenotazioneById();
    int getId();

    boolean setDataArrivoById();
    boolean setCamerieriPrenotazioneById();
    boolean setTavoloPrenotazioneById();
    boolean setClientiPrenotazioneById();

    ArrayList<Tavolata> getAllPrenotazioni();
    ArrayList<Tavolata> getAllPrenotazioniByRistorante();
    ArrayList<Tavolata> getAllPrenotazioniBySala();
    ArrayList<Tavolata> getAllPrenotazioniByTavolo();
    ArrayList<Tavolata> getAllPrenotazioniByCameriere();
    ArrayList<Tavolata> getAllPrenotazioniByCliente();

    boolean createPrenotazione();
    boolean updatePrenotazione();
    boolean deletePrenotazione();
}
