package battleship;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    private final Ship[] ships;
    private int currentShips;
    private final List<Coordinate> misses;
    public Field() {
        ships = new Ship[5];
        currentShips = 0;
        misses = new ArrayList<>();
    }

    public void addShip(Ship ship) {
        for (Ship s: ships) {
            if(s == null) continue;
            if(Coordinate.overlap(s.start, s.end, ship.start, ship.end)){
                throw new IllegalArgumentException("Overlapping coordinates!");
            }
        }
        this.ships[this.currentShips] = ship;
        this.currentShips++;
    }

    public boolean shoot(Coordinate target) {
        for (Ship s : ships) {
            Coordinate[] coords = s.coords();
            for(int i = 0; i < coords.length; i++) {
                if(coords[i].equals(target)) {
                    s.hits[i] = true;
                    if(Arrays.asList(s.hits).contains(false))
                        System.out.println("You hit a ship!");
                    else
                        System.out.println("You sank a ship!");
                    return true;
                }
            }
        }
        misses.add(target);
        System.out.println("You missed!");
        return false;
    }

    public boolean shipsLeft() {
        for (Ship s : ships)
            for(Boolean hit : s.hits)
                if(!hit) return true;
        return false;
    }

    public String toString() {
        return render(true);
    }

    public String render() {
        return render(false);
    }

    public String render(boolean shipsVisible) {
        //build 2D character array to work with
        char[][] fieldRender = new char[10][10];
        for(int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (misses.contains(new Coordinate(j, i))) fieldRender[i][j] = 'M';
                else fieldRender[i][j] = '~';
            }

        //render ships (X for a hit, O for visible, ~ for invisible
        for (Ship ship: this.ships) {
            if(ship == null) continue;
            ship.render(fieldRender, !shipsVisible); //if shipsVisible, hitsOnly should be false
        }

        // turn the 2D char array into a well formatted string
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3 4 5 6 7 8 9 10\n");
        for(int i = 0; i < 10; i++) {
            sb.append(String.format("%s ", (char)(i+65)));
            for (int j = 0; j < 10; j++)
                sb.append(String.format("%s ", fieldRender[i][j]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
