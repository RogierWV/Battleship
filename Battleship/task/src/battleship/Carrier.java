package battleship;

public class Carrier extends Ship {
    public static final String message = "Enter the coordinates of the Aircraft Carrier (5 cells):";
    public Carrier(Coordinate start, Coordinate end) {
        super(5, start, end);
    }
}
