package com.benwager12.lastbiscuit;

import java.util.HashMap;
import java.util.Scanner;

public class LastBiscuit {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int barrelOne = 6;
        int barrelTwo = 8;

        HashMap<Integer, Boolean> hasSkip = new HashMap<>();
        hasSkip.put(1, true);
        hasSkip.put(2, true);

        int playerTurn = 1;

        // Game loop
        while (barrelOne > 0 || barrelTwo > 0) {
            System.out.printf("Biscuits Left - Barrel 1: %d%n" +
                    "Biscuits Left - Barrel 2: %d%n", barrelOne, barrelTwo);
            System.out.println("Player Turn: " + playerTurn);

            System.out.print("Choose a barrel: barrel1 (one), barrel2 (two), or both (both), or skip turn (skip)? ");

            String selectedBarrel = in.nextLine();

            if (selectedBarrel.equalsIgnoreCase("skip")) {
                if (hasSkip.get(playerTurn)) {
                    playerTurn = switchPlayer(playerTurn);
                    hasSkip.put(playerTurn, false);
                    continue;
                }

                // no skip message
            }

            int biscuitAmount = askIntegerQuestion("How many biscuits are you taking?", in);

            if (selectedBarrel.equalsIgnoreCase("one") ||
                    selectedBarrel.equalsIgnoreCase("both")) {
                barrelOne -= biscuitAmount;
            }

            if (selectedBarrel.equalsIgnoreCase("two") ||
                    selectedBarrel.equalsIgnoreCase("both")) {
                barrelTwo -= biscuitAmount;
            }
            playerTurn = switchPlayer(playerTurn);
        }
    }

    public static int askIntegerQuestion(String question, Scanner in) {
        String result;

        System.out.printf("%s ", question);
        result = in.nextLine();

        if (isInteger(result)) {
            return Integer.parseInt(result);
        }

        int resultInteger = -1;
        boolean hasBeenSet = false;

        while (!hasBeenSet) {
            System.out.print("Please input an integer: ");
            result = in.nextLine();

            if (!isInteger(result)) {
                continue;
            }

            resultInteger = Integer.parseInt(result);
            hasBeenSet = true;
        }

        return resultInteger;
    }

    public static boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
}
