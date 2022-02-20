/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.data;

import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/18/2021
 *purpose: Guess The Number Assessment
 */

public interface GameDao {
    
    Game addGame(Game game);
    
    List<Game> getAllGames();
    
    Game getGameById(int gameId);
    
    boolean update(Game game);
    
    boolean deleteGame(int gameId);

}
