package Model.DAO.Interfaces;

import Model.DTO.*;

import java.util.ArrayList;

public interface CameriereDAO {

    Cameriere getCameriereById(int id);

    ArrayList<Cameriere> getAllCamerieri();
    ArrayList<Cameriere> getAllCamerieriByRistorante(String nomeRistorante);
    ArrayList<Cameriere> getAllCamerieriBySala(int id);
    ArrayList<Cameriere> getAllCamerieriByTavolo(int id);
    ArrayList<Cameriere> getAllCamerieriByTavolata(int id);

    void createCameriere(Cameriere cameriere, Ristorante ristorante);
    void updateCameriere(Cameriere cameriere, Ristorante ristorante);
    void deleteCameriere(Cameriere cameriere);
}
