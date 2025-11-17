import java.sql.Connection;
import java.sql.Statement;

public class CreateTables {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Players ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Name VARCHAR(50), "
                    + "Score INT)";
            stmt.executeUpdate(sql);
            System.out.println("Tabela Players u krijua me sukses!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
