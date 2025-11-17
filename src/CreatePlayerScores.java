import java.sql.Connection;
import java.sql.Statement;

public class CreatePlayerScores {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE PlayerScores ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Username VARCHAR(50), "
                    + "Score INT DEFAULT 0, "
                    + "ScoreDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(sql);
            connection.setAutoCommit(true);
            System.out.println("Tabela PlayerScores u krijua me sukses!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
