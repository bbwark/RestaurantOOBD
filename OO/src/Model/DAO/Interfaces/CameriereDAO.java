package Model.DAO.Interfaces;

import Model.DTO.Cameriere;
import Model.DTO.Tavolata;

import java.util.ArrayList;

public interface CameriereDAO {

    String getNomeCameriereById();
    String getCognomeCameriereById();
    ArrayList<Tavolata> getPrenotazioniCameriereById();
    int getId();

    boolean setNomeCameriereById();
    boolean setCognomeCameriereById();
    boolean setPrenotazioniCameriereById();

    ArrayList<Cameriere> getAllCamerieri();
    ArrayList<Cameriere> getAllCamerieriByRistorante();
    ArrayList<Cameriere> getAllCamerieriBySala();
    ArrayList<Cameriere> getAllCamerieriByTavolo();
    ArrayList<Cameriere> getAllCamerieriByPrenotazione();

    boolean createCameriere();
    boolean updateCameriere();
    boolean deleteCameriere();
}
