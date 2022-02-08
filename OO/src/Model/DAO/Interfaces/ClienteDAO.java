package Model.DAO.Interfaces;

import Model.DTO.Cliente;
import Model.DTO.Tavolata;

import java.util.ArrayList;

public interface ClienteDAO {

    String getNomeClienteById(String id);
    String getCognomeClienteById(String id);
    String getNumeroIDCardCliente();
    String getNumeroTelefonoClienteById(String id);
    ArrayList<Tavolata> getPrenotazioniClienteById(String id);

    boolean setNomeClienteById(String id, String nome);
    boolean setCognomeClienteById(String id, String cognome);
    boolean setNumeroIDCardClienteById(String id, String idCard);
    boolean setNumeroTelefonoClienteById(String id, String numTelefono);
    boolean setPrenotazioniClienteById(String id, ArrayList<Tavolata> prenotazioni);

    ArrayList<Cliente> getAllClienti();
    ArrayList<Cliente> getAllClientiByRistorante(int idRistorante);
    ArrayList<Cliente> getAllClientiBySala(String nomeSala);
    ArrayList<Cliente> getAllClientiByTavolo(int idTavolo);
    ArrayList<Cliente> getAllClientiByPrenotazione(int idPrenotazione);
    int getNumClientiByRistorante(int idRistorante);
    int getNumClientiByPrenotazione(int idPrenotazione);

    boolean createCliente();
    boolean updateCliente();
    boolean deleteCliente();
}
