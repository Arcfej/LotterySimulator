package com.company;

import java.util.*;
import java.util.function.Predicate;

public class Menu {

    private static Currency pound = Currency.getInstance(Locale.UK);

    private Scanner in;

    private LotterySystem lotterySystem;

    private int weeks;

    private Set<Player> players = new MySet<>();

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
    }

    public Menu() {
        in = new Scanner(System.in);
    }

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
        int upperBound = getIntInput("Which should be the upper bound (included) of the game (more than 6)?",
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
                break;
            }
        }
        thankInput();
        return player;
    }

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
            players.forEach(Player::buyTicket);

            Map<String, Set<Integer>> wins = lotterySystem.drawWinners();

            System.out.print("The " + getOrdinalNumber(i + 1) + " week's winning numbers are: ");
            Set<Integer> winnerSet = wins.remove(LotterySystem.WINNER_SET_KEY);
            winnerSet.stream()
                    .sorted(Comparator.naturalOrder())
                    .forEach(number -> System.out.print(number + "  "));
            System.out.println();

            wins.forEach((key, value) -> {
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

        System.out.println("Final payoff: ");
        players.forEach(player -> {
            System.out.println(player.getName() + " spent " + formatCurrency(player.getMoneySpent()) + " and won " + formatCurrency(player.getMoneyWon()) + ".");
            int win = player.getMoneyWon() - player.getMoneySpent();
            System.out.println(player.getName() + "'s money changed by " + formatCurrency(win));
        });

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
