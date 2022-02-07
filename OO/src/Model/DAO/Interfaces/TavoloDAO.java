package Model.DAO.Interfaces;

import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface TavoloDAO {

    int getMaxAvventoriById();
    ArrayList<Tavolo> getTavoliAdiacentiById();
    Sala getSalaTavoloById();
    ArrayList<Tavolata> getPrenotazioniTavoloById();
    int getIdTavolo();

    boolean setMaxAvventoriById();
    boolean setTavoliAdiacentiById();
    boolean setSalaTavoloById();
    boolean setPrenotazioniTavoloById();

    ArrayList<Tavolo> getAllTavoli();
    ArrayList<Tavolo> getAllTavoliByRistorante();
    ArrayList<Tavolo> getAllTavoliBySala();

    boolean createTavolo();
    boolean updateTavolo();
    boolean deleteTavolo();
}
