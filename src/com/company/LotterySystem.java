package com.company;

import java.util.*;

/**
 * A lottery system. Each ticket have 6 numbers, 6 winner numbers are drawn weekly.
 * The bettable numbers are between 1 and a provided number bigger than 6.
 */
public class LotterySystem {

    /**
     * Key to identify the numbers drawn by the lottery system
     */
    public static final String WINNER_SET_KEY = "lsléabg48q4qdsjaééebbféé478";

    /**
     * The count of numbers a ticket has and which is drawn every week.
     */
    public static final int DRAW_COUNT = 6;

    /**
     * The price of a ticket
     */
    public static final int TICKET_PRICE = 2;

    /**
     * The amount of money given to a match 3
     */
    public static final int MATCH_3_WIN = 25;

    /**
     * The amount of money given to a match 4
     */
    public static final int MATCH_4_WIN = 100;

    /**
     * The amount of money given to a match 5
     */
    public static final int MATCH_5_WIN = 1000;

    /**
     * The amount of money given to a match 6
     */
    public static final int MATCH_6_WIN = 1000000;

    /**
     * The amount of money given to match 1 - 6 in an array
     */
    public static final int[] WINS = new int[]{0, 0, MATCH_3_WIN, MATCH_4_WIN, MATCH_5_WIN, MATCH_6_WIN};

    /**
     * The biggest bettable number in the Lottery System
     */
    private static int lotteryMax;

    /**
     * The list of bought tickets
     */
    private List<Ticket> tickets;

    /**
     * Base cunstructor of the class
     *
     * @param lotteryMax the bigest bettable number in the lottery system. Must be bigger than 6.
     * @throws IllegalArgumentException if the provided lotteryMax smaller or equal to 6.
     */
    public LotterySystem(int lotteryMax) throws IllegalArgumentException {
        if (lotteryMax <= 6) {
            throw new IllegalArgumentException("Too little number for lottery max");
        }
        tickets = new ArrayList<>();
        LotterySystem.lotteryMax = lotteryMax;
    }

    /**
     * @return the biggest bettable number in the lottery system
     */
    public static int getLotteryMax() {
        return lotteryMax;
    }

    /**
     * @return the list of bought tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Buy a ticket in the system.
     *
     * @param newTicket the ticket a player bought.
     */
    public void buyTicket(Ticket newTicket) {
        tickets.add(newTicket);
    }

    /**
     * Draw a weekly winner numbers and...
     *
     * @return  these numbers and the winners who have any match in a map with the player's name as the key.
     *          The winner numbers stored with the WINNER_SET_KEY.
     */
    public Map<String, Set<Integer>> drawWinners() {
        Set<Integer> draw = generateNumbersForTicket();
        Map<String, Set<Integer>> winners = new HashMap<>();
        winners.put(WINNER_SET_KEY, draw);
        for (Ticket ticket : tickets) {
            Set<Integer> win = ticket.getNumbers().intersection(draw);
            if (!win.isEmpty()) {
                winners.put(ticket.getId(), win);
            }
        }
        return winners;
    }

    /**
     * Generate 6 random numbers for a ticket or draw within the Lottery System set boundary without duplicate numbers.
     *
     * @return the generated 6 numbers in a set.
     */
    private static MySet<Integer> generateNumbersForTicket() {
        Random rnd = new Random();
        MySet<Integer> numbers = new MySet<>(DRAW_COUNT);
        while (numbers.size() < DRAW_COUNT) {
            numbers.add(rnd.nextInt(lotteryMax) + 1);
        }
        return numbers;
    }

    /**
     * The ticket which is used in the LotterySystem.
     */
    public static class Ticket {

        /**
         * The id of a ticket, currently it's the player's name.
         */
        private final String id;

        /**
         * The six bet number with no duplicates.
         */
        private final MySet<Integer> numbers;

        /**
         * Base constructor of the class.
         *
         * @param id The id of ticket, currently it's the player's name
         * @param numbers The numbers the player bet on.
         * @throws WrongCountOfNumbersException if the provided numbers are not equals lotteryMax
         * @throws NotValidNumberException if the provided numbers are not in the LotterySystem's valid range.
         */
        public Ticket(String id, MySet<Integer> numbers) throws WrongCountOfNumbersException, NotValidNumberException {
            if (numbers.size() != 6) {
                throw new WrongCountOfNumbersException(LotterySystem.lotteryMax + " numbers should be provided to the Ticket");
            }
            for (Integer num : numbers) {
                if (num > lotteryMax || num < 1) {
                    throw new NotValidNumberException("The numbers should be between 1 and " + LotterySystem.lotteryMax);
                }
            }
            this.id = id;
            this.numbers = numbers;
        }

        /**
         * Generate a ticket with random numbers.
         *
         * @param id The id of ticket, currently it's the player's name
         * @throws WrongCountOfNumbersException if the provided numbers are not equals lotteryMax
         * @throws NotValidNumberException if the provided numbers are not in the LotterySystem's valid range.
         */
        public static Ticket generateRandomTicket(String id) throws WrongCountOfNumbersException, NotValidNumberException {
            return new Ticket(id, generateNumbersForTicket());
        }

        /**
         * @return The id of the ticket
         */
        public String getId() {
            return id;
        }

        /**
         * @return the numbers the ticket bets on.
         */
        public MySet<Integer> getNumbers() {
            return numbers;
        }
    }
}
