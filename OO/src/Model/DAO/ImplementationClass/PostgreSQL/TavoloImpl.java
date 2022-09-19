package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.TavoloDAO;
import Model.DTO.Sala;
import Model.DTO.Tavolata;
import Model.DTO.Tavolo;

import java.sql.*;
import java.util.ArrayList;

public class TavoloImpl implements TavoloDAO {

    private Connection connection;

    public TavoloImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Tavolo getTavoloById(int id) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Tavolo\" WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                int maxAvventori = rs.getInt(2);
                SalaImpl tempSala = new SalaImpl(connection);
                Sala sala = tempSala.getSalaById(rs.getInt(3));
                int codiceTavolo = rs.getInt(1);

                TavolataImpl tempTavolate = new TavolataImpl(connection);
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
                return tempTavolo;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoli() {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Tavolo\"");

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl(connection);
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl(connection);
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
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoliByRistorante(String nomeRistorante) {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolo\".\"Codice_Tavolo\", \"Tavolo\".\"Max_Avventori\", \"Tavolo\".\"ID_Sala\" FROM \"Ristorante\" " +
                        "INNER JOIN \"Sala\" ON \"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" WHERE \"Nome_Ristorante\" = ?");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl(connection);
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl(connection);
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
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Tavolo> getAllTavoliBySala(int id) {
        ArrayList<Tavolo> tempTavoli = new ArrayList<>();
            try {
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolo\".\"Codice_Tavolo\", \"Tavolo\".\"Max_Avventori\", \"Tavolo\".\"ID_Sala\" FROM \"Sala\" \n" +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" WHERE \"Sala\".\"ID_Sala\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {

                    int maxAvventori = rs.getInt(2);
                    SalaImpl tempSala = new SalaImpl(connection);
                    Sala sala = tempSala.getSalaById(rs.getInt(3));
                    int codiceTavolo = rs.getInt(1);

                    TavolataImpl tempTavolate = new TavolataImpl(connection);
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
                return tempTavoli;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public void createTavolo(Tavolo tavolo) {
            try{
                PreparedStatement st2 = connection.prepareStatement("SELECT \"ID_Sala\" FROM \"Sala\" WHERE \"Nome_Sala\" = ?");
                st2.setString(1, tavolo.getSala().getNome());
                ResultSet rs2 = st2.executeQuery();
                int idSala = rs2.getInt(1);

                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Tavolo\" (\"Max_Avventori\", \"ID_Sala\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                st.setInt(1, tavolo.getMaxAvventori());
                st.setInt(2, idSala);

                st.executeUpdate();
                st2.close();
                rs2.close();
                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updateTavolo(Tavolo tavolo) {
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"ID_Sala\" FROM \"Sala\" WHERE \"Nome_Sala\" = ?");
                st.setString(1, tavolo.getSala().getNome());
                ResultSet rs = st.executeQuery();
                int idSala = rs.getInt(1);
                rs.close();

                st = connection.prepareStatement("UPDATE \"Tavolo\" SET \"Max_Avventori\" = ?, \"ID_Sala\" = ? WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, tavolo.getMaxAvventori());
                st.setInt(2, idSala);
                st.setInt(3, tavolo.getCodiceTavolo());
                st.executeUpdate();

                if(!tavolo.getTavoliAdiacenti().isEmpty()) {
                    st = connection.prepareStatement("DELETE FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ? OR \"ID_Tavolo_Adiacente\" = ? ");
                    st.setInt(1, tavolo.getCodiceTavolo());
                    st.setInt(2, tavolo.getCodiceTavolo());
                    st.executeUpdate();

                    for (Tavolo tavoloAdiacente : tavolo.getTavoliAdiacenti()) {
                        st = connection.prepareStatement("INSERT INTO \"TavoliAdiacenti\" (\"ID_Tavolo\", \"ID_Tavolo_Adiacente\") VALUES (?, ?)");
                        st.setInt(1, tavolo.getCodiceTavolo());
                        st.setInt(2, tavoloAdiacente.getCodiceTavolo());
                        st.executeUpdate();
                    }
                }


                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void deleteTavolo(Tavolo tavolo) {
            try{
                PreparedStatement st = connection.prepareStatement("DELETE FROM \"Tavolo\" WHERE \"Codice_Tavolo\" = ?");
                st.setInt(1, tavolo.getCodiceTavolo());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"TavoliAdiacenti\" WHERE \"ID_Tavolo\" = ? OR \"ID_Tavolo_Adiacente\" = ?");
                st.setInt(1, tavolo.getCodiceTavolo());
                st.setInt(2, tavolo.getCodiceTavolo());
                st.executeUpdate();

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
