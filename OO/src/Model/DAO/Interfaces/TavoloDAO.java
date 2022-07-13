package Model.DAO.Interfaces;

import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface TavoloDAO {

    Tavolo getTavoloById(int id);

    ArrayList<Tavolo> getAllTavoli();
    ArrayList<Tavolo> getAllTavoliByRistorante(String nomeRistorante);
    ArrayList<Tavolo> getAllTavoliBySala(String nomeSala);

    void createTavolo(Tavolo tavolo);
    void updateTavolo(Tavolo tavolo);
    void deleteTavolo(Tavolo tavolo);
}
