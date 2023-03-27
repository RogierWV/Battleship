package battleship;

public class Destroyer extends Ship {
    public static final String message = "Enter the coordinates of the Destroyer (2 cells):";
    public Destroyer(Coordinate start, Coordinate end) {
        super(2, start, end);
    }
}
