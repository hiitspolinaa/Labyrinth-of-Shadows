import java.sql.Connection;
import java.sql.Statement;

public class CreateGameStateTable {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE GameState ("
                    + "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Username VARCHAR(50), "
                    + "MazeData BLOB, "  // Përdorim BLOB për të ruajtur labirintin në formë binare
                    + "PlayerX INT, "
                    + "PlayerY INT, "
                    + "Treasures INT)";
            stmt.executeUpdate(sql);
            System.out.println("Tabela GameState u krijua me sukses!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
