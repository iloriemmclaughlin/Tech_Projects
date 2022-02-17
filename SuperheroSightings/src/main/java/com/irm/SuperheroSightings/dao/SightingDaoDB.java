/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.dao.LocationDaoDB.LocationMapper;
import com.irm.SuperheroSightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.irm.SuperheroSightings.dao.PowerDaoDB.PowerMapper;
import com.irm.SuperheroSightings.dao.SuperheroDaoDB.SuperheroMapper;
import com.irm.SuperheroSightings.entities.Location;
import com.irm.SuperheroSightings.entities.Organization;
import com.irm.SuperheroSightings.entities.Power;
import com.irm.SuperheroSightings.entities.Sighting;
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
 *date: 1/23/2022
 *purpose: Superhero Sightings Project
 */

@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Sighting getSightingById(int id) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT * FROM sightings WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(getSuperheroForSighting(id));
            Superhero newHero = sighting.getSuperhero();
            newHero.setPowers(getPowersForSuperhero(newHero.getId()));
            newHero.setOrganizations(getOrganizationsForSuperhero(newHero.getId()));
            sighting.setSuperheros(getSuperherosForSightingList(id));
            List<Superhero> superheros = sighting.getSuperheros();
            for(Superhero superhero : superheros) {
                superhero.setPowers(getPowersForSuperhero(superhero.getId()));
                superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
            }
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private List<Superhero> getSuperherosForSightingList(int id) {
        final String SELECT_SUPERHEROS_FOR_SIGHTING = "SELECT s.* FROM superhero s "
                + "JOIN sightings ss ON ss.superheroId = s.id WHERE ss.id = ?";
        return jdbc.query(SELECT_SUPERHEROS_FOR_SIGHTING, new SuperheroMapper(), id);
    }
    
    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHEROS_FOR_SIGHTING = "SELECT s.* FROM superhero s "
                + "JOIN sightings ss ON ss.superheroId = s.id WHERE ss.id = ?";
        return jdbc.queryForObject(SELECT_SUPERHEROS_FOR_SIGHTING, new SuperheroMapper(), id);
    }
    
     private List<Power> getPowersForSuperhero(int id) {
        final String SELECT_POWERS_FOR_SUPERHERO = "SELECT p.* FROM power p "
        + "JOIN superhero_power sp ON sp.powerId = p.id WHERE sp.superheroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_SUPERHERO, new PowerMapper(), id);
    }
     
     private List<Organization> getOrganizationsForSuperhero(int id) {
         final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                 + "JOIN organization_superhero os ON os.organizationId = o.id WHERE os.superheroId = ?";
         return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationMapper(), id);
     }
    
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sightings s ON s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }
    
    @Override
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT * FROM sightings";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        associateSuperherosAndLocations(sightings);
        return sightings;
    }
    
    private void associateSuperherosAndLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperheroForSighting(sighting.getId()));
            sighting.setSuperheros(getSuperherosForSightingList(sighting.getId()));
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            Superhero newHero = sighting.getSuperhero();
            newHero.setPowers(getPowersForSuperhero(newHero.getId()));
            newHero.setOrganizations(getOrganizationsForSuperhero(newHero.getId()));
            List<Superhero> superheros = sighting.getSuperheros();
            for(Superhero superhero : superheros) {
                superhero.setPowers(getPowersForSuperhero(superhero.getId()));
                superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
            }
        }
    }
    
    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sightings(date, superheroId, locationId) VALUES(?,?,?)";
        int locationId = sighting.getLocation().getId();
        for(Superhero superhero : sighting.getSuperheros()) {
            jdbc.update(INSERT_SIGHTING,
                    sighting.getDate(),
                    superhero.getId(),
                    locationId);
        }
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sightings SET date = ?, superheroId = ?, locationId = ? WHERE id = ?";
        for(Superhero superhero : sighting.getSuperheros()) {
            jdbc.update(UPDATE_SIGHTING, 
                    sighting.getDate(),
                    superhero.getId(),
                    sighting.getLocation().getId(),
                    sighting.getId());
            }
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) { 
        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForSuperhero(int superheroId) {
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM sightings WHERE superheroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO, new SightingMapper(), superheroId);
        associateSuperherosAndLocations(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByLocation(int locationId) {
        final String SELECT_SIGHTINGS_BY_LOCATION = "SELECT * FROM sightings WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_LOCATION, new SightingMapper(), locationId);
        associateSuperherosAndLocations(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByDate(String date) {
        final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM sightings WHERE date = ?";
        List<Sighting> sightings =jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date);
        associateSuperherosAndLocations(sightings);
        return sightings;
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {
    
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getString("date"));
            
            return sighting;
        }
    }

}
