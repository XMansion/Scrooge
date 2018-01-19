package com.deeaae.entities;

import org.apache.tapestry5.beaneditor.Validate;

public class GameConfig {
    public String player1 = "Player 1";
    public String player2 = "Player 2";
    public String player3 = "Player 3";
    public String player4 = "Player 4";
    @Validate("min=1,max=99999")
    public long bankInitialAmount = 5000;
    @Validate("min=1,max=99999")
    public long playerInitialAmount = 100;

    public String bankCred;

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public void setPlayer4(String player4) {
        this.player4 = player4;
    }

    public long getBankInitialAmount() {
        return bankInitialAmount;
    }

    public void setBankInitialAmount(long bankInitialAmount) {
        this.bankInitialAmount = bankInitialAmount;
    }

    public long getPlayerInitialAmount() {
        return playerInitialAmount;
    }

    public void setPlayerInitialAmount(long playerInitialAmount) {
        this.playerInitialAmount = playerInitialAmount;
    }

    public String getBankCred() {
        return bankCred;
    }

    public void setBankCred(String bankCred) {
        this.bankCred = bankCred;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", player3='" + player3 + '\'' +
                ", player4='" + player4 + '\'' +
                ", bankInitialAmount=" + bankInitialAmount +
                ", playerInitialAmount=" + playerInitialAmount +
                ", bankCred='" + bankCred + '\'' +
                '}';
    }
}
