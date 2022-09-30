package Model.DAO.ImplementationClass.PostgreSQL;

import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.TavolataDAO;
import Model.DAO.Interfaces.TavoloDAO;
import Model.DTO.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class TavolataImpl implements TavolataDAO {

    private Connection connection;

    public TavolataImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Tavolata getTavolataById(int id) {
            try {
                PreparedStatement st = connection.prepareStatement("SELECT * FROM \"Tavolata\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                rs.next();
                LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                int codicePrenotazione = rs.getInt(2);
                CameriereImpl tempCameriere = new CameriereImpl(connection);
                ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                ClienteImpl tempCliente = new ClienteImpl(connection);
                ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri, tempClienti, codicePrenotazione);

                st.close();
                rs.close();
                return tempTavolata;
            }catch (SQLException e){
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public ArrayList<Tavolata> getAllTavolate() {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM \"Tavolata\" ORDER BY \"Codice_Prenotazione\" ASC");
                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public ArrayList<Tavolata> getAllTavolateByRistorante(String nomeRistorante) {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Ristorante\" " +
                        "INNER JOIN \"Sala\" ON \"Ristorante\".\"ID_Ristorante\" = \"Sala\".\"ID_Ristorante\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Ristorante\".\"Nome_Ristorante\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setString(1, nomeRistorante);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public ArrayList<Tavolata> getAllTavolateBySala(int id) {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Sala\" " +
                        "INNER JOIN \"Tavolo\" ON \"Sala\".\"ID_Sala\" = \"Tavolo\".\"ID_Sala\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Sala\".\"ID_Sala\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public ArrayList<Tavolata> getAllTavolateByTavolo(int id) {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Tavolo\" " +
                        "INNER JOIN \"Tavolata\" ON \"Tavolo\".\"Codice_Tavolo\" = \"Tavolata\".\"Codice_Tavolo\" " +
                        "WHERE \"Tavolo\".\"Codice_Tavolo\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public ArrayList<Tavolata> getAllTavolateByCameriere(int id) {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Cameriere\" " +
                        "INNER JOIN \"Servizio\" ON \"Cameriere\".\"ID_Cameriere\" = \"Servizio\".\"ID_Cameriere\" " +
                        "INNER JOIN \"Tavolata\" ON \"Servizio\".\"Codice_Prenotazione\" = \"Tavolata\".\"Codice_Prenotazione\" " +
                        "WHERE \"Cameriere\".\"ID_Cameriere\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public ArrayList<Tavolata> getAllTavolateByCliente(String id) {
        ArrayList<Tavolata> result = new ArrayList<>();
            try{
                PreparedStatement st = connection.prepareStatement("SELECT \"Tavolata\".\"Data_Arrivo\", \"Tavolata\".\"Codice_Prenotazione\", \"Tavolata\".\"Codice_Tavolo\" FROM \"Cliente\" " +
                        "INNER JOIN \"Prenotazione\" ON \"Cliente\".\"Numero_ID_Card\" = \"Prenotazione\".\"Numero_ID\" " +
                        "INNER JOIN \"Tavolata\" ON \"Prenotazione\".\"Codice_Prenotazione\" = \"Tavolata\".\"Codice_Prenotazione\" " +
                        "WHERE \"Cliente\".\"Numero_ID_Card\" = ? ORDER BY \"Tavolata\".\"Codice_Prenotazione\" ASC");
                st.setString(1, id);
                ResultSet rs = st.executeQuery();

                while (rs.next()){

                    LocalDate dataArrivo = rs.getDate(1).toLocalDate();
                    int codicePrenotazione = rs.getInt(2);
                    CameriereImpl tempCameriere = new CameriereImpl(connection);
                    ArrayList<Cameriere> tempCamerieri = tempCameriere.getAllCamerieriByTavolata(codicePrenotazione);
                    ClienteImpl tempCliente = new ClienteImpl(connection);
                    ArrayList<Cliente> tempClienti = tempCliente.getAllClientiByTavolata(codicePrenotazione);

                    Tavolata tempTavolata = new Tavolata(dataArrivo, tempCamerieri,tempClienti, codicePrenotazione);

                    result.add(tempTavolata);
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
    public void createPrenotazione(Tavolata tavolata, Tavolo tavolo) {
            try{
                PreparedStatement st = connection.prepareStatement("INSERT INTO \"Tavolata\" (\"Data_Arrivo\", \"Codice_Tavolo\") VALUES (?, ?)");
                st.setDate(1, java.sql.Date.valueOf(tavolata.getDataArrivo()));
                st.setInt(2, tavolo.getCodiceTavolo());
                st.executeUpdate();

                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT MAX(\"Codice_Prenotazione\") FROM \"Tavolata\"");
                rs.next();
                Tavolata tempTavolata = getTavolataById(rs.getInt(1));
                tavolo.getTavolate().add(tempTavolata);

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void updatePrenotazione(Tavolata tavolata) {
            try {
                PreparedStatement st = connection.prepareStatement("UPDATE \"Tavolata\" SET \"Data_Arrivo\" = ? WHERE \"Codice_Prenotazione\" = ?");
                st.setDate(1, java.sql.Date.valueOf(tavolata.getDataArrivo()));
                st.setInt(2, tavolata.getCodicePrenotazione());
                st.executeUpdate();

                st = connection.prepareStatement("DELETE FROM \"Servizio\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();
                st = connection.prepareStatement("INSERT INTO \"Servizio\" (\"ID_Cameriere\", \"Codice_Prenotazione\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                for (Cameriere cameriere : tavolata.getCamerieri()) {
                    st.setInt(1, cameriere.getCodiceCameriere());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }

                st = connection.prepareStatement("DELETE FROM \"Prenotazione\" WHERE \"Codice_Prenotazione\" = ?");
                st.setInt(1, tavolata.getCodicePrenotazione());
                st.executeUpdate();
                st = connection.prepareStatement("INSERT INTO \"Prenotazione\" (\"Numero_ID\", \"Codice_Prenotazione\") VALUES (?, ?) ON CONFLICT DO NOTHING");
                for (Cliente cliente : tavolata.getClienti()) {
                    st.setString(1, cliente.getNumeroIdCard());
                    st.setInt(2, tavolata.getCodicePrenotazione());
                    st.executeUpdate();
                }

                st.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void deletePrenotazione(Tavolata tavolata) {
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
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
