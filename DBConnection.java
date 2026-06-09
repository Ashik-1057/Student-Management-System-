import java.sql.*;
import javax.swing.*;
public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdb", "root", "");
        } catch(Exception e) { 
            JOptionPane.showMessageDialog(null, "DB Error: " + e.getMessage());
            return null; 
        }
    }
}