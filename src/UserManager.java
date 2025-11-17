import java.sql.*;

public class UserManager {
    public boolean registerUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("Përdoruesi u regjistrua me sukses!");
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error
                System.out.println("Ky emër përdoruesi ekziston tashmë!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Hyrja u krye me sukses!");
                return true;
            } else {
                System.out.println("Emri ose fjalëkalimi është i pasaktë!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
