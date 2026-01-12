package controllerr;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/crudestudante?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";    
    private static final String PASSWORD = "Edyb62122"; 

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

