package com.company;

import java.util.*;
import java.util.function.Predicate;

/**
 * A menu for the Lottery game. Handles the communication between the user(s) and the system.
 */
public class Menu {

    /**
     * The currency of the game
     */
    private static Currency pound;

    /**
     * The input stream through the users communicate with the program
     */
    private Scanner in;

    /**
     * The lottery system
     */
    private LotterySystem lotterySystem;

    /**
     * The number of weeks the lottery is running for
     */
    private int weeks;

    /**
     * The players of the lottery
     */
    private Set<Player> players;

    /**
     * Entry point of the program
     *
     * @param args not used
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
    }

    /**
     * Base constructor of the class
     */
    public Menu() {
        in = new Scanner(System.in);
        players = new MySet<>();
        pound = Currency.getInstance(Locale.UK);
    }

    /**
     * Show the menu to the user
     */
    public void showMenu() {
        showIntro();

        int upperBound = getUpperBound();
        lotterySystem = new LotterySystem(upperBound);

        weeks = getWeeks();

        do {
            Player player = getName();
            player.setTicket(createTicket(player));
            players.add(player);
        } while (isNextPlayer());

        System.out.println("Lets draw!");

        startDraw();
    }

    /**
     * Show the intro of the Lottery game
     */
    private void showIntro() {
        System.out.println();
        System.out.println("WELCOME TO THE ARCFEJ LOTTERIES");
        System.out.println();
        System.out.println("We're delighted to offer you the opportunity, to win " +
                formatCurrency(LotterySystem.MATCH_6_WIN) + "!");
        System.out.println("Each ticket cost " + formatCurrency(LotterySystem.TICKET_PRICE) + ".");
        System.out.println();
    }

    /**
     * Get the upper bound used in the Lottery from the user. It should be bigger than 6 and a whole number.
     *
     * @return the validated number the user provided
     */
    private int getUpperBound() {
        System.out.println("Right now you can set the range of numbers you can bet on.");
        int upperBound = getIntInput("Which should be the upper bound (included) of the game (more than 6)?",
                i -> i > 6,
                "Please provide a whole number bigger than 6.");
        thankInput();
        return upperBound;
    }

    /**
     * Get the number of weeks the lottery will run for from the user. It must be bigger than 0 and a whole number.
     *
     * @return the validated number the user provided
     */
    private int getWeeks() {
        int weeks = getIntInput("For how many weeks would you like to run your bet?",
                i -> i >= 1,
                "Please provide a whole, positive number.");
        thankInput();
        return weeks;
    }

    /**
     * Get the name of the user. Duplicated names not allowed.
     *
     * @return a new Player with the not empty name provided by the user.
     */
    private Player getName() {
        Player player;
        while (true) {
            System.out.println("What is your name?");
            String name = in.nextLine();
            player = new Player(name);
            if (name.isBlank()) {
                System.out.println("Please provide a name");
            } else if (players.contains(player)) {
                System.out.println("You've already placed your bet. Yield the game to the next player.");
            } else {
                // break if the input is valid
                break;
            }
        }
        thankInput();
        return player;
    }

    /**
     * Create a ticket for a player.
     *
     * @param player The player to create the ticket for.
     * @return The created ticket
     */
    private LotterySystem.Ticket createTicket(Player player) {
        while (true) {
            try {
                MySet<Integer> bet = getBet();
                LotterySystem.Ticket ticket = new LotterySystem.Ticket(player.getName(), bet);
                lotterySystem.buyTicket(ticket);
                return ticket;
            } catch (WrongCountOfNumbersException | NotValidNumberException e) {
                System.out.println("Some error occurred during your ticket creation. Please try again.");
            }
        }
    }

    /**
     * Take 6 number from the user for a ticket to buy.
     * The numbers must be whole and in the range of the current lottery game.
     *
     * @return a set of validated numbers for a ticket.
     */
    private MySet<Integer> getBet() {
        MySet<Integer> numbers = new MySet<>(LotterySystem.DRAW_COUNT);
        while (numbers.size() < LotterySystem.DRAW_COUNT) {
            boolean success = numbers.add(
                    getIntInput("What is your " + getOrdinalNumber(numbers.size() + 1) + " number?",
                            i -> i > 0 && i <= LotterySystem.getLotteryMax(),
                            "Please provide a valid number between 1 and " + LotterySystem.getLotteryMax() + ".")
            );
            if (!success) {
                System.out.println("You've already picked that number. Choose another one");
            }
        }
        thankInput();
        return numbers;
    }

    /**
     * Ask for the user(s) if there is another player who would like to bet.
     *
     * @return true if there is another player.
     */
    private boolean isNextPlayer() {
        System.out.println("Is there another player who would like to bet (y/n)?");
        String answer = in.nextLine();
        System.out.println();
        return answer.equals("y") || answer.equals("Y");
    }

    /**
     * Display a thank you message to the user.
     */
    private void thankInput() {
        System.out.println();
        System.out.println("Thank you!");
        System.out.println();
    }

    /**
     * Start the draw of the lottery, display the results of every week's draw and the final outcome too.
     */
    private void startDraw() {
        // Run a draw for every required week
        for (int i = 0; i < weeks; i++) {
            players.forEach(Player::buyTicket);

            // Draw the winning numbers for the week and the winners of the week.
            Map<String, Set<Integer>> wins = lotterySystem.drawWinners();
            // Display tha drawn numbers
            System.out.print("The " + getOrdinalNumber(i + 1) + " week's winning numbers are: ");
            Set<Integer> winnerSet = wins.remove(LotterySystem.WINNER_SET_KEY);
            winnerSet.stream()
                    .sorted(Comparator.naturalOrder())
                    .forEach(number -> System.out.print(number + "  "));
            System.out.println();

            // Display the winners
            wins.forEach((key, value) -> {
                // The won amount
                int win = LotterySystem.WINS[value.size() - 1];

                if (win > 0) {
                    Player winner = players.stream()
                            .filter(player -> player.getName().equals(key))
                            .findAny()
                            .orElse(null);

                    if (winner != null) {
                        winner.addWin(win);

                        System.out.print(winner.getName() + " won " + formatCurrency(win) + " with the numbers: ");
                        value.forEach(number -> System.out.print(number + " "));
                        System.out.println();
                    }
                }
            });
            System.out.println();
        }

        // Display the final outcome after the weekly draws have ended.
        System.out.println("Final payoff: ");
        players.forEach(player -> {
            System.out.println(player.getName() + " spent " + formatCurrency(player.getMoneySpent()) + " and won " + formatCurrency(player.getMoneyWon()) + ".");
            int win = player.getMoneyWon() - player.getMoneySpent();
            System.out.println(player.getName() + "'s money changed by " + formatCurrency(win));
        });

    }

    /**
     * Format an amount of money to display as currency.
     *
     * @param money The amount to format
     * @return The formattet amount of money as String
     */
    private static String formatCurrency(Integer money) {
        String formatted = pound.getSymbol(Locale.UK);
        if (money != null) {
            formatted += String.format("%,d", money);
        }
        return formatted;
    }

    /**
     * Get an integer from the user.
     *
     * @param inputMessage The message to display before the input to the user
     * @param condition The condition the input have to meet
     * @param errorMessage The message to display if the user provided an invalid input
     * @return The validated whole number from the user.
     */
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

    /**
     * Transform a whole number to ordinal number.
     *
     * @param number to transform
     * @return The ordinal number as String
     */
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
