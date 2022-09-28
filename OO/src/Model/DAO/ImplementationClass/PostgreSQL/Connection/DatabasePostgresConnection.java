package Model.DAO.ImplementationClass.PostgreSQL.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePostgresConnection {

    private Connection db;
    private String url;
    private String username;
    private String password;

    private static DatabasePostgresConnection istance = null;

    private DatabasePostgresConnection(String host, String port, String databaseName, String username, String password) {
        url = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
        this.username = username;
        this.password = password;
        db = null;
    }

    public static DatabasePostgresConnection getInstance(String host, String port, String databaseName, String username, String password){
        if (istance == null)
            istance = new DatabasePostgresConnection(host, port, databaseName, username, password);
        return istance;
    }

    public boolean open() {
        try {
            this.db = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\nConnection Failed");
            return false;
        }
        System.out.println("Connection Succeeded");
        System.out.println("Connected with " + url + " as" + username + " user");
        return true;
    }

    public void close(){
        if (this.db != null){
            try{
                this.db.close();;
            } catch (Exception e){
                e.printStackTrace();
            }
            this.db = null;
        }
    }

    public Connection getConnection() {
        return db;
    }
}