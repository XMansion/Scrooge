package com.deeaae.services;

import com.deeaae.entities.Game;
import com.deeaae.entities.GameConfig;
import com.deeaae.entities.Transaction;

import java.util.UUID;

public interface GameManager {
    //Game getGame(UUID id);
    Game getGame(long id);
    boolean perform(Transaction transaction, long id);
    void revertLastTransaction(long id);
    Game createGame(GameConfig gameConfig);
    Transaction createTransaction(String from, String to, long amount);
    String getMappedName(String name, long id);

}
