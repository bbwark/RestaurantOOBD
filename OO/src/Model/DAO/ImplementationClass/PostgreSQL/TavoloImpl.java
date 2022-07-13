package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.TavoloDAO;
import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.sql.*;
import java.util.ArrayList;

public class TavoloImpl implements TavoloDAO {

    private DatabasePostgresConnection databasePostgresConnection;
    private Connection connection;
    private boolean connectionSucceeded;

    @Override
    public Tavolo getTavoloById(int id) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Tavolo\" WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                int maxAvventori = rs.getInt(2);
                SalaImpl tempSala = new SalaImpl();
                Sala sala = tempSala.getSalaById(rs.getInt(3));
                int codiceTavolo = rs.getInt(1);

                TavolataImpl tempTavolate = new TavolataImpl();
                ArrayList<Tavolata> tavolate = tempTavolate.getAllTavolateByTavolo(codiceTavolo);

                st = connection.prepareStatement("SELECT \"ID_Tavolo_Adiacente\" FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ?");
                st.setInt(1, codiceTavolo);
                rs = st.executeQuery();
                ArrayList<Tavolo> tavoliAdiacenti = new ArrayList<>();

                while (rs.next()){
                    int codiceTavoloAdiacente = rs.getInt(1);
                    tavoliAdiacenti.add(getTavoloById(codiceTavoloAdiacente));
                }

                Tavolo tempTavolo = new Tavolo(maxAvventori, tavoliAdiacenti, sala, tavolate, codiceTavolo);

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempTavolo;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoli() {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Tavolo\"");

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl();
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl();
                    ArrayList<Tavolata> tavolate = tempTavolate.getAllTavolateByTavolo(codiceTavolo);

                    PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Tavolo_Adiacente\" FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ?");
                    st2.setInt(1, codiceTavolo);
                    ResultSet rs2 = st2.executeQuery();
                    ArrayList<Tavolo> tavoliAdiacenti = new ArrayList<>();

                    while (rs.next()) {
                        int codiceTavoloAdiacente = rs.getInt(1);
                        tavoliAdiacenti.add(getTavoloById(codiceTavoloAdiacente));
                    }

                    Tavolo tempTavolo = new Tavolo(maxAvventori, tavoliAdiacenti, sala, tavolate, codiceTavolo);

                    tempTavoli.add(tempTavolo);
                    st2.close();
                    rs2.close();
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoliByRistorante(String nomeRistorante) {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolo\".\"Codice_Tavolo\", \"Tavolo\".\"Max_Avventori\", \"Tavolo\".\"ID_Sala\" FROM \"Ristorante\" " +
                        "INNER JOIN \"Sala\" ON \"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl();
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl();
                    ArrayList<Tavolata> tavolate = tempTavolate.getAllTavolateByTavolo(codiceTavolo);

                    PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Tavolo_Adiacente\" FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ?");
                    st2.setInt(1, codiceTavolo);
                    ResultSet rs2 = st2.executeQuery();
                    ArrayList<Tavolo> tavoliAdiacenti = new ArrayList<>();

                    while (rs.next()) {
                        int codiceTavoloAdiacente = rs.getInt(1);
                        tavoliAdiacenti.add(getTavoloById(codiceTavoloAdiacente));
                    }

                    Tavolo tempTavolo = new Tavolo(maxAvventori, tavoliAdiacenti, sala, tavolate, codiceTavolo);

                    tempTavoli.add(tempTavolo);
                    st2.close();
                    rs2.close();
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoliBySala(String nomeSala) {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolo\".\"Codice_Tavolo\", \"Tavolo\".\"Max_Avventori\", \"Tavolo\".\"ID_Sala\" FROM \"Sala\" \n" +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" WHERE \"Nome_Sala\" = '?'");
                st.setString(1, nomeSala);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl();
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl();
                    ArrayList<Tavolata> tavolate = tempTavolate.getAllTavolateByTavolo(codiceTavolo);

                    PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Tavolo_Adiacente\" FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ?");
                    st2.setInt(1, codiceTavolo);
                    ResultSet rs2 = st2.executeQuery();
                    ArrayList<Tavolo> tavoliAdiacenti = new ArrayList<>();

                    while (rs.next()) {
                        int codiceTavoloAdiacente = rs.getInt(1);
                        tavoliAdiacenti.add(getTavoloById(codiceTavoloAdiacente));
                    }

                    Tavolo tempTavolo = new Tavolo(maxAvventori, tavoliAdiacenti, sala, tavolate, codiceTavolo);

                    tempTavoli.add(tempTavolo);
                    st2.close();
                    rs2.close();
                }

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void createTavolo(Tavolo tavolo) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Sala\" FROM \"Sala\" WHERE \"Nome_Sala\" = '?'");
                st2.setString(1, tavolo.getSala().getNome());
                ResultSet rs2 = st2.executeQuery();
                int idSala = rs2.getInt(1);

                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Tavolo\" (\"Max_Avventori\", \"ID_Sala\") VALUES (?, ?)");
                st.setInt(1, tavolo.getMaxAvventori());
                st.setInt(2, idSala);

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
    public void updateTavolo(Tavolo tavolo) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Sala\" FROM \"Sala\" WHERE \"Nome_Sala\" = '?'");
                st2.setString(1, tavolo.getSala().getNome());
                ResultSet rs2 = st2.executeQuery();
                int idSala = rs2.getInt(1);

                PreparedStatement st = connection.prepareStatement("UPDATE \"Tavolo\" SET \"Max_Avventori\" = ?, \"ID_Sala\" = ? WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, tavolo.getMaxAvventori());
                st.setInt(2, idSala);
                st.setInt(3, tavolo.getCodiceTavolo());

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
    public void deleteTavolo(Tavolo tavolo) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Tavolo\" WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, tavolo.getCodiceTavolo());

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
