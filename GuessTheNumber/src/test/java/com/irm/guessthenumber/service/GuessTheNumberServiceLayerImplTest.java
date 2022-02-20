/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.guessthenumber.service;

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
public class GuessTheNumberServiceLayerImplTest {
    
    @Autowired
    GuessTheNumberServiceLayer service;
    
    public GuessTheNumberServiceLayerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Game> games = service.getAllGames();
        for (Game game : games) {
            service.deleteGame(game.getGameId());
        }
        
        List<Round> rounds = service.getAllRounds();
        for (Round round : rounds) {
            service.deleteRound(round.getGameId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addGame method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Game fromService = service.getGameById(game.getGameId());
        
        assertEquals(game, fromService);
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Game game2 = new Game();
        game2.setGameId(2);
        game2.setAnswer("4321");
        game2.setFinished(false);
        game2 = service.addGame(game2);
        
        List<Game> games = service.getAllGames();
        
        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of update method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testUpdate() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Game fromService = service.getGameById(game.getGameId());
        
        assertEquals(game, fromService);
        
        game.setFinished(true);
        
        service.update(game);
        
        assertNotEquals(game, fromService);
        
        fromService = service.getGameById(game.getGameId());
        
        assertEquals(game, fromService);
    }

    /**
     * Test of deleteGame method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Game fromService = service.getGameById(game.getGameId());
        
        assertEquals(game, fromService);
        
        service.deleteGame(game.getGameId());
        service.deleteGame(fromService.getGameId());
        
        assertEquals(game, fromService);
    }

    /**
     * Test of addRound method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testAddGetRound() {
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("1569");
        game.setFinished(false);
        service.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuessTime(LocalDateTime.now());
        round.setGuess("1234");
        
        service.addRound(round);
        
        List<Round> rounds = service.getAllRounds();
        
        assertEquals(1, rounds.size());
    }

    /**
     * Test of getRoundsByGameId method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testGetRoundsByGameId() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = service.addRound(round);
        
        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("3456");
        round2.setResult("e:0:p:2");
        round2 = service.addRound(round2);
        
        List<Round> rounds = service.getRoundsByGameId(round.getGameId());
        
        assertEquals(2, rounds.size());
    }
    
    @Test
    public void testGetAllRounds() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("1289");
        service.addRound(round);
        
        Round round2 = new Round();
        round2.setGameId(game.getGameId());
        round2.setGuess("1548");
        service.addRound(round2);
        
        List<Round> rounds = service.getAllRounds();
        
        assertEquals(2, rounds.size());
    }

    /**
     * Test of deleteRound method, of class GuessTheNumberServiceLayerImpl.
     */
    @Test
    public void testDeleteRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = service.addGame(game);
        
        Round round = new Round();
        round.setGameId(game.getGameId());
        round.setGuess("6789");
        round.setResult("e:0:p:0");
        round = service.addRound(round);
        
        service.deleteRound(round.getRoundId());
        
        List<Round> rounds = service.getAllRounds();
        
        assertEquals(0, rounds.size());
    }
    
}
