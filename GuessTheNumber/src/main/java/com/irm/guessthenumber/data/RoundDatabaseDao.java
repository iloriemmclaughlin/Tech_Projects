/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.guessthenumber.data;

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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 11/30/2021
 *purpose: Guess the Number Assessment
 */

@Repository
@Profile("database")
public class RoundDatabaseDao implements RoundDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public RoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public Round addRound(Round round) {
        
        final String sql = "INSERT INTO Round(gameId, guessTime, guess, result) VALUES (?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            
            statement.setInt(1, round.getGameId());
            LocalDateTime ldt = LocalDateTime.now();
            round.setGuessTime(ldt);
            String time = ldt.toString();
            statement.setString(2, time);
            statement.setString(3, round.getGuess());
            statement.setString(4, round.getResult());
            return statement;
        }, keyHolder);
        
        round.setRoundId(keyHolder.getKey().intValue());
        
        return round;
    }

    @Override
    public List<Round> getRoundsByGameId(int gameId) {
        final String sql = "SELECT * FROM Round WHERE gameId = ? ORDER BY guessTime;";
        List<Round> rounds = jdbcTemplate.query(sql, new RoundDatabaseDao.RoundMapper(), gameId);
        return rounds;
    }
    
    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT * FROM Round;";
        return jdbcTemplate.query(sql, new RoundMapper());
    }
    
    @Override
    public Round getRoundById(int roundId) { 
        final String sql = "SELECT * FROM Round WHERE roundId = ?;";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), roundId);
    }
    
    @Override
    @Transactional
    public boolean deleteRound(int roundId) { 
        final String sql = "DELETE FROM Round WHERE roundId = ?;";
        return jdbcTemplate.update(sql, roundId) > 0;
    }
    
    
    private static final class RoundMapper implements RowMapper<Round> {
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));
            Timestamp timestamp = rs.getTimestamp("guessTime");
            round.setGuessTime(timestamp.toLocalDateTime());
            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            return round;
        }
    }
    
}
