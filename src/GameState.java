import java.io.Serializable;

public class GameState implements Serializable {

    private int[][] maze;
    private int playerX;
    private int playerY;
    private int treasures;

    public GameState(int[][] maze, int playerX, int playerY, int treasures) {
        this.maze = maze;
        this.playerX = playerX;
        this.playerY = playerY;
        this.treasures = treasures;
    }

    // Getters dhe Setters
    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getTreasures() {
        return treasures;
    }

    public void setTreasures(int treasures) {
        this.treasures = treasures;
    }
}
