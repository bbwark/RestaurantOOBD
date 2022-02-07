package Model.DAO.Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface SalaDAO {

    String getNomeSala();
    Sala getSalaByNome();
    int getNumeroTavoliSala();
    Ristorante getRistoranteByNomeSala();
    ArrayList<Tavolo> getTavoliSalaByNome();

    boolean setNomeSala();
    boolean setNumeroTavoli();
    boolean setRistoranteSalaByNome();
    boolean setTavoliSala();

    ArrayList<Sala> getAllSale();
    ArrayList<Sala> getAllSaleByRistorante();

    void createSala();
    void updateSala();
    void deleteSala();
}
