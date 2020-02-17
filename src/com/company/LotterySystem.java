package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class LotterySystem {

    public static final String WINNER_SET_KEY = "lsléabg48q4qdsjaééebbféé478géqhgkjgaglsa21éáahgnbrpáeu72b";

    public static final int DRAW_COUNT = 6;

    public static final int TICKET_PRICE = 2;

    public static final int MATCH_3_WIN = 25;

    public static final int MATCH_4_WIN = 100;

    public static final int MATCH_5_WIN = 1000;

    public static final int MATCH_6_WIN = 1000000;

    public static final int[] WINS = new int[]{0, 0, MATCH_3_WIN, MATCH_4_WIN, MATCH_5_WIN, MATCH_6_WIN};

    private static int lotteryMax = 10;

    private List<Ticket> tickets;

    public LotterySystem(int lotteryMax) {
        tickets = new ArrayList<>();
        LotterySystem.lotteryMax = lotteryMax;
    }

    public static int getLotteryMax() {
        return lotteryMax;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void buyTicket(Ticket newTicket) {
        tickets.add(newTicket);
    }

    public Map<String, Set<Integer>> drawWinners() {
        Set<Integer> draw = generateNumbersForTicket();
        Map<String, Set<Integer>> winners = new HashMap<>();
        winners.put(WINNER_SET_KEY, draw);
        for (Ticket ticket : tickets) {
            Set<Integer> win = ticket.getNumbers().stream()
                    .filter(draw::contains)
                    .collect(Collectors.toSet());
            if (!win.isEmpty()) {
                winners.put(ticket.getId(), win);
            }
        }
        return winners;
    }

    private static Set<Integer> generateNumbersForTicket() {
        Random rnd = new Random();
        Set<Integer> numbers = new HashSet<>(DRAW_COUNT);
        while (numbers.size() < DRAW_COUNT) {
            numbers.add(rnd.nextInt(lotteryMax) + 1);
        }
        return numbers;
    }

    public static class Ticket {

        private final String id;

        private final Set<Integer> numbers;

        public Ticket(String id, Set<Integer> numbers) throws WrongCountOfNumbersException, NotValidNumberException {
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

        public static Ticket generateRandomTicket(String id) throws WrongCountOfNumbersException, NotValidNumberException {
            return new Ticket(id, generateNumbersForTicket());
        }

        public String getId() {
            return id;
        }

        public Set<Integer> getNumbers() {
            return numbers;
        }
    }
}
