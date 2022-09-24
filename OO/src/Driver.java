import Controller.Controller;
import GUI.mainFrame.mainFrame;
import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;

import java.sql.Connection;


public class Driver {
    public static void main(String[] args){

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
