package com.company;

import java.util.*;
import java.util.function.Predicate;

public class Menu {

    private static Currency pound = Currency.getInstance(Locale.UK);

    private Scanner in;

    private LotterySystem lotterySystem;

    private int weeks;

    private Set<String> names = new HashSet<>();

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
    }

    public Menu() {
        in = new Scanner(System.in);
    }

    private void showMenu() {
        showIntro();

        int upperBound = getUpperBound();
        lotterySystem = new LotterySystem(upperBound);

        weeks = getWeeks();

        do {
            String name = getName();
            createTicket(name);
        } while (isNextPlayer());

        System.out.println("Lets draw!");

        startDraw();
    }

    private void showIntro() {
        System.out.println();
        System.out.println("WELCOME TO THE ARCFEJ LOTTERIES");
        System.out.println();
        System.out.println("We're delighted to offer you the opportunity, to win " +
                formatCurrency(LotterySystem.MATCH_6_WIN) + "!");
        System.out.println("Each ticket cost " + formatCurrency(LotterySystem.TICKET_PRICE) + ".");
        System.out.println();
    }

    private int getUpperBound() {
        System.out.println("Right now you can set the range of numbers you can bet on.");
        int upperBound = getIntInput("Which should be the upper bound (included) of the game (min. 6)?",
                i -> i > 6,
                "Please provide a whole number bigger than 6.");
        thankInput();
        return upperBound;
    }

    private int getWeeks() {
        int weeks = getIntInput("For how many weeks would you like to run your bet?",
                i -> i >= 1,
                "Please provide a whole, positive number.");
        thankInput();
        return weeks;
    }

    private String getName() {
        System.out.println("What is your name?");
        String name = in.nextLine();
        if (name.isBlank()) {
            System.out.println("Please provide a name");
        } else if (names.contains(name)) {
            System.out.println("You've already placed your bet. Yield the game to the next player.");
        }
        names.add(name);
        thankInput();
        return name;
    }

    private void createTicket(String name) {
        while (true) {
            try {
                Set<Integer> bet = getBet();
                lotterySystem.buyTicket(new LotterySystem.Ticket(name, bet));
                break;
            } catch (WrongCountOfNumbersException | NotValidNumberException e) {
                System.out.println("Some error occurred during your ticket creation. Please try again.");
            }
        }
    }

    private Set<Integer> getBet() {
        Set<Integer> numbers = new HashSet<>(LotterySystem.DRAW_COUNT);
        while (numbers.size() < LotterySystem.DRAW_COUNT) {
            boolean success = numbers.add(
                    getIntInput("What is your " + getOrdinalNumber(numbers.size() + 1) + "number?",
                            i -> i > 0 && i < LotterySystem.getLotteryMax(),
                            "Please provide a valid number between 1 and " + LotterySystem.getLotteryMax() + ".")
            );
            if (!success) {
                System.out.println("You've already picked that number. Choose another one");
            }
        }
        thankInput();
        return numbers;
    }

    private boolean isNextPlayer() {
        System.out.println("Is there another player who would like to bet (y/n)?");
        String answer = in.nextLine();
        System.out.println();
        return answer.equals("y") || answer.equals("Y");
    }

    private void thankInput() {
        System.out.println();
        System.out.println("Thank you!");
        System.out.println();
    }

    private void startDraw() {
        for (int i = 0; i < weeks; i++) {
            System.out.print("The " + getOrdinalNumber(i) + " week's winning numbers are: ");

        }
    }

    private static String formatCurrency(Integer money) {
        String formatted = pound.getSymbol(Locale.UK);
        if (money != null) {
            formatted += String.format("%,d", money);
        }
        return formatted;
    }

    private int getIntInput(String inputMessage, Predicate<Integer> condition, String errorMessage) {
        int input;
        while (true) {
            System.out.println(inputMessage);
            try {
                input = Integer.parseInt(in.nextLine());
                if (condition.test(input)) {
                    return input;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println(errorMessage);
        }
    }

    private String getOrdinalNumber(int number) {
        String suffix;
        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                suffix = "th";
                break;
            default:
                switch (number % 10) {
                    case 1:
                        suffix = "st";
                        break;
                    case 2:
                        suffix = "nd";
                        break;
                    case 3:
                        suffix = "rd";
                        break;
                    default:
                        suffix = "th";
                }
        }
        return number + suffix;
    }
}
