package com.company;

/**
 * A player of the Lottery System
 */
public class Player {

    /**
     * The name of the player
     */
    private final String name;

    /**
     * The ticket the player bet with
     */
    private LotterySystem.Ticket ticket;

    /**
     * The amount of money the player spent
     */
    private int moneySpent;

    /**
     * The amount of money the player has won
     */
    private int moneyWon;

    /**
     * Base constructor of the class
     *
     * @param name The name of the player
     */
    public Player(String name) {
        this.name = name;
        moneySpent = 0;
        moneyWon = 0;
    }

    /**
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return the ticket the player is betting with
     */
    public LotterySystem.Ticket getTicket() {
        return ticket;
    }

    /**
     * Set the ticket the player is betting with
     *
     * @param ticket the ticket the player would like to bet with
     */
    public void setTicket(LotterySystem.Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the amount of money the player has spent
     */
    public int getMoneySpent() {
        return moneySpent;
    }

    /**
     * @return the amount of money the player has won
     */
    public int getMoneyWon() {
        return moneyWon;
    }

    /**
     * Register a ticket purchase. Increase the spent money.
     */
    public void buyTicket() {
        moneySpent += LotterySystem.TICKET_PRICE;
    }

    /**
     *  Register a win.
     *
     * @param moneyWon the amount of money the player has won and to register
     */
    public void addWin(int moneyWon) {
        this.moneyWon += moneyWon;
    }

    @Override
    public boolean equals(Object obj) {
        Player player;
        try {
            player = (Player) obj;
            return this.name.equals(player.getName());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
