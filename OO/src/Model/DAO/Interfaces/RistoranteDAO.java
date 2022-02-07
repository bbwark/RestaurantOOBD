package Model.DAO.Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;

import java.util.ArrayList;

public interface RistoranteDAO {

    String getNomeByID(int id);
    ArrayList<Sala> getSaleRistoranteById();

    boolean setNomeRistoranteById();
    boolean setSaleRistoranteById();

    ArrayList<Ristorante> getAllRistoranti();

    boolean createRistorante();
    boolean updateRistorante();
    boolean deleteRistorante();
}
