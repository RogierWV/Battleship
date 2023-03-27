package battleship;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static Scanner s;
    public static PrintStream DEBUG_LOG;
    public static void main(String[] args) {
        try {
            DEBUG_LOG = new PrintStream(Paths.get(
                    System.getProperty("java.io.tmpdir"), "battleship.log").toString());
        } catch (FileNotFoundException e) {
            System.err.println("Can't create error log: " + e.getMessage());
        }
        s = new Scanner(System.in);
        System.out.println("Player 1, place your ships on the game field");
        Field player1 = setupPlayer();
        pass();
        System.out.println("Player 2, place your ships on the game field");
        Field player2 = setupPlayer();
        pass();
        while(true) { // game loop
            System.out.println(renderFields(player1, player2));
            System.out.println("Player 1, it's your turn:");
            while(!shoot(player2));
            if(!(player1.shipsLeft() && player2.shipsLeft())) break;
            pass();
            System.out.println(renderFields(player2, player1));
            System.out.println("Player 2, it's your turn:");
            while(!shoot(player1));
            if(!(player1.shipsLeft() && player2.shipsLeft())) break;
            pass();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public static boolean shoot(Field field) {
        try {
            field.shoot(new Coordinate(s.nextLine().trim()));//) {
//                System.out.println("You hit a ship!");
//            } else {
//                System.out.println("You missed!");
//            }
            return true;
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage() + " Try again:");
            return false;
        }
    }

    public static boolean addShip(Field field, Class<? extends Ship> shipType) {
        try {
            Coordinate start = new Coordinate(s.next());
            Coordinate end = new Coordinate(s.next());
            s.nextLine();
            DEBUG_LOG.printf("%s to %s%n", start, end);
            field.addShip(shipType.getConstructor(Coordinate.class, Coordinate.class).
                    newInstance(start, end));
            return true;
        } catch (Exception e) {
            System.out.printf("Error! %s Try again:%n",
                    ((e.getCause() != null) && ("java.lang.IllegalArgumentException".equals(
                            e.getCause().getClass().getName())))?
                            e.getCause().getMessage():e.getMessage());
            e.printStackTrace(DEBUG_LOG);
            return false;
        }
    }

    public static void addWrapper(Field field, String message, Class<? extends Ship> shipType) {
        System.out.println(message);
        while(!addShip(field, shipType));
        System.out.println("\n" + field);
    }

    public static void pass() {
        System.out.println("Press Enter and pass the move to another player");
        s.nextLine();
        clear();
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Field setupPlayer() {
        Field field = new Field();
        System.out.println(field.render());
        addWrapper(field, Carrier.message, Carrier.class);
        addWrapper(field, Battleship.message, Battleship.class);
        addWrapper(field, Submarine.message, Submarine.class);
        addWrapper(field, Cruiser.message, Cruiser.class);
        addWrapper(field, Destroyer.message, Destroyer.class);
        return field;
    }

    public static String renderFields(Field self, Field other) {
        return other.render(false) + "---------------------\n" +
                self.render(true);
    }
}
