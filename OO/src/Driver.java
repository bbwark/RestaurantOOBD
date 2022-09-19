import Controller.Controller;
import GUI.mainFrame.mainFrame;
import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) throws SQLException {

        DatabasePostgresConnection databasePostgresConnection = new DatabasePostgresConnection("localhost", "5432", "RestaurantOO", "postgres", "admin");
        boolean connectionSucceeded = databasePostgresConnection.openConnection();
        if (connectionSucceeded){
            Connection connection = databasePostgresConnection.getDatabaseConnection();
            mainFrame mainFrame = new mainFrame();
            Controller controller = new Controller(mainFrame, connection);
            connection.close();
        }
        else {
            System.out.println("CONNECTION FAILED");
        }
        databasePostgresConnection.closeConnection();

    }
}
