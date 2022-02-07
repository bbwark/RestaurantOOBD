package Model.DAO_Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;

import java.util.ArrayList;

public interface RistoranteDAO {

    String getNomeRistorante();
    ArrayList<Sala> getSaleRistorante();

    void setNomeRistorante();
    void setSaleRistorante();

    ArrayList<Ristorante> getAllRistoranti();

    void deleteRistorante();
    void addRistorante();
    void updateRistorante();
}
