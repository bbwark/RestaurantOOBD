import java.sql.*;
class Connessione {
            public static void main(String args[]) throws Exception {

                try {
                    Class.forName("org.postgresql.Driver");

                            String url = "jdbc:postgresql://localhost:5432/OOBD";
                            Connection conn = DriverManager.getConnection(url, "postgres", "cavallo");
                            System.out.println("Connessione OK \n");
                            conn.close();
                }

                catch (ClassNotFoundException e) {
                    System.out.println("DB driver not found \n");
                    System.out.println(e);
                }
                catch (SQLException e){
                    System.out.println("Connessione Fallita \n");
                    System.out.println(e);
                }
            }
}