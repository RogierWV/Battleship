package battleship;

import java.util.Arrays;

public class Coordinate implements Comparable {
//public class Coordinate {
    private int x;
    private int y;
    public Coordinate(String coord) {
        if(!coord.matches("[A-J][0-9]+"))
            throw new IllegalArgumentException("Invalid coordinate " + coord + "!");
        this.x = Integer.parseInt(coord.substring(1).trim()) - 1;
        this.y = Character.codePointAt(coord, 0) - 65;
        if(x >= 10 || y >= 10) throw new IllegalArgumentException("Invalid coordinate " + coord + "!");
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        if(x >= 10 || y >= 10) throw new IllegalArgumentException("Invalid coordinate " + x  + ", " + y + "!");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Coordinate[] range(Coordinate start, Coordinate end) {
        int size = Math.max(Math.abs(start.getX() - end.getX()),
                Math.abs(start.getY() - end.getY())) + 1;
        Coordinate[] ret = new Coordinate[size];
        int count = 0;
        if(start.getX() == end.getX())
            for(int y = start.getY(); y <= end.getY(); y++)
                ret[count++] = new Coordinate(start.getX(), y);
        else if (start.getY() == end.getY())
            for(int x = start.getX(); x <= end.getX(); x++)
                ret[count++] = new Coordinate(x, start.getY());
        else throw new IllegalArgumentException("No straight line possible from " +
                    start + " to " + end + "!");
        Main.DEBUG_LOG.println(Arrays.toString(ret));
        return ret;
    }

    public static boolean overlap(Coordinate startA, Coordinate endA,
                                  Coordinate startB, Coordinate endB) {
        for(Coordinate a : Coordinate.range(startA, endA)) {
            Main.DEBUG_LOG.printf("a: %s%n", a);
            if (a == null) continue;
            for (Coordinate b : Coordinate.range(startB, endB)) {
                Main.DEBUG_LOG.printf("b: %s%n", b);
                if (b == null) continue;
                if (a.closeTo(b)) {
                    Main.DEBUG_LOG.printf("Too close: %s and %s%n", a, b);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean closeTo(Coordinate other) {
        if(other == null) return false;
        return (Math.abs(this.x - other.getX()) <= 1 &&
                Math.abs(this.y - other.getY()) <= 1);
    }

    @Override
    public String toString() {
        return String.format("%s%d", ((char)(y+65)), x+1);
    }

    @Override
    public int compareTo(Object o) {
        if(o == null) return 1;
        if(((Coordinate)o).getX() == x)
            return y - ((Coordinate)o).getY();
        if(((Coordinate)o).getY() == y)
            return x - ((Coordinate)o).getX();
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return ((Coordinate)o).getX() == x && ((Coordinate)o).getY() == y;
    }
}
