import java.sql.*;
import java.io.*;

public class MySQLDatabase {

    public GameState loadGame(String username) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM GameState WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                byte[] mazeData = resultSet.getBytes("MazeData");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mazeData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                int[][] maze = (int[][]) objectInputStream.readObject();

                return new GameState(maze,
                        resultSet.getInt("PlayerX"),
                        resultSet.getInt("PlayerY"),
                        resultSet.getInt("Treasures"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveGame(String username, int[][] maze, int playerX, int playerY, int treasures) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO GameState (Username, MazeData, PlayerX, PlayerY, Treasures) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(maze);
            byte[] mazeData = byteArrayOutputStream.toByteArray();
            statement.setBytes(2, mazeData);

            statement.setInt(3, playerX);
            statement.setInt(4, playerY);
            statement.setInt(5, treasures);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/maze_game", "root", "Manushaqe20@");
    }
}
