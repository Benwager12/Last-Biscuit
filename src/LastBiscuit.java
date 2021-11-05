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
                // Check if they actually have that skip
                if (hasSkip.get(playerTurn)) {
                    // Change the Hashmap so they don't have a skip
                    hasSkip.put(playerTurn, false);
                    // Switch the player, so when it continues it will be on a different one
                    playerTurn = switchPlayer(playerTurn);
                    // Output the barrels and the player turn
                    System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);
                    System.out.printf(PLAYER_TURN_STRING, playerTurn);
                } else {
                    // Message the player about no skip left and stay on the same player
                    System.out.println("Sorry you've used your skip.");
                }
                continue;
            }

            // Checking which option the player entered
            boolean isOne = selectedBarrel.equalsIgnoreCase("one");
            boolean isTwo = selectedBarrel.equalsIgnoreCase("two");
            boolean isBoth = selectedBarrel.equalsIgnoreCase("both");

            // Initialize the biscuit amount as 0
            int biscuitAmount = 0;
            // Boolean to check if player entered an integer, is false by default
            boolean enteredInteger = false;
            // Boolean to check if the program has found the biscuit amount.
            boolean foundBiscuitAmount = false;

            // Loop around until a biscuit amount is found
            do {
                /*
                If the player entered an integer, show the first
                string, otherwise show the second one.
                */
                System.out.print(enteredInteger
                        ? "Please input an integer: "
                        : "How many biscuits are you taking? ");

                // Get the next line of the input
                String strBiscuitAmount = in.nextLine();
                // Checking if input item is numeric
                if (!isNumeric(strBiscuitAmount)) {
                    // Make it sure that the next time around, it asks for an integer
                    enteredInteger = true;
                    continue;
                }

                // Parse the inputted item to an integer
                biscuitAmount = Integer.parseInt(strBiscuitAmount);

                /*
                Condition for whether the integer is within the parameters
                biscuitAmount > 0 - Check whether the biscuit amount is more than 0.
                ((isOne || isBoth) && barrelOne - biscuitAmount < 0) - Check
                if the barrel amount would go below 0 for the value entered (for barrel one).
                ((isTwo || isBoth) && barrelTwo - biscuitAmount < 0) - Check
                if the barrel amount would go below 0 for the value entered (for barrel two).
                 */
                foundBiscuitAmount = biscuitAmount > 0
                        && (!(((isOne || isBoth) && barrelOne - biscuitAmount < 0)
                        || ((isTwo || isBoth) && barrelTwo - biscuitAmount < 0)));

                // Do the below logic if the above conditioned is not satisfied
                if (!foundBiscuitAmount) {
                    System.out.println("Sorry, that's not a legal number of "
                            + "biscuits for that/those barrel(s)");
                    // Get out of this while loop
                    break;
                }
            } while (!foundBiscuitAmount);

            // If a biscuit amount isn't found, loop back around
            if (!foundBiscuitAmount) {
                continue;
            }

            // If you selected one or both, take away the biscuit amount from barrel one
            if (isOne || isBoth) {
                barrelOne -= biscuitAmount;
            }

            // If you selected two or both, take away the biscuit amount from barrel one
            if (isTwo || isBoth) {
                barrelTwo -= biscuitAmount;
            }

            // If both is selected then both conditions will be satisfied

            // Print out the barrels
            System.out.printf(OUTPUT_BARRELS_STRING, barrelOne, barrelTwo);

            // As long as at least one of the barrels are more than one, switch the players
            if (barrelOne + barrelTwo > 0) {
                // Set the player to the function switchPlayer on the player turn.
                playerTurn = switchPlayer(playerTurn);
                // Print the new player
                System.out.printf(PLAYER_TURN_STRING, playerTurn);
            }
        }
        // Print the winner, this is only if it exits this loop
        System.out.println("Winner is player " + playerTurn);
    }

    public static int switchPlayer(int player) {
        /*
        switchPlayer(int) -> int
        This is executed at the end of a player's turn, if it's player 1's
        turn, return player 2, and vice versa for player 2.
        The default will never happen but is there to satisfy checkstyle
         */
        switch (player) {
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                // Impossible to get to this point in the real game.
                return 0;
        }
    }

    public static boolean isNumeric(String input) {
        /*
        isNumeric(String) -> boolean
        This would get given a string (named input) and it would loop
        through every character and make sure that they match that of
        a number, called for checking the biscuit amount.
         */

        // Boolean for if it's the first character in the sequence
        boolean isFirst = true;
        for (char loopChar : input.toCharArray()) {
            /*
            If the character is a not digit anytime or if it's not
            an - on the first iteration
             */
            if (!(Character.isDigit(loopChar)
                    ||(isFirst && Character.toString(loopChar).equals("-")))) {
                return false;
            }

            /*
            It has run through once, after this, it will no longer be
            the first iteration
             */
            isFirst = false;
        }
        // It is an integer at this point
        return true;
    }

    public static boolean isNotOption(String selectedOption) {
        /*
        isNotOption(String) -> boolean
        Check if the selected option is not within the options array
         */
        String[] options = "one,two,both,skip".split(",");

        // Loop through the strings
        for (String op : options) {
            // See if it matches this
            if (selectedOption.equalsIgnoreCase(op)) {
                return false;
            }
        }
        return true;
    }
}
