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
    public String getNomeByID(int id) {
        String result = null;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Nome_Ristorante\" FROM \"Ristorante\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                result = rs.getString(1);
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
    public ArrayList<Sala> getSaleRistoranteById(int id) {
        ArrayList<Sala> salaResult = null;
        databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        connectionSucceeded = databasePostgresConnection.openConnection();
        if(connectionSucceeded){
            connection = databasePostgresConnection.getDatabaseConnection();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Sala\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();






            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public boolean setNomeRistoranteById() {
        return false;
    }

    @Override
    public boolean setSaleRistoranteById() {
        return false;
    }

    @Override
    public ArrayList<Ristorante> getAllRistoranti() {
        return null;
    }

    @Override
    public boolean createRistorante() {
        return false;
    }

    @Override
    public boolean updateRistorante() {
        return false;
    }

    @Override
    public boolean deleteRistorante() {
        return false;
    }
}
