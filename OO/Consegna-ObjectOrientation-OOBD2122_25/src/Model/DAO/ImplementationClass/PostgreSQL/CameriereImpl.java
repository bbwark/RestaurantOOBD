package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.CameriereDAO;
import Model.DTO.Cameriere;
import Model.DTO.Ristorante;
import Model.DTO.Tavolata;

import java.sql.*;
import java.util.ArrayList;

public class CameriereImpl implements CameriereDAO {

    private Connection connection;

    public CameriereImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Cameriere getCameriereById(int id) {
            try{
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Cameriere\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                int codiceCameriere = rs.getInt(1);
                String nome = rs.getString(2);
                String cognome = rs.getString(3);


                Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                st.close();
                rs.close();
                return tempCameriere;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<Cameriere> getAllCamerieri() {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Cameriere\" ORDER BY \"ID_Cameriere\" ASC");
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);


                    Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByRistorante(String nomeRistorante) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();
                rs.next();
                int idRistorante = rs.getInt(1);

                st = connection.prepareStatement("SELECT * FROM \"Cameriere\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, idRistorante);
                rs = st.executeQuery();

                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<Cameriere> getAllCamerieriBySala(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
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

                    Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByTavolo(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
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

                    Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public ArrayList<Cameriere> getAllCamerieriByTavolata(int id) {
        ArrayList<Cameriere> tempCamerieri = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT DISTINCT \"Cameriere\".\"ID_Cameriere\", \"Cameriere\".\"Nome\", \"Cameriere\".\"Cognome\", \"Cameriere\".\"ID_Ristorante\"" +
                        " FROM \"Tavolata\" INNER JOIN \"Servizio\" ON \"Tavolata\".\"Codice_Prenotazione\" = \"Servizio\".\"Codice_Prenotazione\" " +
                        "INNER JOIN \"Cameriere\" ON \"Servizio\".\"ID_Cameriere\" = \"Cameriere\".\"ID_Cameriere\" WHERE \"Tavolata\".\"Codice_Prenotazione\" = ? ORDER BY \"ID_Cameriere\"");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {

                    int codiceCameriere = rs.getInt(1);
                    String nome = rs.getString(2);
                    String cognome = rs.getString(3);

                    Cameriere tempCameriere = new Cameriere(nome, cognome, codiceCameriere);

                    tempCamerieri.add(tempCameriere);
                }
                st.close();
                rs.close();
                return tempCamerieri;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
        }

    @Override
    public void createCameriere(Cameriere cameriere, Tavolata tavolata) {
            try {
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Cameriere\" (\"Nome\", \"Cognome\", \"ID_Ristorante\") VALUES (?, ?, ?)");
                st.setString(1, cameriere.getNome());
                st.setString(2, cameriere.getCognome());

                    PreparedStatement st2 = connection.prepareStatement("SELECT \"Ristorante\".\"ID_Ristorante\" FROM \"Ristorante\" INNER JOIN \"Sala\" ON " +
                            "(\"Sala\".\"ID_Ristorante\" = \"Ristorante\".\"ID_Ristorante\") INNER JOIN \"Tavolo\" ON (\"Tavolo\".\"ID_Sala\" = \"Sala\".\"ID_Sala\") " +
                            "INNER JOIN \"Tavolata\" ON (\"Tavolata\".\"Codice_Tavolo\" = \"Tavolo\".\"Codice_Tavolo\") WHERE \"Tavolata\".\"Codice_Prenotazione\" = ?");
                    st2.setInt(1, tavolata.getCodicePrenotazione());
                    ResultSet rs2 = st2.executeQuery();
                    rs2.next();
                    int idRistorante = rs2.getInt(1);

                st.setInt(3, idRistorante);
                st.executeUpdate();

                Statement statement = connection.createStatement();
                rs2 = statement.executeQuery("SELECT MAX(\"ID_Cameriere\") FROM \"Cameriere\"");

                rs2.next();
                Cameriere tempCameriere = getCameriereById(rs2.getInt(1));
                tavolata.getCamerieri().add(tempCameriere);
                TavolataImpl tempTavolata = new TavolataImpl(connection);
                tempTavolata.updatePrenotazione(tavolata);

                statement.close();
                st2.close();
                rs2.close();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateCameriere(Cameriere cameriere) {
            try {

                PreparedStatement st = connection.prepareStatement("UPDATE \"Cameriere\" SET \"Nome\" = ?, \"Cognome\" = ? WHERE \"ID_Cameriere\" = ?");
                st.setString(1, cameriere.getNome());
                st.setString(2, cameriere.getCognome());
                st.setInt(3, cameriere.getCodiceCameriere());
                st.executeUpdate();

                TavolataImpl tavolataImpl = new TavolataImpl(connection);
                ArrayList<Tavolata> tavolateCameriere = tavolataImpl.getAllTavolateByCameriere(cameriere.getCodiceCameriere());

                if (!tavolateCameriere.isEmpty()) {
                    st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"ID_Cameriere\" = ?");
                    st.setInt(1, cameriere.getCodiceCameriere());
                    st.executeUpdate();
                    for (Tavolata t : tavolateCameriere) {
                        st = connection.prepareStatement("INSERT INTO \"Servizio\" (\"ID_Cameriere\", \"Codice_Prenotazione\") VALUES (?, ?)");
                        st.setInt(1, cameriere.getCodiceCameriere());
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
    public void deleteCameriere(Cameriere cameriere) {
            try {
                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, cameriere.getCodiceCameriere());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Cameriere\" WHERE \"ID_Cameriere\" = ?");
                st.setInt(1, cameriere.getCodiceCameriere());
                st.executeUpdate();

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
