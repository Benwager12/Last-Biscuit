import java.util.HashMap;
import java.util.Scanner;

public class LastBiscuit {

    public static void main(String[] args) {
        final String OUTPUT_BARRELS_STRING = "Biscuits Left - Barrel 1: %d%n"
                + "Biscuits Left - Barrel 2: %d%n";
        final String PLAYER_TURN_STRING = "Player Turn: %d%n";
        final String BISCUITS_TAKING = "How many biscuits are you taking? ";

        Scanner in = new Scanner(System.in);

        final int SIX = 6;
        final int EIGHT = 8;

        int barrelOne = SIX;
        int barrelTwo = EIGHT;

        // Hashmap of which players have their skip
        HashMap<Integer, Boolean> hasSkip = new HashMap<>();
        hasSkip.put(1, true);
        hasSkip.put(2, true);

        int playerTurn = 1;

        System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);
        System.out.printf(PLAYER_TURN_STRING, playerTurn);

        // Variables will be redefined in the game in each turn
        boolean isOne;
        boolean isTwo;
        boolean isBoth;

        // Game loop
        while (barrelOne + barrelTwo > 0) {
            String selectedBarrel = "";
            while (isNotOption(selectedBarrel)) {
                System.out.print("Choose a barrel: barrel1 (one), barrel2 (two), "
                            + "or both (both), or skip turn (skip)? ");
                selectedBarrel = in.nextLine();

            }

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

            isOne = selectedBarrel.equalsIgnoreCase("one")
                    || selectedBarrel.equalsIgnoreCase("1");
            isTwo = selectedBarrel.equalsIgnoreCase("two")
                    || selectedBarrel.equalsIgnoreCase("2");
            isBoth = selectedBarrel.equalsIgnoreCase("both");

            int biscuitAmount = 0;
            boolean foundBiscuitAmount = false;

            int printType = 0;
            do {
                System.out.print(printType == 0 ?
                        BISCUITS_TAKING : "Please input an integer: ");

                String strBiscuitAmount = in.nextLine();
                if (!isNumeric(strBiscuitAmount)) {
                    printType = 1;
                    continue;
                }

                biscuitAmount = Integer.parseInt(strBiscuitAmount);

                foundBiscuitAmount = biscuitAmount > 0 && (!(((isOne || isBoth) && barrelOne - biscuitAmount < 0)
                        || ((isTwo || isBoth) && barrelTwo - biscuitAmount < 0)));

                if (!foundBiscuitAmount) {
                    printType = 0;
                    System.out.println("Sorry, that's not a legal number of "
                            + "biscuits for that/those barrel(s)");
                }
            } while (!foundBiscuitAmount);

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
            if (!Character.isDigit(c) && (!(isFirst && str.equalsIgnoreCase("-")))) {
                return false;
            }
            isFirst = false;
        }
        return true;
    }

    public static boolean isNotOption(String selectedOption) {
        String[] options = "one,two,both,skip,1,2".split(",");

        for (String op : options) {
            if (selectedOption.equalsIgnoreCase(op)) {
                return false;
            }
        }
        return true;
    }
}
