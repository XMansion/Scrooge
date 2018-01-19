package com.deeaae.pages;

import com.deeaae.entities.Constants;
import com.deeaae.entities.Game;
import com.deeaae.entities.Transaction;
import com.deeaae.services.GameManager;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.PostInjection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GamePage {

    @Inject
    private GameManager gameManager;

    private Game game;

    @Property
    @Persist
    private Long gameId;

    @Persist
    @Property
    private long amount;

    @Persist
    @Property
    private String from;

    @Persist
    @Property
    private String to;

    @Persist
    @Property
    boolean isError;

    @Property
    @Persist
    private List<String> players;

    @BeginRender
    void beginRender(){
        players = new ArrayList<String>();
        players.add(gameManager.getGame(gameId).getGameConfig().player1);
        players.add(gameManager.getGame(gameId).getGameConfig().player2);
        players.add(gameManager.getGame(gameId).getGameConfig().player3);
        players.add(gameManager.getGame(gameId).getGameConfig().player4);
        players.add(Constants.BANKER);
        }



    @PostInjection
    public void setup(){
        isError = false;
    }

    @OnEvent(EventConstants.ACTIVATE)
     public void onActivate(long gameId){
        System.out.println("Game Id : " + gameId);
        this.gameId = gameId;
        getGame();
        }

    @Log
    GamePage onSuccessFromTransact(){
        isError=false;
        if(amount<=0){
            isError=true;
            return this;
        }
        if(from == null){
            isError=true;
            return this;
        }
        if(to == null){
            isError=true;
            return this;
        }
        //long playersBalance = gameManager.getGame(gameId).getBalance().get(gameManager.getMappedName(from,gameId));
        game = gameManager.getGame(gameId);
        Map<String,Long> balance = game.getBalance();
        long playersBalance = balance.get(gameManager.getMappedName(from,gameId));
        if(amount>playersBalance){
            isError=true;
            return this;
        }
        System.out.println("Lets Transact");
        Transaction transaction = gameManager.createTransaction(gameManager.getMappedName(from,gameId),gameManager.getMappedName(to,gameId),amount);
        if(gameManager.perform(transaction,gameId)) {
            return this;
        } else {
            isError = true;
            return this;
        }
    }

    public boolean isPageHasError(){
        return isError;
    }

    private void getGame(){
        game = gameManager.getGame(gameId);
    }

    public void setGameId(long id){
        this.gameId=id;
    }

    public String getPlayer1(){
        if(game==null){
            getGame();
        }
        return game.getGameConfig().getPlayer1();
    }
    public String getPlayer2(){
        if(game==null){
            getGame();
        }
        return game.getGameConfig().getPlayer2();
    }
    public String getPlayer3(){
        if(game==null){
            getGame();
        }
        return game.getGameConfig().getPlayer3();
    }
    public String getPlayer4(){
        if(game==null){
            getGame();
        }
        return game.getGameConfig().getPlayer4();
    }

    public long getBalPlayer1(){
        if(game==null){
            getGame();
        }
        return game.getBalance().get(Constants.PLAYER1);
    }

    public long getBalPlayer2(){
        if(game==null){
            getGame();
        }
        return game.getBalance().get(Constants.PLAYER2);
    }

    public long getBalPlayer3(){
        if(game==null){
            getGame();
        }
        return game.getBalance().get(Constants.PLAYER3);
    }

    public long getBalPlayer4(){
        if(game==null){
            getGame();
        }
        return game.getBalance().get(Constants.PLAYER4);
    }

    public long getBalBanker(){
        if(game==null){
            getGame();
        }
        return game.getBalance().get(Constants.BANKER);
    }

    public String getN0transaction(){
        if(game==null){
            getGame();
        }
        Iterator<Transaction> transactionIterator = game.getTransactions().iterator();
        String response = "";
        while (transactionIterator.hasNext()){
            response = transactionIterator.next().toString();
        }
        return response;

    }

    public String getLast3Transaction(){
        if(game==null){
            getGame();
        }
        Iterator<Transaction> transactionIterator = game.getTransactions().iterator();
        int cntr = 0;
        String response = "";
        while (transactionIterator.hasNext() && cntr < 3){
            response = response + transactionIterator.next().toString() + "<br/>";
            cntr++;
        }
        return response;

    }
}
