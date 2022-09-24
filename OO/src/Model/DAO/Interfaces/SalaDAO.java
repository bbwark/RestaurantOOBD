package Model.DAO.Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface SalaDAO {

    Sala getSalaByNome(String nomeSala);
    Sala getSalaById(int id);
    Sala getSalaByTavolo(Tavolo tavolo);

    ArrayList<Sala> getAllSale();
    ArrayList<Sala> getAllSaleByRistorante(String nomeRistorante);

    void createSala(Sala sala, Ristorante ristorante);
    void updateSala(Sala sala);
    void deleteSala(Sala sala);
}
