package DBConnection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    Connection conn = null;
    public static Connection conDB()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/pos","root","");
            return con;
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            System.err.println("DBConnection: "+ex.getMessage());
           return null;
        }
    }
}
