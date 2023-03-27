package battleship;

public class Cruiser extends Ship {
    public static final String message = "Enter the coordinates of the Cruiser (3 cells):";
    public Cruiser(Coordinate start, Coordinate end) {
        super(3, start, end);
    }
}
