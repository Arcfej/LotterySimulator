package com.company;

public class Player {

    private final String name;

    private LotterySystem.Ticket ticket;

    private int moneySpent;

    private int moneyWon;

    public Player(String name) {
        this.name = name;
        moneySpent = 0;
        moneyWon = 0;
    }

    public String getName() {
        return name;
    }

    public LotterySystem.Ticket getTicket() {
        return ticket;
    }

    public void setTicket(LotterySystem.Ticket ticket) {
        this.ticket = ticket;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public int getMoneyWon() {
        return moneyWon;
    }

    public void buyTicket() {
        moneySpent += LotterySystem.TICKET_PRICE;
    }

    @Override
    public boolean equals(Object obj) {
        Player player = null;
        try {
            player = (Player) obj;
            return this.name.equals(player.getName());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
