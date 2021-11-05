import java.util.HashMap;
import java.util.Scanner;

public class LastBiscuit {

    public static void main(String[] args) {
        // Declaring final variables
        final String OUTPUT_BARRELS_STRING = "Biscuits Left - Barrel 1: %d%n"
                + "Biscuits Left - Barrel 2: %d%n";
        final String PLAYER_TURN_STRING = "Player Turn: %d%n";

        // Preparing to use scanner for inputs
        Scanner in = new Scanner(System.in);

        // Magic numbers, in practice this would be put into a config file
        final int BARREL_ONE_START = 6;
        final int BARREL_TWO_START = 8;

        // Declaring each of the barrels with a predetermined value
        int barrelOne = BARREL_ONE_START;
        int barrelTwo = BARREL_TWO_START;

        /*
        Hashmap of which players have their skip
        If a player were to press skip it would change their value
         */
        HashMap<Integer, Boolean> hasSkip = new HashMap<>();
        hasSkip.put(1, true);
        hasSkip.put(2, true);

        // Initializing player's turn, it starts on player one.
        int playerTurn = 1;

        // Initially say the size of the barrels and the player's turn
        System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);
        System.out.printf(PLAYER_TURN_STRING, playerTurn);

        /*
        Game loop - This is where the main chunk of code is.
        This runs continuously until both barrels have reached 0.
         */
        while (barrelOne + barrelTwo > 0) {
            /*
            Until an option that is valid is chosen, keep asking the user for
            a selected input until this happens.
             */
            String selectedBarrel = "";
            while (isNotOption(selectedBarrel)) {
                System.out.print("Choose a barrel: barrel1 (one), barrel2 (two), "
                            + "or both (both), or skip turn (skip)? ");
                selectedBarrel = in.nextLine();
            }

            // If the current player enters skip
            if (selectedBarrel.equalsIgnoreCase("skip")) {
                if (hasSkip.get(playerTurn)) {
                    hasSkip.put(playerTurn, false);
                    playerTurn = switchPlayer(playerTurn);
                    System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);
                    System.out.printf(PLAYER_TURN_STRING, playerTurn);
                    continue;
                }
                System.out.println("Sorry you've used your skip.");
                continue;
            }

            boolean isOne = selectedBarrel.equalsIgnoreCase("one");
            boolean isTwo = selectedBarrel.equalsIgnoreCase("two");
            boolean isBoth = selectedBarrel.equalsIgnoreCase("both");

            int biscuitAmount = 0;
            int printType = 0;

            boolean foundBiscuitAmount = false;
            do {
                System.out.print(printType == 0
                        ? "How many biscuits are you taking? "
                        : "Please input an integer: ");

                String strBiscuitAmount = in.nextLine();
                if (!isNumeric(strBiscuitAmount)) {
                    printType = 1;
                    continue;
                }

                biscuitAmount = Integer.parseInt(strBiscuitAmount);

                foundBiscuitAmount = biscuitAmount > 0
                        && (!(((isOne || isBoth) && barrelOne - biscuitAmount < 0)
                        || ((isTwo || isBoth) && barrelTwo - biscuitAmount < 0)));

                if (!foundBiscuitAmount) {
                    System.out.println("Sorry, that's not a legal number of "
                            + "biscuits for that/those barrel(s)");
                    break;
                }
            } while (!foundBiscuitAmount);

            if (!foundBiscuitAmount) {
                continue;
            }

            if (isOne || isBoth) {
                barrelOne -= biscuitAmount;
            }

            if (isTwo || isBoth) {
                barrelTwo -= biscuitAmount;
            }

            System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);

            if (barrelOne > 0 || barrelTwo > 0) {
                playerTurn = switchPlayer(playerTurn);
                System.out.printf(PLAYER_TURN_STRING, playerTurn);
            }
        }
        System.out.println("Winner is player " + playerTurn);
    }

    // you give 1, you get 2
    // you give 2, you get 1
    // playerTurn = switchPlayer(playerTurn);
    public static int switchPlayer(int player) {
        switch (player) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public static boolean isNumeric(String str) {
        boolean isFirst = true;
        for (char c : str.toCharArray()) {
            if (!(Character.isDigit(c) || (isFirst && Character.toString(c).equals("-")))) {
                return false;
            }
            isFirst = false;
        }
        return true;
    }

    public static boolean isNotOption(String selectedOption) {
        String[] options = "one,two,both,skip".split(",");

        for (String op : options) {
            if (selectedOption.equalsIgnoreCase(op)) {
                return false;
            }
        }
        return true;
    }
}
