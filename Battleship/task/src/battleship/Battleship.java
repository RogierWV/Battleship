package battleship;

public class Battleship extends Ship {
    public static final String message = "Enter the coordinates of the Battleship (4 cells):";
    public Battleship(Coordinate start, Coordinate end) {
        super(4, start, end);
    }
}
