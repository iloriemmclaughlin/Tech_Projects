/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.entities.Location;
import com.irm.SuperheroSightings.entities.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 1/19/2022
 *purpose: Superhero Sightings Project
 */

@Repository
public class LocationDaoDB implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
            return location;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, city, country, latitude, longitude) "
                + "VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getCity(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, city = ?, "
                + "country = ?, latitude = ?, longitude = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getCity(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTINGS = "DELETE FROM sightings WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsForSuperhero(Superhero superhero) {
        final String SELECT_LOCATIONS_FOR_SUPERHERO = "SELECT l.* FROM location l JOIN "
                + "sightings sl ON sl.locationId = l.id WHERE sl.superheroId = ?";
        List<Location> locations = jdbc.query(SELECT_LOCATIONS_FOR_SUPERHERO, new LocationMapper(), superhero.getId());
        return locations;
    }
    
    public static final class LocationMapper implements RowMapper<Location> {
        
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
           Location location = new Location();
           location.setId(rs.getInt("id"));
           location.setName(rs.getString("name"));
           location.setDescription(rs.getString("description"));
           location.setCity(rs.getString("city"));
           location.setCountry(rs.getString("country"));
           location.setLatitude(rs.getBigDecimal("latitude"));
           location.setLongitude(rs.getBigDecimal("longitude"));
           return location;
        }
    }

}
