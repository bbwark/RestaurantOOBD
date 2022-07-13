package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.TavolataDAO;
import Model.DTO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class TavolataImpl implements TavolataDAO {

    private DatabasePostgresConnection databasePostgresConnection;
    private Connection connection;
    private boolean connectionSucceeded;

    @Override
    public Tavolata getTavolataById(int id) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Tavolata\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                Date dataArrivo = rs.getDate(1);
                int codicePrenotazione = rs.getInt(2);
                CameriereImpl tempCameriere = new CameriereImpl();
                ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                ClienteImpl tempCliente = new ClienteImpl();
                ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                TavoloImpl tempTavolo = new TavoloImpl();
                Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempTavolata;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolate() {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Tavolata\" ORDER BY \"Codice_Prenotazione\" ASC");
                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolateByRistorante(String nomeRistorante) {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Ristorante\" " +
                        "INNER JOIN \"Sala\" ON \"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Ristorante\".\"Nome_Ristorante\" = '?' ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolateBySala(String nomeSala) {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Sala\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Sala\".\"Nome_Sala\" = '?' ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setString(1, nomeSala);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolateByTavolo(int id) {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Tavolo\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Tavolo\".\"Codice_Tavolo\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolateByCameriere(int id) {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Cameriere\" " +
                        "INNER JOIN \"Servizio\" ON \"Cameriere\".\"ID_Cameriere\" = \"Servizio\".\"ID_Cameriere\" " +
                        "INNER JOIN \"Tavolata\" ON \"Servizio\".\"Codice_Prenotazione\" = \"Tavolata\".\"Codice_Prenotazione\" " +
                        "WHERE \"Cameriere\".\"ID_Cameriere\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolateByCliente(String id) {
        ArrayList<Tavolata> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Cliente\" " +
                        "INNER JOIN \"Prenotazione\" ON \"Cliente\".\"Numero_ID_Card\" = \"Prenotazione\".\"Numero_ID\" " +
                        "INNER JOIN \"Tavolata\" ON \"Prenotazione\".\"Codice_Prenotazione\" = \"Tavolata\".\"Codice_Prenotazione\" " +
                        "WHERE \"Cliente\".\"Numero_ID_Card\" = '?' ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    Date dataArrivo = rs.getDate(1);
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl();
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl();
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);
                    TavoloImpl tempTavolo = new TavoloImpl();
                    Tavolo tavolo = tempTavolo.getTavoloById(rs.getInt(3));

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tavolo, tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void createPrenotazione(Tavolata tavolata) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Tavolata\" (\"Data_Arrivo\", \"Codice_Tavolo\") VALUES ('?', ?)");
                st.setDate(1, (java.sql.Date)tavolata.getDataArrivo());
                st.setInt(2, tavolata.getTavolo().getCodiceTavolo());
                st.executeUpdate();

                for (Cameriere cameriere : tavolata.getCamerieri()) {
                    st = connection.prepareStatement("INSERT INTO \"Servizio\" (\"ID_Cameriere\", \"Codice_Prenotazione\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                    st.setInt(1, cameriere.getCodiceCameriere());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }

                for (Cliente cliente : tavolata.getClienti()){
                    st = connection.prepareStatement("INSERT INTO \"Prenotazione\" (\"Numero_ID\", \"Codice_Prenotazione\") VALUES ('?', ?) ON CONFLICT DO NOTHING");
                    st.setString(1, cliente.getNumeroIdCard());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }
                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }

    @Override
    public void updatePrenotazione(Tavolata tavolata) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("UPDATE \"Tavolata\" SET \"Data_Arrivo\" = '?', \"Codice_Tavolo\" = ? WHERE \"Codice_Prenotazione\" = ?");
                st.setDate(1, (java.sql.Date)tavolata.getDataArrivo());
                st.setInt(2, tavolata.getTavolo().getCodiceTavolo());
                st.setInt(3, tavolata.getCodicePrenotazione());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();
                for (Cameriere cameriere : tavolata.getCamerieri()) {
                    st = connection.prepareStatement("INSERT INTO \"Servizio\" (\"ID_Cameriere\", \"Codice_Prenotazione\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                    st.setInt(1, cameriere.getCodiceCameriere());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }

                st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();
                for (Cliente cliente : tavolata.getClienti()) {
                    st = connection.prepareStatement("INSERT INTO \"Prenotazione\" (\"Numero_ID\", \"Codice_Prenotazione\") VALUES ('?', ?) ON CONFLICT DO NOTHING");
                    st.setString(1, cliente.getNumeroIdCard());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }

                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }

    @Override
    public void deletePrenotazione(Tavolata tavolata) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{

                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Tavolata\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();

                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }
}
