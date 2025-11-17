import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerScoreManager {

    public void savePlayerScore(String username, int score) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO PlayerScores (Username, Score) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, score);
            statement.executeUpdate();
            System.out.println("Piket e lojtarit u ruajten me sukses!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPlayerScores(String username) {
        List<String> scores = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Score, ScoreDate FROM PlayerScores WHERE Username = ? ORDER BY Score DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int score = resultSet.getInt("Score");
                Timestamp date = resultSet.getTimestamp("ScoreDate");
                scores.add("Pike: " + score + " - Data: " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
