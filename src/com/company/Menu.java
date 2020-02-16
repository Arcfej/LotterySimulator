package com.company;

import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

public class Menu {

    private static Currency pound = Currency.getInstance(Locale.UK);

    private LotterySystem lotterySystem;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
    }

    public Menu() {

    }

    private void showMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("WELCOME TO THE ARCFEJ LOTTERIES");
        System.out.println("We're delighted to offer you the opportunity, to win " +
                formatCurrency(LotterySystem.MATCH_6_WIN) + "!");
        System.out.println("Each ticket cost " + formatCurrency(LotterySystem.TICKET_PRICE) + ".");
        System.out.println("Right now you can set the range of numbers you can bet on.");
        System.out.println("Which should be the upper bound (included) of the game?");
        int upperBound;
        while (true) {
            System.out.print(formatCurrency(null));
            try {
                upperBound = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException ignored) {
                System.out.println("Please provide a valid whole number.");
            }
        }
        lotterySystem = new LotterySystem(upperBound);

        System.out.println("Thank you!");
        System.out.println("For how many weeks would you like to run your bet?");
    }

    public static String formatCurrency(Integer money) {
        String formatted = pound.getSymbol(Locale.UK);
        if (money != null) {
            formatted += String.format("%,d", money);
        }
        return formatted;
    }
}
