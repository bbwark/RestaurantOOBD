package Model.DAO_Interfaces;

import Model.DTO.Cameriere;
import Model.DTO.Tavolata;

import java.util.ArrayList;

public interface CameriereDAO {

    String getNomeCameriere();
    String getCognomeCameriere();
    ArrayList<Tavolata> getPrenotazioniCameriere();

    void setNomeCameriere();
    void setCognomeCameriere();
    void setPrenotazioniCameriere();

    ArrayList<Cameriere> getAllCamerieri();
    ArrayList<Cameriere> getAllCamerieriByRistorante();
    ArrayList<Cameriere> getAllCamerieriBySala();
    ArrayList<Cameriere> getAllCamerieriByTavolo();
    ArrayList<Cameriere> getAllCamerieriByPrenotazione();

    void deleteCameriere();
    void addCameriere();
    void updateCameriere();
}
