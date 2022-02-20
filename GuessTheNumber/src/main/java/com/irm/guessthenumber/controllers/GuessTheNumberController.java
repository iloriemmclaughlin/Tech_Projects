/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.controllers;

import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import com.irm.guessthenumber.service.GuessTheNumberServiceLayer;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.irm.guessthenumber.data.GameDao;
import com.irm.guessthenumber.data.RoundDao;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/18/2021
 *purpose: Guess The Number Assessment
 */

@RestController
@RequestMapping("/api/game")
public class GuessTheNumberController {
    
    private final GameDao gameDao;
    private final RoundDao roundDao;
    private final GuessTheNumberServiceLayer service;
    
    public GuessTheNumberController(GameDao gameDao, RoundDao roundDao, GuessTheNumberServiceLayer service) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
        this.service = service;
    }
    
    @GetMapping
    public List<Game> getAllGames() {
        List<Game> games = service.getAllGames();
        for (Game game : games) {
            if (!game.isFinished()) {
                game.setAnswer("****");
            }
        }
        return games;
    }
    
    @GetMapping("/{gameId}")
    public Game getGameById(@PathVariable int gameId) {
        Game game = service.getGameById(gameId);
        if (!game.isFinished()) {
                game.setAnswer("****");
            }   
        return game;
    }
    
    @GetMapping("/rounds/{gameId}")
    public List<Round> getRoundsByGameId(@PathVariable int gameId) {
        return service.getRoundsByGameId(gameId);
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin() {
        return service.startGame();
    }
    
    @PostMapping("/guess")
    public Round guess(@RequestBody Round round) {
        return service.addRound(round);
    }
    
    @PutMapping("/gameId")
    public ResponseEntity update(@PathVariable int gameId, @RequestBody Game game) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (gameId != game.getGameId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (service.update(game)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
}
