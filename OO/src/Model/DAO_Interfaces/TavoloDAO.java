package Model.DAO_Interfaces;

import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface TavoloDAO {

    int getMaxAvventori();
    ArrayList<Tavolo> getTavoliAdiacenti();
    Sala getSalaTavolo();
    ArrayList<Tavolata> getPrenotazioniTavolo();

    void setMaxAvventori();
    void setTavoliAdiacenti();
    void setSalaTavolo();
    void setPrenotazioniTavolo();

    ArrayList<Tavolo> getAllTavoli();
    ArrayList<Tavolo> getAllTavoliByRistorante();
    ArrayList<Tavolo> getAllTavoliBySala();

    void deleteTavolo();
    void addTavolo();
    void updateTavolo();
}
