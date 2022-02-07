package Model.PostgresJDBC_DAO_Impl.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePostgres {

    private Connection db;
    private String url;
    private String username;
    private String password;


    public DatabasePostgres(String host, String port, String databaseName, String username, String password) {
        url = "jdbc:postgresql://" + host + ":" + port + "/" + databaseName;
        this.username = username;
        this.password = password;
        db = null;
    }

    public boolean openConnection() {
        try {
            this.db = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\nConnection Failed");
            return false;
        }
        System.out.println("Connection Succeeded");
        System.out.println("Connected with " + url);
        return true;
    }

    public void closeConnection (){
        if (this.db != null){
            try{
                this.db.close();;
            } catch (Exception e){
                e.printStackTrace();
            }
            this.db = null;
        }
    }
}