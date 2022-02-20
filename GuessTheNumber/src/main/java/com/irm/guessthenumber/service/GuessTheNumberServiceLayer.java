/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.service;

import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/19/2021
 *purpose: Guess The Number Assessment
 */
public interface GuessTheNumberServiceLayer {
    
    Game addGame(Game game);
    
    List<Game> getAllGames();
    
    Game getGameById(int gameId);
    
    boolean update(Game game);
    
    boolean deleteGame(int gameId);
    
    Round addRound(Round round);
    
    List<Round> getRoundsByGameId(int gameId);
    
    List<Round> getAllRounds();
    
    Round getRoundById(int roundId);
    
    boolean deleteRound(int roundId);
    
    int startGame();

}
