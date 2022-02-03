package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connessione {

    public Connessione() {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            String url = "jdbc:postgresql://localhost:5432/OOBD";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url, "postgres", "cavallo");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Model.DAO.Connessione OK \n");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Model.DAO.Connessione Fallita \n");
            System.out.println(e);
        }
    }
}