package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.RistoranteDAO;
import Model.DTO.Ristorante;
import Model.DTO.Sala;
import java.sql.*;

import java.util.ArrayList;

public class RistoranteImpl implements RistoranteDAO {

    private Connection connection;

    public RistoranteImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Ristorante getRistoranteByNome(String nomeRistorante) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                rs.next();
                String nome = rs.getString(1);
                int numeroCamerieri = rs.getInt(3);
                int capienza = rs.getInt(4);
                SalaImpl tempSala = new SalaImpl(connection);
                ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                st.close();
                rs.close();
                return tempRistorante;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public Ristorante getRistoranteById(int id) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Ristorante\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                String nome = rs.getString(1);
                int numeroCamerieri = rs.getInt(3);
                int capienza = rs.getInt(4);
                SalaImpl tempSala = new SalaImpl(connection);
                ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                st.close();
                rs.close();
                return tempRistorante;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Ristorante> getAllRistoranti() {
        ArrayList<Ristorante> result = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Ristorante\" ORDER BY \"ID_Ristorante\" ASC");
                while (rs.next()){

                    String nome = rs.getString(1);
                    int numeroCamerieri = rs.getInt(3);
                    int capienza = rs.getInt(4);
                    SalaImpl tempSala = new SalaImpl(connection);
                    ArrayList<Sala> tempSale = tempSala.getAllSaleByRistorante(nome);

                    Ristorante tempRistorante = new Ristorante(nome, numeroCamerieri, tempSale, capienza);

                    result.add(tempRistorante);
                }
                st.close();
                rs.close();
                return result;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public void createRistorante(Ristorante ristorante) {
            try{
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Ristorante\" (\"Nome_Ristorante\") VALUES (?)");
                st.setString(1, ristorante.getNome());
                st.executeUpdate();
                st.close();
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateRistorante(Ristorante ristorante, String oldName) {
            try{
                PreparedStatement st = connection.prepareStatement("UPDATE \"Ristorante\" SET \"Nome_Ristorante\" = ? WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, ristorante.getNome());
                st.setString(2, oldName);
                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void deleteRistorante(Ristorante ristorante) {
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM\"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, ristorante.getNome());
                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
