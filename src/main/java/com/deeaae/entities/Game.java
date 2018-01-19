package com.deeaae.entities;

import java.util.*;

public class Game {
    private long gameId;
    private GameConfig gameConfig;
    private Map<String,Long> balance;
    private Queue<Transaction> transactions;
    private long lastTransactionId;
    private int queueLimit;

    public Game(long bankInitialAmount){
        setup();
        balance.put(Constants.BANKER,bankInitialAmount);
    }

    public Game() {
        setup();
    }

    public long getGameId() {
        return gameId;
    }

    private void setup(){
        balance = new HashMap<String, Long>();
        transactions = new LinkedList<Transaction>();
        lastTransactionId = 0;
        queueLimit = 9999;
        balance.put(Constants.PLAYER1,0L);
        balance.put(Constants.PLAYER2,0L);
        balance.put(Constants.PLAYER3,0L);
        balance.put(Constants.PLAYER4,0L);
        balance.put(Constants.BANKER,0L);
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public Map<String, Long> getBalance() {
        return balance;
    }

    public void setBalance(Map<String, Long> balance) {
        this.balance = balance;
    }

    public Queue<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Queue<Transaction> transactions) {
        this.transactions = transactions;
    }
}
