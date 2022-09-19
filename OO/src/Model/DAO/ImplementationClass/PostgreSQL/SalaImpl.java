package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.SalaDAO;
import Model.DTO.*;

import java.sql.*;
import java.util.ArrayList;

public class SalaImpl implements SalaDAO {

    private Connection connection;

    public SalaImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Sala getSalaByNome(String nomeSala) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Sala\" WHERE \"Nome_Sala\" = ?");
                st.setString(1, nomeSala);
                ResultSet rs = st.executeQuery();

                String nome = rs.getString(4);
                int id = rs.getInt(5);
                int numeroTavoli = rs.getInt(2);
                int capienza = rs.getInt(3);
                TavoloImpl tempTavolo = new TavoloImpl(connection);
                ArrayList<Tavolo> tempTavoli = tempTavolo.getAllTavoliBySala(id);
                RistoranteImpl tempRistorante = new RistoranteImpl(connection);
                Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(1));

                Sala tempSala = new Sala(id, nome, ristorante, numeroTavoli, tempTavoli, capienza);

                st.close();
                rs.close();
                return tempSala;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public Sala getSalaById(int id) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Sala\" WHERE \"ID_Sala\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                String nome = rs.getString(4);
                int numeroTavoli = rs.getInt(2);
                int capienza = rs.getInt(3);
                TavoloImpl tempTavolo = new TavoloImpl(connection);
                ArrayList<Tavolo> tempTavoli = tempTavolo.getAllTavoliBySala(id);
                RistoranteImpl tempRistorante = new RistoranteImpl(connection);
                Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(1));

                Sala tempSala = new Sala(id, nome, ristorante, numeroTavoli, tempTavoli, capienza);

                st.close();
                rs.close();
                return tempSala;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Sala> getAllSale() {
        ArrayList<Sala> result = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Sala\" ORDER BY \"ID_Sala\" ASC");
                while (rs.next()){

                    String nome = rs.getString(4);
                    int id = rs.getInt(5);
                    int numeroTavoli = rs.getInt(2);
                    int capienza = rs.getInt(3);
                    TavoloImpl tempTavolo = new TavoloImpl(connection);
                    ArrayList<Tavolo> tempTavoli = tempTavolo.getAllTavoliBySala(id);
                    RistoranteImpl tempRistorante = new RistoranteImpl(connection);
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(1));

                    Sala tempSala = new Sala(id, nome, ristorante, numeroTavoli, tempTavoli, capienza);

                    result.add(tempSala);
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
    public ArrayList<Sala> getAllSaleByRistorante(String nomeRistorante) {
        ArrayList<Sala> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();
                rs.next();
                int idRistorante = rs.getInt(1);

                st = connection.prepareStatement("SELECT * FROM \"Sala\" WHERE \"ID_Ristorante\" = ?");
                st.setInt(1, idRistorante);
                rs = st.executeQuery();

                while (rs.next()) {

                    String nome = rs.getString(4);
                    int id = rs.getInt(5);
                    int numeroTavoli = rs.getInt(2);
                    int capienza = rs.getInt(3);
                    TavoloImpl tempTavolo = new TavoloImpl(connection);
                    ArrayList<Tavolo> tempTavoli = tempTavolo.getAllTavoliBySala(id);
                    RistoranteImpl tempRistorante = new RistoranteImpl(connection);
                    Ristorante ristorante = tempRistorante.getRistoranteById(rs.getInt(1));

                    Sala tempSala = new Sala(id, nome, ristorante, numeroTavoli, tempTavoli, capienza);

                    result.add(tempSala);
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
    public void createSala(Sala sala) {
            try{
                PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st2.setString(1, sala.getRistorante().getNome());
                ResultSet rs2 = st2.executeQuery();
                int idRistorante = rs2.getInt(1);

                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Sala\" (\"Nome_Sala\", \"ID_Ristorante\") VALUES (?, ?)");
                st.setString(1, sala.getNome());
                st.setInt(2, idRistorante);

                st.executeUpdate();
                st2.close();
                rs2.close();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateSala(Sala sala) {
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Ristorante\" FROM \"Ristorante\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, sala.getRistorante().getNome());
                ResultSet rs = st.executeQuery();
                int idRistorante = rs.getInt(1);
                rs.close();

                st = connection.prepareStatement("UPDATE \"Sala\" SET \"Nome_Sala\" = ?, \"ID_Ristorante\" = ? WHERE \"ID_Sala\" = ?");
                st.setString(1, sala.getNome());
                st.setInt(2, idRistorante);
                st.setInt(3, sala.getIdSala());

                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void deleteSala(Sala sala) {
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Sala\" WHERE \"Nome_Sala\" = ?");
                st.setString(1, sala.getNome());
                st.executeUpdate();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
