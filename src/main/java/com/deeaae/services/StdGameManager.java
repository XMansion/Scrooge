package com.deeaae.services;

import com.deeaae.entities.Constants;
import com.deeaae.entities.Game;
import com.deeaae.entities.GameConfig;
import com.deeaae.entities.Transaction;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.PostInjection;
import org.apache.tapestry5.ioc.annotations.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Scope(ScopeConstants.DEFAULT)
public class StdGameManager implements GameManager {
    private Long gameCounter;
    private Map<Long,Game> gameMap;

    @Override
    public synchronized Game createGame(GameConfig gameConfig) {
        try {
            Game newGame = new Game(gameConfig.getBankInitialAmount());
            gameCounter++;
            newGame.setGameId(gameCounter);
            newGame.setGameConfig(gameConfig);
            gameMap.put(gameCounter,newGame);
            //List<Transaction> initialTransactions = new ArrayList<Transaction>(4);
            perform(createTransaction(Constants.BANKER,
                            Constants.PLAYER1,
                            gameConfig.playerInitialAmount),gameCounter);

            perform(createTransaction(Constants.BANKER,
                            Constants.PLAYER2,
                            gameConfig.playerInitialAmount),gameCounter);

            perform(createTransaction(Constants.BANKER,
                            Constants.PLAYER3,
                            gameConfig.playerInitialAmount),gameCounter);

            perform(createTransaction(Constants.BANKER,
                            Constants.PLAYER4,
                            gameConfig.playerInitialAmount),gameCounter);
            return newGame;
            }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @PostInjection
     public void initialize() {
        gameMap = new HashMap<Long, Game>();
        gameCounter = 0L;
        GameConfig gameConfig = new GameConfig();
        gameConfig.setPlayer1("Anunay");
        gameConfig.setPlayer2("Tanmay");
        gameConfig.setPlayer3("Noopur");
        gameConfig.setBankInitialAmount(1000);
        gameConfig.setPlayerInitialAmount(100);
        createGame(gameConfig);
    }

    @Override
    public Game getGame(long id) {
        return gameMap.get(id);
    }

    @Override
    public boolean perform(Transaction transaction, long id) {
        Game game = gameMap.get(id);
        if(null == game) {
            throw new RuntimeException("Game not found");
        }
        synchronized (game){
            Map<String,Long> balance = game.getBalance();
            long transactionId = game.getLastTransactionId() + 1;
            transaction.setTransactionId(transactionId);

            long startingAmountOfA = balance.get(transaction.getFrom());
            long closingBlanceOfA = startingAmountOfA - transaction.getAmount();
            long startingAmountOfB = balance.get(transaction.getTo());
            long closingBlanceOfB = startingAmountOfB + transaction.getAmount();
            if(closingBlanceOfA<0){
                return false;
            }
            game.setLastTransactionId(transactionId);
            game.getTransactions().add(transaction);
            balance.put(transaction.getFrom(),closingBlanceOfA);
            balance.put(transaction.getTo(),closingBlanceOfB);
            return true;
            }

    }

    @Override
    public void revertLastTransaction(long id) {

    }

    public String getMappedName(String name, long id){
        Game game = gameMap.get(id);
        if(game.getGameConfig().getPlayer1().equalsIgnoreCase(name)){
            return Constants.PLAYER1;
        }
        if(game.getGameConfig().getPlayer2().equalsIgnoreCase(name)){
            return Constants.PLAYER2;
        }
        if(game.getGameConfig().getPlayer3().equalsIgnoreCase(name)){
            return Constants.PLAYER3;
        }
        if(game.getGameConfig().getPlayer4().equalsIgnoreCase(name)){
            return Constants.PLAYER4;
        }
        return name;

    }

    public Transaction createTransaction(String from, String to, long amount){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTo(to);
        transaction.setFrom(from);
        return transaction;
        }
}
