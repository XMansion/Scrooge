package com.deeaae.pages;

import com.deeaae.entities.Game;
import com.deeaae.entities.GameConfig;
import com.deeaae.services.GameManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;


public class Config {
    @Property
    private GameConfig gameConfig;

    @InjectPage
    private GamePage gamePage;

    @Inject
    private GameManager gameManager;

    GamePage onSuccess() {
        System.out.println(gameConfig);
        Game game = gameManager.createGame(gameConfig);
        gamePage.setGameId(game.getGameId());
        return gamePage;
        }
}
