package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.RistoranteDAO;
import Model.DTO.Ristorante;
import Model.DTO.Sala;
import java.sql.*;

import java.util.ArrayList;

public class RistoranteImpl implements RistoranteDAO {

    private DatabasePostgresConnection databasePostgresConnection;
    private Connection connection;
    private boolean connectionSucceeded;

    @Override
    public Ristorante getRistoranteByNome(String nomeRistorante) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                String nome = rs.getString(1);
                int numeroCamerieri = rs.getInt(3);
                int capienza = rs.getInt(4);
                SalaImpl tempSala = new SalaImpl();
                ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempRistorante;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Ristorante getRistoranteById(int id) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Ristorante\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                String nome = rs.getString(1);
                int numeroCamerieri = rs.getInt(3);
                int capienza = rs.getInt(4);
                SalaImpl tempSala = new SalaImpl();
                ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                st.close();
                rs.close();
                connection.close();
                databasePostgresConnection.closeConnection();
                return tempRistorante;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ArrayList<Ristorante> getAllRistoranti() {
        ArrayList<Ristorante> result = new ArrayList<>();
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Ristorante\" ORDER BY \"ID_Ristorante\" ASC");
                while (rs.next()){

                    String nome = rs.getString(1);
                    int numeroCamerieri = rs.getInt(3);
                    int capienza = rs.getInt(4);
                    SalaImpl tempSala = new SalaImpl();
                    ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                    Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                    result.add(tempRistorante);
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
    public void createRistorante(Ristorante ristorante) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Ristorante\" (\"Nome_Ristorante\") VALUES ('?')");
                st.setString(1, ristorante.getNome());
                st.executeUpdate();
                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }

    @Override
    public void updateRistorante(Ristorante ristorante, String oldName) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("UPDATE \"Ristorante\" SET \"Nome_Ristorante\" = '?' WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, ristorante.getNome());
                st.setString(2, oldName);
                st.executeUpdate();
                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        databasePostgresConnection.closeConnection();
    }

    @Override
    public void deleteRistorante(Ristorante ristorante) {
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded) {
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM\"Ristorante\" WHERE \"Nome_Ristorante\" = '?'");
                st.setString(1, ristorante.getNome());
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
