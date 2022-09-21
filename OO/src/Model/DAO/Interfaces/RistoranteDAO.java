package Model.DAO.Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;

import java.util.ArrayList;

public interface RistoranteDAO {

    Ristorante getRistoranteByNome(String nomeRistorante);
    Ristorante getRistoranteById(int id);
    Ristorante getRistoranteBySala(Sala sala);

    ArrayList<Ristorante> getAllRistoranti();

    void createRistorante(Ristorante ristorante);
    void updateRistorante(Ristorante ristorante, String oldName);
    void deleteRistorante(Ristorante ristorante);
}