package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.ClienteDAO;
import Model.DTO.Cliente;
import Model.DTO.Tavolata;

import java.sql.*;
import java.util.ArrayList;

public class ClienteImpl implements ClienteDAO {

    private DatabasePostgresConnection databasePostgresConnection;
    private Connection connection;
    private boolean connectionSucceeded;

    @Override
    public String getNomeClienteById(String id) {
        String result = null;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Nome\" FROM \"Cliente\" WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();
                result = rs.getString(1);
                st.close();
                rs.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databasePostgresConnection.closeConnection();
        }
        return result;
    }

    @Override
    public String getCognomeClienteById(String id) {
        String result = null;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Cognome\" FROM \"Cliente\" WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();
                result = rs.getString(1);
                st.close();
                rs.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databasePostgresConnection.closeConnection();
        }
        return result;
    }

    @Override
    public String getNumeroIDCardCliente() {
    }

    @Override
    public String getNumeroTelefonoClienteById(String id) {
        String result = null;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Numero_Tel\" FROM \"Cliente\" WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();
                result = rs.getString(1);
                st.close();
                rs.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            databasePostgresConnection.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<Tavolata> getPrenotazioniClienteById(String id) {
    }

    @Override
    public boolean setNomeClienteById(String id, String nome) {
        boolean result = false;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Nome\" = '?' WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, nome);
                st.setString(2, id);
                st.executeUpdate();
                result = true;
                st.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public boolean setCognomeClienteById(String id, String cognome) {
        boolean result = false;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Cognome\" = '?' WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, cognome);
                st.setString(2, id);
                st.executeUpdate();
                result = true;
                st.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public boolean setNumeroIDCardClienteById(String id, String idCard) {
        boolean result = false;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Numero_ID_Card\" = '?' WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, idCard);
                st.setString(2, id);
                st.executeUpdate();
                result = true;
                st.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public boolean setNumeroTelefonoClienteById(String id, String numTelefono) {
        boolean result = false;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Numero_Tel\" = '?' WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, numTelefono);
                st.setString(2, id);
                st.executeUpdate();
                result = true;
                st.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public boolean setPrenotazioniClienteById(String id, ArrayList<Tavolata> prenotazioni) {
        boolean result = false;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                for (Tavolata prenotazione : prenotazioni){
                    PreparedStatement st = connection.prepareStatement("UPDATE \"ClienteTavolata\" SET \"Codice_Prenotazione\" = '?' WHERE \"Numero_ID_Card\" = ?");
                    st.setString(1, Integer.toString(prenotazione.getId()));
                    st.setString(2, id);
                    st.executeUpdate();
                    result = true;
                    st.close();
                    connection.close();
                }

            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }


    @Override
    public ArrayList<Cliente> getAllClienti() {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
        Cliente temp = new Cliente();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Cliente\"");
                while(rs.next()){
                    temp.setNome(rs.getString(1));
                    temp.setCognome(rs.getString(2));
                    temp.setNumeroIDCard(rs.getString(3));
                    temp.setNumeroTelefono(rs.getString(4));
                    result.add(temp);
                }
                st.close();
                rs.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public ArrayList<Cliente> getAllClientiByRistorante(int idRistorante) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
        Cliente temp = new Cliente();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", " +
                        "\"Cliente\".\"Numero_ID_Card\", \"Cliente\".\"Numero_Tel\" FROM \"Ristorante\" INNER JOIN \"Sala\" ON (\"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\") " +
                        "INNER JOIN \"Tavolo\" ON (\"Sala\".\"Nome_Sala\" = \"Tavolo\".\"Nome_Sala\") INNER JOIN \"Tavolata\" ON (\"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\") " +
                        "INNER JOIN \"ClienteTavolata\" ON (\"Tavolata\".\"Codice_Prenotazione\" = \"ClienteTavolata\".\"Codice_Prenotazione\") INNER JOIN \"Cliente\" ON (\"ClienteTavolata\".\"Numero_ID_Card\" " +
                        "= \"Cliente\".\"Numero_ID_Card\") WHERE \"Ristorante\".\"ID_Ristorante\" = ?");
                st.setString(1, Integer.toString(idRistorante));
                ResultSet rs = st.executeQuery();
                while(rs.next()){
                    temp.setNome(rs.getString(1));
                    temp.setCognome(rs.getString(2));
                    temp.setNumeroIDCard(rs.getString(3));
                    temp.setNumeroTelefono(rs.getString(4));
                    result.add(temp);
                }
                st.close();
                rs.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public ArrayList<Cliente> getAllClientiBySala(String nomeSala) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
        Cliente temp = new Cliente();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", " +
                        "\"Cliente\".\"Numero_ID_Card\", \"Cliente\".\"Numero_Tel\" FROM  \"Sala\" INNER JOIN \"Tavolo\" ON (\"Sala\".\"Nome_Sala\" " +
                        "= \"Tavolo\".\"Nome_Sala\") INNER JOIN \"Tavolata\" ON (\"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\") " +
                        "INNER JOIN \"ClienteTavolata\" ON (\"Tavolata\".\"Codice_Prenotazione\" = \"ClienteTavolata\".\"Codice_Prenotazione\") " +
                        "INNER JOIN \"Cliente\" ON (\"ClienteTavolata\".\"Numero_ID_Card\" = \"Cliente\".\"Numero_ID_Card\") WHERE \"Sala\".\"Nome_Sala\" = '\"?\"'");
                st.setString(1, nomeSala);
                ResultSet rs = st.executeQuery();
                while(rs.next()){
                    temp.setNome(rs.getString(1));
                    temp.setCognome(rs.getString(2));
                    temp.setNumeroIDCard(rs.getString(3));
                    temp.setNumeroTelefono(rs.getString(4));
                    result.add(temp);
                }
                st.close();
                rs.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
        return result;
    }

    @Override
    public ArrayList<Cliente> getAllClientiByTavolo(int idTavolo) {
        return null;
    }

    @Override
    public ArrayList<Cliente> getAllClientiByPrenotazione(int idPrenotazione) {
        return null;
    }

    @Override
    public int getNumClientiByRistorante(int idRistorante) {
        return 0;
    }

    @Override
    public int getNumClientiByPrenotazione(int idPrenotazione) {
        return 0;
    }

    @Override
    public boolean createCliente() {
        return false;
    }

    @Override
    public boolean updateCliente() {
        return false;
    }

    @Override
    public boolean deleteCliente() {
        return false;
    }
}