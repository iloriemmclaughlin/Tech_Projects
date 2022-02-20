/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.service;

import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.irm.guessthenumber.data.GameDao;
import com.irm.guessthenumber.data.RoundDao;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/19/2021
 *purpose: Guess The Number Assessment
 */

@Service
public class GuessTheNumberServiceLayerImpl implements GuessTheNumberServiceLayer {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    @Override
    public Game addGame(Game game) {
        return gameDao.addGame(game);
    }

    @Override
    public List<Game> getAllGames() {
        return gameDao.getAllGames();
    }

    @Override
    public Game getGameById(int gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public boolean update(Game game) {
        return gameDao.update(game);
    }
    
    @Override
    public boolean deleteGame(int gameId) {
        return gameDao.deleteGame(gameId);
    }

    @Override
    public Round addRound(Round round) {
        Game game = getGameById(round.getGameId());
        String guess = round.getGuess();
        String answer = game.getAnswer();
        String result = findResult(guess, answer);
        round.setResult(result);
        if (guess.equals(answer)) {
            game.setFinished(true);
            gameDao.update(game);
        }
        return roundDao.addRound(round);
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        return roundDao.getRoundsByGameId(gameId);
    }
    
    @Override
    public List<Round> getAllRounds() {
        return roundDao.getAllRounds();
    }
    
    @Override
    public Round getRoundById(int roundId) {
        return roundDao.getRoundById(roundId);
    }
    
    @Override
    public boolean deleteRound(int roundId) {
        return roundDao.deleteRound(roundId);
    }
    
    @Override
    public int startGame() {
        Game game = new Game();
        game.setAnswer(createAnswer());
        game = gameDao.addGame(game);
        return game.getGameId();
    }

    private String createAnswer() {
        Random r = new Random();
        int num1 = r.nextInt(10);
        
        int num2 = r.nextInt(10);
        
        int num3 = r.nextInt(10);
        
        int num4 = r.nextInt(10);
        
        String answer = String.valueOf(num1) + String.valueOf(num2) + String.valueOf(num3) + String.valueOf(num4);
        return answer;
    }
    
    
    private String findResult(String guess, String answer) {
        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();
        int exact = 0;
        int partial = 0;
        
        for (int i = 0; i < guessArray.length; i++) {
            if(guessArray[i] == answerArray[i]){
                    exact++;
                    continue;
            }
            for (int j = 0; j < guessArray.length; j++) {
                if(answerArray[i] == guessArray[j]) {
                    partial++;
                }
            }
        }
        
        
        String result = "e:" + exact + ":p:" + partial;
        return result;
    
}
    
}
