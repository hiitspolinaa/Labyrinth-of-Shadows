import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateUserTable {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Users ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Username VARCHAR(50) UNIQUE, "
                    + "Password VARCHAR(50))";
            stmt.executeUpdate(sql);
            System.out.println("Tabela Users u krijua me sukses!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
