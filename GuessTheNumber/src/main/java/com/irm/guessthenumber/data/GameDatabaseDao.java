/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.data;

import com.irm.guessthenumber.models.Game;
import com.irm.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/18/2021
 *purpose: Guess The Number Assessment
 */

@Repository
@Profile("database")
public class GameDatabaseDao implements GameDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        
        final String sql = "INSERT INTO Game(answer) VALUES (?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            
            PreparedStatement statement = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, game.getAnswer());
            return statement;
            
        }, keyHolder);
        
        game.setGameId(keyHolder.getKey().intValue());
        
        return game;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game;";
        
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        
        final String sql = "SELECT * FROM Game WHERE gameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), gameId);
    }

    @Override
    public boolean update(Game game) {
        
        final String sql = "UPDATE Game SET "
                + "answer = ?, "
                + "finished = ? "
                + "WHERE gameId = ?;";
        
        return jdbcTemplate.update(sql,
                game.getAnswer(),
                game.isFinished(),
                game.getGameId()) > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteGame(int gameId) {
        final String DELETE_GAME_FROM_ROUND = "DELETE FROM Round WHERE gameId = ?";
        jdbcTemplate.update(DELETE_GAME_FROM_ROUND, gameId);
        
        final String sql = "DELETE FROM Game WHERE gameId = ?;";
        return jdbcTemplate.update(sql, gameId) > 0;
    }

    
    private static final class GameMapper implements RowMapper<Game> {
        
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setFinished(rs.getBoolean("finished"));
            return game;
        }
    }

}
