package Model.DAO.Interfaces;

import Model.DTO.Cliente;
import Model.DTO.Tavolata;

import java.util.ArrayList;

public interface ClienteDAO {

    String getNomeClienteById();
    String getCognomeClienteById();
    String getNumeroIDCardCliente();
    String getNumeroTelefonoClienteById();
    ArrayList<Tavolata> getPrenotazioniClienteById();

    boolean setNomeClienteById();
    boolean setCognomeClienteById();
    boolean setNumeroIDCardCliente();
    boolean setNumeroTelefonoClienteById();
    boolean setPrenotazioniClienteById();

    ArrayList<Cliente> getAllClienti();
    ArrayList<Cliente> getAllClientiByRistorante();
    ArrayList<Cliente> getAllClientiBySala();
    ArrayList<Cliente> getAllClientiByTavolo();
    ArrayList<Cliente> getAllClientiByPrenotazione();
    int getNumClienti();
    int getNumClientiByPrenotazione();

    boolean createCliente();
    boolean updateCliente();
    boolean deleteCliente();
}
