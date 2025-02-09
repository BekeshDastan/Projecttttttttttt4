import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class db_connection {
    public Connection connect_to_db(String dbname,String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
            if(conn!=null){
                System.out.println("Connected to database");
            }
            else{
                System.out.println("Not connected to database");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }

    }
