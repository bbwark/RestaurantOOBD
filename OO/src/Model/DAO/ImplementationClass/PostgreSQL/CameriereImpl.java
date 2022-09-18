package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.CameriereDAO;
import Model.DTO.Cameriere;
import Model.DTO.Ristorante;
import Model.DTO.Tavolata;

import java.sql.*;
import java.util.ArrayList;

public class CameriereImpl implements CameriereDAO {

    private DatabasePostgresConnection databasePostgresConnection;
    private Connection connection;
    private boolean connectionSucceeded;

    @Override
    public Cameriere getCameriereById(int id) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Cameriere\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                int codiceCameriere = rs.getInt(1);
                String nome = rs.getString(2);
                String cognome = rs.getString(3);
                RistoranteImpl tempRistorante = new RistoranteImpl();
                Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));

                TavolataImpl tempTavolata = new TavolataImpl();
                ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);


                Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCameriere;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cameriere> getAllCamerieri() {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Cameriere\" ORDER BY \"ID_Cameriere\" ASC");
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);
                    RistoranteImpl tempRistorante = new RistoranteImpl();
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));
                    TavolataImpl tempTavolata = new TavolataImpl();
                    ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);


                    Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByRistorante(String nomeRistorante) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();
                int idRistorante = rs.getInt(1);

                st = connection.prepareStatement("SELECT * FROM \"Ristorante\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, idRistorante);
                rs = st.executeQuery();

                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);
                    RistoranteImpl tempRistorante = new RistoranteImpl();
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));
                    TavolataImpl tempTavolata = new TavolataImpl();
                    ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cameriere> getAllCamerieriBySala(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cameriere\".\"ID_Cameriere\", \"Cameriere\".\"Nome\", \"Cameriere\".\"Cognome\" FROM \"Sala\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "INNER JOIN \"Servizio\" ON \"Tavolata\".\"Codice_Prenotazione\" = \"Servizio\".\"Codice_Prenotazione\" " +
                        "INNER JOIN \"Cameriere\" ON \"Servizio\".\"ID_Cameriere\" = \"Cameriere\".\"ID_Cameriere\" WHERE \"Sala\".\"ID_Sala\" = ? ORDER BY \"ID_Cameriere\"");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);
                    RistoranteImpl tempRistorante = new RistoranteImpl();
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));
                    TavolataImpl tempTavolata = new TavolataImpl();
                    ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByTavolo(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cameriere\".\"ID_Cameriere\", \"Cameriere\".\"Nome\", \"Cameriere\".\"Cognome\" FROM \"Tavolo\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "INNER JOIN \"Servizio\" ON \"Tavolata\".\"Codice_Prenotazione\" = \"Servizio\".\"Codice_Prenotazione\" " +
                        "INNER JOIN \"Cameriere\" ON \"Servizio\".\"ID_Cameriere\" = \"Cameriere\".\"ID_Cameriere\" WHERE \"Tavolo\".\"Codice_Tavolo\" = ? ORDER BY \"ID_Cameriere\"");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);
                    RistoranteImpl tempRistorante = new RistoranteImpl();
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));
                    TavolataImpl tempTavolata = new TavolataImpl();
                    ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByTavolata(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cameriere\".\"ID_Cameriere\", \"Cameriere\".\"Nome\", \"Cameriere\".\"Cognome\" FROM \"Tavolata\" " +
                        "INNER JOIN \"Servizio\" ON \"Tavolata\".\"Codice_Prenotazione\" = \"Servizio\".\"Codice_Prenotazione\" " +
                        "INNER JOIN \"Cameriere\" ON \"Servizio\".\"ID_Cameriere\" = \"Cameriere\".\"ID_Cameriere\" WHERE \"Tavolata\".\"Codice_Prenotazione\" = ? ORDER BY \"ID_Cameriere\"");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);
                    RistoranteImpl tempRistorante = new RistoranteImpl();
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(4));
                    TavolataImpl tempTavolata = new TavolataImpl();
                    ArrayList<Tavolata> servizio = tempTavolata.getAllTavolateByCameriere(codiceCameriere);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, ristorante, servizio, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void createCameriere(Cameriere cameriere) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Cameriere\" (\"Nome\", \"Cognome\", \"ID_Ristorante\",) VALUES ('?', '?', ?)");
                st.setString(1, cameriere.getNome());
                st.setString(2, cameriere.getCognome());

                    PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = '?'");
                    st2.setString(1, cameriere.getRistorante().getNome());
                    ResultSet rs2 = st2.executeQuery();
                    int idRistorante = rs2.getInt(1);

                st.setInt(3, idRistorante);

                st.executeUpdate();
                st2.close();
                rs2.close();
                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }

    @Override
    public void updateCameriere(Cameriere cameriere) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {

                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, cameriere.getRistorante().getNome());
                ResultSet rs = st.executeQuery();
                int idRistorante = rs.getInt(1);
                rs.close();

                st = connection.prepareStatement("UPDATE \"Cameriere\" \"Nome\" = '?', \"Cognome\" = '?', \"ID_Ristorante\" = ? WHERE \"ID_Cameriere = ?\"");
                st.setString(1, cameriere.getNome());
                st.setString(2, cameriere.getCognome());
                st.setInt(3, idRistorante);
                st.setInt(4, cameriere.getCodiceCameriere());
                st.executeUpdate();

                if (!cameriere.getServizio().isEmpty()) {
                    st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"ID_Cameriere\" = ?");
                    st.setInt(1, cameriere.getCodiceCameriere());
                    st.executeUpdate();
                    for (Tavolata tavolata : cameriere.getServizio()) {
                        st = connection.prepareStatement("INSERT INTO \"Servizio\" (\"ID_Cameriere\", \"Codice_Prenotazione\") VALUES (?, ?)");
                        st.setInt(1, cameriere.getCodiceCameriere());
                        st.setInt(2, tavolata.getCodicePrenotazione());
                        st.executeUpdate();
                    }
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
    public void deleteCameriere(Cameriere cameriere) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try {

                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, cameriere.getCodiceCameriere());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Cameriere\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, cameriere.getCodiceCameriere());
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
