import Controller.Controller;
import GUI.mainFrame.mainFrame;
import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.ImplementationClass.PostgreSQL.RistoranteImpl;
import Model.DAO.Interfaces.RistoranteDAO;
import Model.DTO.Ristorante;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) throws SQLException {

        DatabasePostgresConnection databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "Restaurant", "postgres", "postgres");
        boolean connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded){
            Connection connection = databasePostgresConnection.getDatabaseConnection();
            mainFrame mainFrame = new mainFrame();
            Controller controller = new Controller(mainFrame, connection);
        }
        else {
            System.out.println("CONNECTION FAILED");
        }
    }
}
