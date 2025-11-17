import java.util.Random;

public class Labirint {

    private int[][] rrjeti = new int[10][10];
    private int thesar = 8;
    private int daljeX;
    private int daljeY;
    private int nisjeX;
    private int nisjeY;
    private Random rand = new Random();

    public Labirint() {
    }

    public void gjeneroLabirint() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 || i == 9 || j == 0 || j == 9) {
                    rrjeti[i][j] = 1; // Muret e jashtme
                } else {
                    rrjeti[i][j] = rand.nextInt(2); // Rruga e lirë ose mur
                }
            }
        }

        // Vendos thesaret
        for (int i = 0; i < thesar; i++) {
            int x = rand.nextInt(5) + 1;
            int y = rand.nextInt(5) + 1;
            rrjeti[x][y] = 9;
        }

        // Vendos daljen
        daljeX = rand.nextInt(8) + 1;
        daljeY = rand.nextInt(8) + 1;
        rrjeti[daljeX][daljeY] = 8;

        // Vendos nisjen
        nisjeX = rand.nextInt(8) + 1;
        nisjeY = 1;
        rrjeti[nisjeX][nisjeY] = 7;
    }

    public boolean LidhjeHyrjeDalje() {
        boolean[][] vizituar = new boolean[10][10];
        return eksploro(nisjeX, nisjeY, vizituar);
    }

    private boolean eksploro(int x, int y, boolean[][] vizituar) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10 || eshteMur(x, y) || vizituar[x][y]) {
            return false;
        }

        if (eshteDalje(x, y)) {
            return true;
        }

        vizituar[x][y] = true;

        return eksploro(x + 1, y, vizituar) || eksploro(x - 1, y, vizituar) || eksploro(x, y + 1, vizituar) || eksploro(x, y - 1, vizituar);
    }

    public boolean eshteMur(int i, int j) {
        return rrjeti[i][j] == 1;
    }

    public boolean eshteThesar(int i, int j) {
        return rrjeti[i][j] == 9;
    }

    public boolean eshteDalje(int i, int j) {
        return rrjeti[i][j] == 8;
    }

    public void PrintoLabirint() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(rrjeti[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public int[][] getRrjeti() {
        return rrjeti;
    }

    public void setRrjeti(int[][] rrjeti) {
        this.rrjeti = rrjeti;
    }

    public int getNisjeX() {
        return nisjeX;
    }

    public int getNisjeY() {
        return nisjeY;
    }

    public int getDaljeX() {
        return daljeX;
    }

    public int getDaljeY() {
        return daljeY;
    }

    public void updateMaze(int oldX, int oldY, int newX, int newY) {
        rrjeti[oldX][oldY] = 0; // Pozicioni i mëparshëm bëhet rrugë
        rrjeti[newX][newY] = 7; // Pozicioni i ri bëhet pozicioni i lojtarit
    }
}
