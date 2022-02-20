/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.guessthenumber.data;

import com.irm.guessthenumber.TestApplicationConfiguration;
import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ilori
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDatabaseDaoTest {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    public RoundDatabaseDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            gameDao.deleteGame(game.getGameId());
        }
        
        List<Round> rounds = roundDao.getAllRounds();
        for (Round round : rounds) {
            roundDao.deleteRound(round.getGameId());
        }  
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addRound method, of class RoundDatabaseDao.
     */
    @Test
    public void testAddGetRound() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setRoundId(1);
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = roundDao.addRound(round);
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(1, rounds.size());
        
    }

    /**
     * Test of getRoundsByGameId method, of class RoundDatabaseDao.
     */
    @Test
    public void testGetRoundsByGameId() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = roundDao.addRound(round);
        
        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("3456");
        round2.setResult("e:0:p:2");
        round2 = roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getRoundsByGameId(round.getGameId());
        
        assertEquals(2, rounds.size());
    }

    /**
     * Test of getAllRounds method, of class RoundDatabaseDao.
     */
    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = roundDao.addRound(round);
        
        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("3456");
        round2.setResult("e:0:p:2");
        round2 = roundDao.addRound(round2);
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(2, rounds.size());
    }

    /**
     * Test of deleteRound method, of class RoundDatabaseDao.
     */
    @Test
    public void testDeleteRound() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setRoundId(1);
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = roundDao.addRound(round);
        
        roundDao.deleteRound(round.getRoundId());
        
        List<Round> rounds = roundDao.getAllRounds();
        
        assertEquals(0, rounds.size());
        
    }
    
}
