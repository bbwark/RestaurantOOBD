package Model.DAO_Interfaces;

import Model.DTO.Cameriere;
import Model.DTO.Cliente;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;
import java.util.Date;

public interface TavolataDAO {

    Date getDataArrivo();
    ArrayList<Cameriere> getCamerieriPrenotazione();
    Tavolo getTavoloPrenotazione();
    ArrayList<Cliente> getClientiPrenotazione();

    void setDataArrivo();
    void setCamerieriPrenotazione();
    void setTavoloPrenotazione();
    void setClientiPrenotazione();

    ArrayList<Tavolata> getAllPrenotazioni();
    ArrayList<Tavolata> getAllPrenotazioniByRistorante();
    ArrayList<Tavolata> getAllPrenotazioniBySala();
    ArrayList<Tavolata> getAllPrenotazioniByTavolo();
    ArrayList<Tavolata> getAllPrenotazioniByCameriere();
    ArrayList<Tavolata> getAllPrenotazioniByCliente();

    void deletePrenotazione();
    void addPrenotazione();
    void updatePrenotazione();
}
