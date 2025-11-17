public class Lojtar {

    private int pozicioniX;
    private int pozicioniY;
    private int thesarCounter;
    private int score;

    public Lojtar() {
        this.pozicioniX = 0;
        this.pozicioniY = 0;
        this.thesarCounter = 0;
        this.score = 0;
    }

    public void lundro(char drejtimi) {
        switch (drejtimi) {
            case '1': // Lart
                pozicioniX--;
                break;
            case '2': // Poshtë
                pozicioniX++;
                break;
            case '3': // Majtas
                pozicioniY--;
                break;
            case '4': // Djathtas
                pozicioniY++;
                break;
            default:
                System.out.println("Drejtimi i gabuar!");
        }
    }

    public void increaseThesarCounter() {
        thesarCounter++;
        score += 10;
    }
    public int getScore() {
        return score;
    }
    public int getThesarCounter() {
        return thesarCounter;
    }
    public void setPozicioniX(int pozicioniX) {
        this.pozicioniX = pozicioniX;
    }
    public int getPozicioniX() {
        return pozicioniX;
    }
    public void setPozicioniY(int pozicioniY) {
        this.pozicioniY = pozicioniY;
    }
    public int getPozicioniY() {
        return pozicioniY;
    }
    public void setThesarCounter(int thesarCounter) {
        this.thesarCounter = thesarCounter;
    }
}
