package Model.DAO.Interfaces;

import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface TavoloDAO {

    Tavolo getTavoloById(int id);
    Tavolo getTavoloByTavolata(Tavolata tavolata);

    ArrayList<Tavolo> getAllTavoli();
    ArrayList<Tavolo> getAllTavoliByRistorante(String nomeRistorante);
    ArrayList<Tavolo> getAllTavoliBySala(int id);

    void createTavolo(Tavolo tavolo, Sala sala);
    void updateTavolo(Tavolo tavolo, Sala sala);
    void deleteTavolo(Tavolo tavolo);
}
