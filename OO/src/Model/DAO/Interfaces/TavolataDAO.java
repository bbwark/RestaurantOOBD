package Model.DAO.Interfaces;

import Model.DTO.*;

import java.util.ArrayList;
import java.util.Date;

public interface TavolataDAO {

    Tavolata getTavolataById(int id);

    ArrayList<Tavolata> getAllTavolate();
    ArrayList<Tavolata> getAllTavolateByRistorante(String nomeRistorante);
    ArrayList<Tavolata> getAllTavolateBySala(String nomeSala);
    ArrayList<Tavolata> getAllTavolateByTavolo(int id);
    ArrayList<Tavolata> getAllTavolateByCameriere(int id);
    ArrayList<Tavolata> getAllTavolateByCliente(String id);

    void createPrenotazione(Tavolata tavolata);
    void updatePrenotazione(Tavolata tavolata);
    void deletePrenotazione(Tavolata tavolata);
}
