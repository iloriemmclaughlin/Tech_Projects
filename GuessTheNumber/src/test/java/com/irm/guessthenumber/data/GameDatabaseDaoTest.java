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
public class GameDatabaseDaoTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public GameDatabaseDaoTest() {
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
            roundDao.deleteRound(round.getRoundId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class GameDatabaseDao.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDatabaseDao.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Game game2 = new Game();
        game2.setGameId(2);
        game2.setAnswer("4321");
        game2.setFinished(false);
        game2 = gameDao.addGame(game2);
        
        List<Game> games = gameDao.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of update method, of class GameDatabaseDao.
     */
    @Test
    public void testUpdate() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
        
        game.setFinished(true);
        
        gameDao.update(game);
        
        assertNotEquals(game, fromDao);
        
        fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
    }

    /**
     * Test of deleteGame method, of class GameDatabaseDao.
     */
    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setRoundId(1);
        round.setGameId(game.getGameId());
        round.setGuessTime(LocalDateTime.now());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = roundDao.addRound(round);
        
        Game fromDao = gameDao.getGameById(game.getGameId());
        
        assertEquals(game, fromDao);
        
        gameDao.deleteGame(game.getGameId());
        gameDao.deleteGame(fromDao.getGameId());
        
        assertEquals(game, fromDao);
      
    }
    
}
