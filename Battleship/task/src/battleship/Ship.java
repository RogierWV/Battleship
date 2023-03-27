package battleship;

import java.util.Arrays;

abstract class Ship {
    @SuppressWarnings("unused")
    public static final String message = "An error occurred";
    int size;
    Boolean[] hits;
    Coordinate start;
    Coordinate end;

    public Ship(int size, Coordinate start, Coordinate end) {
        if(Coordinate.range(start,end).length != size)
            throw new IllegalArgumentException("Wrong length of " +
                    this.getClass().getSimpleName() + ", got " +
                    Coordinate.range(start,end).length
                    + ", expected " + size + "!");
        this.size = size;
        this.hits = new Boolean[size];
        Arrays.fill(hits, false);
        Coordinate[] tmp = {start, end};
        Arrays.sort(tmp);
        this.start = tmp[0];
        this.end = tmp[1];
    }

    public Coordinate[] coords() {
        return Coordinate.range(this.start, this.end);
    }

    public void render(char[][] buf, boolean onlyHits) {
        int pos = 0;
        for(int x = this.start.getX();
            x <= this.end.getX(); x++) {
            for(int y = this.start.getY();
                y <= this.end.getY(); y++) {
                buf[y][x] = ((this.hits[pos])?'X':(onlyHits?'~':'O'));
                pos++;
            }
        }
    }

    @SuppressWarnings("unused")
    public void render(char[][] buf) {
        render(buf, false);
    }
}