/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/19/2021
 *purpose: Guess The Number Assessment
 */

public class Round {
    
    private int roundId;
    private int gameId;
    private LocalDateTime guessTime;
    private String guess;
    private String result;
    
    public Round() {
        
    }
    
    public Round(int roundId, int gameId, LocalDateTime guessTime, String guess, String result) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guessTime = guessTime;
        this.guess = guess;
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.roundId;
        hash = 83 * hash + this.gameId;
        hash = 83 * hash + Objects.hashCode(this.guessTime);
        hash = 83 * hash + Objects.hashCode(this.guess);
        hash = 83 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.guessTime, other.guessTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Round{" + "roundId=" + roundId + ", gameId=" + gameId + ", guessTime=" + guessTime + ", guess=" + guess + ", result=" + result + '}';
    }
    
    
    
    

}
