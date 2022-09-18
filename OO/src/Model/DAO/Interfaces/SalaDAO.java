package Model.DAO.Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface SalaDAO {

    Sala getSalaByNome(String nomeSala);
    Sala getSalaById(int id);

    ArrayList<Sala> getAllSale();
    ArrayList<Sala> getAllSaleByRistorante(String nomeRistorante);

    void createSala(Sala sala);
    void updateSala(Sala sala);
    void deleteSala(Sala sala);
}
