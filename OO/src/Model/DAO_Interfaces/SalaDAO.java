package Model.DAO_Interfaces;

import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import java.util.ArrayList;

public interface SalaDAO {

    String getNomeSala();
    int getNumeroTavoliSala();
    Ristorante getRistoranteSala();
    ArrayList<Tavolo> getTavoliSala();

    void setNomeSala();
    void setNumeroTavoli();
    void setRistoranteSala();
    void setTavoliSala();

    ArrayList<Sala> getAllSale();
    ArrayList<Sala> getAllSaleByRistorante();

    void deleteSala();
    void addSala();
    void updateSala();
}
