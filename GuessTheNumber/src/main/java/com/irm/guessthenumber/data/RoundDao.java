/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.data;

import com.irm.guessthenumber.models.Round;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 
 *purpose: 
 */
public interface RoundDao {
    
    Round addRound(Round round);
    
    List<Round> getRoundsByGameId(int gameId);
    
    List<Round> getAllRounds();
    
    Round getRoundById(int roundId);
    
    boolean deleteRound(int roundId);

}
