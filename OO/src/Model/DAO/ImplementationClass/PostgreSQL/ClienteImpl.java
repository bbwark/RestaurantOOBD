package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.ClienteDAO;
import Model.DTO.Cliente;
import Model.DTO.Tavolata;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class ClienteImpl implements ClienteDAO {

    private Connection connection;

    public ClienteImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Cliente getClienteById(String id) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Cliente\" WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                String nome = rs.getString(1);
                String cognome = rs.getString(2);
                String numeroIdCard = rs.getString(3);
                String numeroTel = rs.getString(4);

                Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTel);

                st.close();
                rs.close();
                return tempCliente;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public ArrayList<Cliente> getAllClienti() {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Cliente\"");
                while (rs.next()) {

                    String nome = rs.getString(1);
                    String cognome = rs.getString(2);
                    String numeroIdCard = rs.getString(3);
                    String numeroTelefono = rs.getString(4);

                    Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTelefono);

                    result.add(tempCliente);
                }
                st.close();
                rs.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public ArrayList<Cliente> getAllClientiByRistorante(String nomeRistorante) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", " +
                        "\"Cliente\".\"Numero_ID_Card\", \"Cliente\".\"Numero_Tel\" FROM \"Ristorante\" INNER JOIN \"Sala\" ON (\"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\") " +
                        "INNER JOIN \"Tavolo\" ON (\"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\") INNER JOIN \"Tavolata\" ON (\"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\") " +
                        "INNER JOIN \"Prenotazione\" ON (\"Tavolata\".\"Codice_Prenotazione\" = \"Prenotazione\".\"Codice_Prenotazione\") INNER JOIN \"Cliente\" ON (\"Prenotazione\".\"Numero_ID\" " +
                        "= \"Cliente\".\"Numero_ID_Card\") WHERE \"Ristorante\".\"Nome_Ristorante\" = ?");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    String nome = rs.getString(1);
                    String cognome = rs.getString(2);
                    String numeroIdCard = rs.getString(3);
                    String numeroTelefono = rs.getString(4);

                    Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTelefono);

                    result.add(tempCliente);
                }
                st.close();
                rs.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public ArrayList<Cliente> getAllClientiBySala(int id) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", " +
                        "\"Cliente\".\"Numero_ID_Card\", \"Cliente\".\"Numero_Tel\" FROM  \"Sala\" INNER JOIN \"Tavolo\" ON (\"Sala\".\"ID_Sala\" " +
                        "= \"Tavolo\".\"ID_Sala\") INNER JOIN \"Tavolata\" ON (\"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\") " +
                        "INNER JOIN \"Prenotazione\" ON (\"Tavolata\".\"Codice_Prenotazione\" = \"Prenotazione\".\"Codice_Prenotazione\") " +
                        "INNER JOIN \"Cliente\" ON (\"Prenotazione\".\"Numero_ID\" = \"Cliente\".\"Numero_ID_Card\") WHERE \"Sala\".\"ID_Sala\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    String nome = rs.getString(1);
                    String cognome = rs.getString(2);
                    String numeroIdCard = rs.getString(3);
                    String numeroTelefono = rs.getString(4);

                    Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTelefono);

                    result.add(tempCliente);
                }
                st.close();
                rs.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public ArrayList<Cliente> getAllClientiByTavolo(int id) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", " +
                        "\"Cliente\".\"Numero_ID_Card\", \"Cliente\".\"Numero_Tel\" FROM \"Tavolo\" INNER JOIN \"Tavolata\" ON " +
                        "(\"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\") INNER JOIN \"Prenotazione\" ON " +
                        "(\"Tavolata\".\"Codice_Prenotazione\" = \"Prenotazione\".\"Codice_Prenotazione\") INNER JOIN \"Cliente\" " +
                        "ON (\"Prenotazione\".\"Numero_ID\" = \"Cliente\".\"Numero_ID_Card\") WHERE \"Tavolo\".\"Codice_Tavolo\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    String nome = rs.getString(1);
                    String cognome = rs.getString(2);
                    String numeroIdCard = rs.getString(3);
                    String numeroTelefono = rs.getString(4);

                    Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTelefono);

                    result.add(tempCliente);
                }
                st.close();
                rs.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<Cliente> getAllClientiByTavolata(int id) {
        ArrayList<Cliente> result = new ArrayList<Cliente>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cliente\".\"Nome\", \"Cliente\".\"Cognome\", \"Cliente\".\"Numero_ID_Card\"," +
                        " \"Cliente\".\"Numero_Tel\" FROM \"Tavolata\" INNER JOIN \"Prenotazione\" ON (\"Tavolata\".\"Codice_Prenotazione\" = \"Prenotazione\".\"Codice_Prenotazione\") " +
                        "INNER JOIN \"Cliente\" ON (\"Prenotazione\".\"Numero_ID\" = \"Cliente\".\"Numero_ID_Card\") WHERE \"Tavolata\".\"Codice_Prenotazione\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    String nome = rs.getString(1);
                    String cognome = rs.getString(2);
                    String numeroIdCard = rs.getString(3);
                    String numeroTelefono = rs.getString(4);

                    Cliente tempCliente = new Cliente(nome, cognome, numeroIdCard, numeroTelefono);

                    result.add(tempCliente);
                }
                st.close();
                rs.close();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public void createCliente(Cliente cliente) {
            try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Cliente\" (\"Nome\", \"Cognome\", \"Numero_ID_Card\", \"Numero_Tel\") VALUES (?, ?, ?, ?)");
                st.setString(1, cliente.getNome());
                st.setString(2, cliente.getCognome());
                st.setString(3, cliente.getNumeroIdCard());
                st.setString(4, cliente.getNumeroTelefono());
                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateCliente(Cliente cliente) {
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Nome\" = ?, \"Cognome\" = ?, \"Numero_Tel\" = ? WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, cliente.getNome());
                st.setString(2, cliente.getCognome());
                st.setString(3, cliente.getNumeroTelefono());
                st.setString(4, cliente.getNumeroIdCard());
                st.executeUpdate();

                TavolataImpl tavolataImpl = new TavolataImpl(connection);
                ArrayList<Tavolata> tavolateCliente = tavolataImpl.getAllTavolateByCliente(cliente.getNumeroIdCard());

                if(!tavolateCliente.isEmpty()) {
                    st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Numero_ID\" = ?");
                    st.setString(1, cliente.getNumeroIdCard());
                    st.executeUpdate();
                    for (Tavolata t : tavolateCliente) {
                        st = connection.prepareStatement("INSERT INTO \"Prenotazione\" (\"Numero_ID\", \"Codice_Prenotazione\") VALUES (?, ?)");
                        st.setString(1, cliente.getNumeroIdCard());
                        st.setInt(2, t.getCodicePrenotazione());
                        st.executeUpdate();
                    }
                }

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateCliente(Cliente cliente, String oldIdCard) {
            try {
                TavolataImpl tavolataImpl = new TavolataImpl(connection);
                ArrayList<Tavolata> tavolateCliente = tavolataImpl.getAllTavolateByCliente(oldIdCard);

                if(!tavolateCliente.isEmpty()) {
                    PreparedStatement st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Numero_ID\" = ?");
                    st.setString(1, oldIdCard);
                    st.executeUpdate();
                }

                PreparedStatement st = connection.prepareStatement("UPDATE \"Cliente\" SET \"Nome\" = ?, \"Cognome\" = ?, \"Numero_ID_Card\" = ?, \"Numero_Tel\" = ? WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, cliente.getNome());
                st.setString(2, cliente.getCognome());
                st.setString(3, cliente.getNumeroIdCard());
                st.setString(4, cliente.getNumeroTelefono());
                st.setString(5, oldIdCard);
                st.executeUpdate();

                if(!tavolateCliente.isEmpty()){
                    for (Tavolata t : tavolateCliente) {
                        st = connection.prepareStatement("INSERT INTO \"Prenotazione\" (\"Numero_ID\", \"Codice_Prenotazione\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                        st.setString(1, cliente.getNumeroIdCard());
                        st.setInt(2, t.getCodicePrenotazione());
                        st.executeUpdate();
                    }
                }

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void deleteCliente(Cliente cliente) {
            try {

                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Numero_ID\" = ?");
                st.setString(1, cliente.getNumeroIdCard());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Cliente\" WHERE \"Numero_ID_Card\" = ?");
                st.setString(1, cliente.getNumeroIdCard());
                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }