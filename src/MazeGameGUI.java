import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class MazeGameGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private JFrame mazeFrame;
    private KontrollerLojes gameController;
    private JButton[][] buttons;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeGameGUI().createAndShowLoginGUI());
    }

    public void createAndShowLoginGUI() {
        frame = new JFrame("Login / Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(230, 230, 250));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(100, 25));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(100, 25));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(100, 30));

        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> signup());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze_game", "root", "Manushaqe20@")) {
            String query = "SELECT * FROM Users WHERE Username = ? AND Password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(frame, "Login successful! Starting the game...");
                    startMazeGame(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze_game", "root", "Manushaqe20@")) {
            String query = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(frame, "Signup successful! You can now log in.");
                }
            }
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void startMazeGame(String username) {
        frame.dispose();
        mazeFrame = new JFrame("Maze Game");
        mazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mazeFrame.setLayout(new BorderLayout());

        gameController = new KontrollerLojes(username);
        buttons = new JButton[10][10];

        JPanel mazePanel = new JPanel(new GridLayout(10, 10));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setEnabled(false);
                buttons[i][j].setPreferredSize(new Dimension(50, 50));
                mazePanel.add(buttons[i][j]);
            }
        }
        mazeFrame.add(mazePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new GridLayout(2, 4));
        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        JButton saveButton = new JButton("Save Game");
        JButton loadButton = new JButton("Load Game");
        JButton viewScoresButton = new JButton("View Scores");

        controlPanel.add(upButton);
        controlPanel.add(downButton);
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(viewScoresButton);

        mazeFrame.add(controlPanel, BorderLayout.SOUTH);

        upButton.addActionListener(e -> movePlayer('1'));
        downButton.addActionListener(e -> movePlayer('2'));
        leftButton.addActionListener(e -> movePlayer('3'));
        rightButton.addActionListener(e -> movePlayer('4'));
        saveButton.addActionListener(e -> {
            gameController.saveGameState();
            JOptionPane.showMessageDialog(mazeFrame, "Game saved successfully!");
        });
        loadButton.addActionListener(e -> {
            gameController.loadGameState();
            JOptionPane.showMessageDialog(mazeFrame, "Game loaded successfully!");
            updateButtons();
        });

        // View Scores Button Action
        viewScoresButton.addActionListener(e -> showScores(username));

        updateButtons();
        mazeFrame.pack();
        mazeFrame.setLocationRelativeTo(null);
        mazeFrame.setVisible(true);
    }

    private void movePlayer(char direction) {
        gameController.movePlayer(direction);
        if (gameController.isHitWall()) {
            JOptionPane.showMessageDialog(mazeFrame, "You hit a wall. Game over!");
            mazeFrame.dispose();
        } else {
            updateButtons();
            if (gameController.kaFituar()) {
                JOptionPane.showMessageDialog(mazeFrame, "Congratulations! You won!");
                mazeFrame.dispose();
            }
        }
    }

    private void updateButtons() {
        int[][] maze = gameController.getLabirint().getRrjeti();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j].setBackground(getCellColor(maze[i][j]));
            }
        }
    }

    private Color getCellColor(int value) {
        switch (value) {
            case 1:
                return Color.BLACK; // Wall
            case 7:
                return Color.BLUE; // Player
            case 8:
                return Color.RED; // Exit
            case 9:
                return Color.YELLOW; // Treasure
            default:
                return Color.WHITE; // Path
        }
    }

    // Method to display scores sorted
    private void showScores(String username) {
        List<String> scores = fetchScoresFromDatabase(username);

        if (scores.isEmpty()) {
            JOptionPane.showMessageDialog(mazeFrame, "No scores found for this user.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] sortingOptions = {"Sort by Date", "Sort by Score"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortingOptions);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Sort by:"), BorderLayout.NORTH);
        panel.add(sortComboBox, BorderLayout.CENTER);

        JList<String> scoreList = new JList<>(scores.toArray(new String[0]));
        panel.add(new JScrollPane(scoreList), BorderLayout.SOUTH);

        int option = JOptionPane.showConfirmDialog(mazeFrame, panel, "View Scores", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String selectedSortOption = (String) sortComboBox.getSelectedItem();
            if (selectedSortOption.equals("Sort by Date")) {
                scores.sort(Comparator.comparing(s -> s.split(" - ")[1])); // Sort by date
            } else if (selectedSortOption.equals("Sort by Score")) {
                scores.sort((s1, s2) -> {
                    int score1 = Integer.parseInt(s1.split(": ")[1].split(" - ")[0]);
                    int score2 = Integer.parseInt(s2.split(": ")[1].split(" - ")[0]);
                    return Integer.compare(score2, score1); // Descending
                });
            }
            scoreList.setListData(scores.toArray(new String[0]));
            JOptionPane.showMessageDialog(mazeFrame, new JScrollPane(scoreList), "Sorted Scores", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // Fetch scores from database
    private List<String> fetchScoresFromDatabase(String username) {
        List<String> scores = new ArrayList<>();
        System.out.println("Fetching scores for username: " + username); // Debugging line

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze_game", "root", "Manushaqe20@")) {
            String query = "SELECT * FROM PlayerScores WHERE Username = ? ORDER BY Score DESC";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String score = "Score: " + resultSet.getInt("Score") + " - " + resultSet.getDate("ScoreDate");
                    scores.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (scores.isEmpty()) {
            System.out.println("No scores found for this user.");
        }

        return scores;
    }

}
