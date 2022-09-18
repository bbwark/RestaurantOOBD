package Model.DAO.Interfaces;

import Model.DTO.*;

import java.util.ArrayList;

public interface ClienteDAO {

    Cliente getClienteById (String id);

    ArrayList<Cliente> getAllClienti();
    ArrayList<Cliente> getAllClientiByRistorante(String nomeRistorante);
    ArrayList<Cliente> getAllClientiBySala(int id);
    ArrayList<Cliente> getAllClientiByTavolo(int id);
    ArrayList<Cliente> getAllClientiByTavolata(int id);

    void createCliente(Cliente cliente);
    void updateCliente(Cliente cliente);
    void updateCliente (Cliente cliente, String oldIdCard);
    void deleteCliente(Cliente cliente);
}
