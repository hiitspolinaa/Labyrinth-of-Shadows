import java.sql.*;
import java.io.*;

public class KontrollerLojes {
    private Labirint labirint;
    private Lojtar lojtar;
    private boolean hitWall;
    private MySQLDatabase database;
    private String username;

    public KontrollerLojes(String username) {
        this.labirint = new Labirint();
        this.lojtar = new Lojtar();
        this.hitWall = false;
        this.database = new MySQLDatabase();
        this.username = username;
        gjeneroLabirintDheLojtar();
    }

    public boolean hasValidPath() {
        return labirint.LidhjeHyrjeDalje();
    }

    public boolean isHitWall() {
        return hitWall;
    }

    private void gjeneroLabirintDheLojtar() {
        labirint.gjeneroLabirint();
        while (!labirint.LidhjeHyrjeDalje()) {
            labirint.gjeneroLabirint();
        }
        lojtar.setPozicioniX(labirint.getNisjeX());
        lojtar.setPozicioniY(labirint.getNisjeY());
    }

    public boolean kaFituar() {
        return lojtar.getPozicioniX() == labirint.getDaljeX() && lojtar.getPozicioniY() == labirint.getDaljeY();
    }

    public void saveGameState() {
        database.saveGame(username, labirint.getRrjeti(), lojtar.getPozicioniX(), lojtar.getPozicioniY(), lojtar.getThesarCounter());
    }

    public void loadGameState() {
        GameState state = database.loadGame(username);
        if (state != null) {
            labirint.setRrjeti(state.getMaze());
            lojtar.setPozicioniX(state.getPlayerX());
            lojtar.setPozicioniY(state.getPlayerY());
            lojtar.setThesarCounter(state.getTreasures());
            System.out.println("Loja u ngarkua me sukses.");
        } else {
            System.out.println("Nuk u gjet asnjë lojë e ruajtur për këtë përdorues.");
        }
    }

    public void saveScore() {
        int score = lojtar.getScore();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maze_game", "root", "Manushaqe20@")) {
            String query = "INSERT INTO PlayerScores (Username, Score, ScoreDate) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setInt(2, score);
                statement.setDate(3, currentDate);
                statement.executeUpdate();
                System.out.println("Score saved: " + score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void movePlayer(char drejtimi) {
        int oldX = lojtar.getPozicioniX();
        int oldY = lojtar.getPozicioniY();

        lojtar.lundro(drejtimi);

        int newX = lojtar.getPozicioniX();
        int newY = lojtar.getPozicioniY();

        if (labirint.eshteMur(newX, newY)) {
            hitWall = true;
        } else {
            if (labirint.eshteDalje(newX, newY)) {
                System.out.println("Ju fituat! Urime, keni fituar!");
                saveScore();
                System.exit(0);
            }

            if (labirint.eshteThesar(newX, newY)) {
                lojtar.increaseThesarCounter();
                System.out.println("Mblodhët një thesar! Totali: " + lojtar.getThesarCounter());
            }

            labirint.updateMaze(oldX, oldY, newX, newY);
        }

        if (hitWall) {
            System.out.println("Ju u përplasët në mur. Loja mbyllet!");
            System.exit(0);
        }

        if (kaFituar()) {
            saveScore();
            System.out.println("Ju fituat! Urime, keni fituar!");
            System.out.println("Thesare te mbledhura: " + lojtar.getThesarCounter());
            System.exit(0);
        }
    }

    public Labirint getLabirint() {
        return labirint;
    }
}
