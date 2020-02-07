package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class LotterySystem {

    public static final int LOTTERY_MAX = 10;

    public static final int DRAW_COUNT = 6;

    public static final int TICKET_PRICE = 2;

    public static final int MATCH_3_WIN = 25;

    public static final int MATCH_4_WIN = 100;

    public static final int MATCH_5_WIN = 1000;

    public static final int MATCH_6_WIN = 1000000;

    private List<Ticket> tickets;

    public LotterySystem(Scanner in) {
        tickets = new ArrayList<>();
    }

    public void buyTicket(Ticket newTicket) {
        tickets.add(newTicket);
    }

    public List<Ticket> drawWinners() {
        return tickets.stream()
                .filter(ticket -> ticket.getNumbers().equals(generateNumbersForTicket()))
                .collect(Collectors.toList());
    }

    private static Set<Integer> generateNumbersForTicket() {
        Random rnd = new Random();
        Set<Integer> numbers = new HashSet<>(DRAW_COUNT);
        while (numbers.size() < DRAW_COUNT) {
            numbers.add(rnd.nextInt(LOTTERY_MAX + 1));
        }
        return numbers;
    }

    public static class Ticket {

        private final String id;

        private final Set<Integer> numbers;

        public Ticket(String id, Set<Integer> numbers) {
            this.id = id;
            this.numbers = numbers;
        }

        public static Ticket generateRandomTicket(String id) {
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
