package Model.DAO_Interfaces;

import Model.DTO.Cliente;
import Model.DTO.Tavolata;

import java.util.ArrayList;

public interface ClienteDAO {

    String getNomeCliente();
    String getCognomeCliente();
    String getNumeroIDCardCliente();
    String getNumeroTelefonoCliente();
    ArrayList<Tavolata> getPrenotazioniCliente();

    void setNomeCliente();
    void setCognomeCliente();
    void setNumeroIDCardCliente();
    void setNumeroTelefonoCliente();
    void setPrenotazioniCliente();

    ArrayList<Cliente> getAllClienti();
    ArrayList<Cliente> getAllClientiByRistorante();
    ArrayList<Cliente> getAllClientiBySala();
    ArrayList<Cliente> getAllClientiByTavolo();
    ArrayList<Cliente> getAllClientiByPrenotazione();
    int getNumClienti();
    int getNumClientiByPrenotazione();

    void deleteCliente();
    void addCliente();
    void updateCliente();
}
