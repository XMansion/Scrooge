package com.deeaae.pages;


import com.deeaae.services.GameManager;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Start page of application scrooge.
 */
public class Index
{
    @InjectPage
    private GamePage gamePage;

    @InjectPage
    private Config config;

    @Inject
    GameManager gameManager;

    @Persist
    @Property
    private long gameId;

    @Persist
    @Property
    boolean allGood;

    @Persist
    @Property
    String errorMsg;

    @Log
    Object onSuccessFromStart() {
        if (gameId == 0) {
            return config;
        }
        if (gameManager.getGame(gameId) != null) {
            gamePage.setGameId(gameId);
            return gamePage;
        }
        allGood = false;
        errorMsg = "Game not found.";
        return this;
    }

    public boolean isErrorOnPage(){
        return !allGood;
    }

}
