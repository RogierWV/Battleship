package battleship;

public class Submarine extends Ship {
    public static final String message = "Enter the coordinates of the Submarine (3 cells):";
    public Submarine(Coordinate start, Coordinate end) {
        super(3, start, end);
    }
}
