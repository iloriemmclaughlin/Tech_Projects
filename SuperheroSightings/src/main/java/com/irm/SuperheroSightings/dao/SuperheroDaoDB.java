/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.irm.SuperheroSightings.dao.PowerDaoDB.PowerMapper;
import com.irm.SuperheroSightings.entities.Organization;
import com.irm.SuperheroSightings.entities.Power;
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
 *date: 1/18/2022
 *purpose: Superhero Sightings Project
 */

@Repository
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String GET_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            Superhero superhero = jdbc.queryForObject(GET_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setPowers(getPowersForSuperhero(id));
            superhero.setOrganizations(getOrganizationsForSuperhero(id));
            return superhero;
        } catch(DataAccessException ex) {
            return null;
        }
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

    @Override
    public List<Superhero> getAllSuperheros() {
        final String GET_ALL_SUPERHEROS = "SELECT * FROM superhero";
        List<Superhero> superheros = jdbc.query(GET_ALL_SUPERHEROS, new SuperheroMapper());
        associatePowers(superheros);
        associateOrganizations(superheros);
        return superheros;
    }
    
    private void associatePowers(List<Superhero> superheros) {
        for(Superhero superhero : superheros) {
            superhero.setPowers(getPowersForSuperhero(superhero.getId()));
        }
    }
    
    private void associateOrganizations(List<Superhero> superheros) {
        for(Superhero superhero : superheros) {
            superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description) VALUES(?,?)";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);  
        superhero.setId(newId);
        insertSuperheroPower(superhero);
        insertSuperheroOrganization(superhero);
        return superhero;
    }
    
    private void insertSuperheroPower(Superhero superhero) {
        final String INSERT_SUPERHERO_POWER = "INSERT INTO superhero_power(superheroId, powerId) VALUES(?,?)";
        for(Power power : superhero.getPowers()) {
            jdbc.update(INSERT_SUPERHERO_POWER,
                    superhero.getId(),
                    power.getId());
        }
    }
    
    private void insertSuperheroOrganization(Superhero superhero) {
        final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO organization_superhero(organizationId, superheroId) VALUES(?,?)";
        for(Organization organization : superhero.getOrganizations()) {
            jdbc.update(INSERT_SUPERHERO_ORGANIZATION,
                    organization.getId(),
                    superhero.getId());
        }
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ? WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getId());
        
        final String DELETE_SUPERHERO_POWER = "DELETE FROM superhero_power WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_POWER, superhero.getId());
        insertSuperheroPower(superhero);
        
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM organization_superhero WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, superhero.getId());
        insertSuperheroOrganization(superhero);
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM organization_superhero WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        
        final String DELETE_SIGHTINGS = "DELETE FROM sightings WHERE superheroId = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
        
        final String DELETE_SUPERHERO_POWER = "DELETE FROM superhero_power WHERE superheroId = ?";
        jdbc.update(DELETE_SUPERHERO_POWER, id);
        
        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE id = ?";
        jdbc.update(DELETE_SUPERHERO, id);
    }
    
    @Override
    public List<Superhero> getSuperherosForPower(Power power) {
        final String SELECT_SUPERHEROS_FOR_POWER = "SELECT s.* FROM superhero s JOIN "
                + "superhero_power sp ON sp.superheroId = s.id WHERE sp.powerId = ?";
        List<Superhero> superheros = jdbc.query(SELECT_SUPERHEROS_FOR_POWER, new SuperheroMapper(), power.getId());
        associatePowers(superheros);
        return superheros;
    }
    
    @Override
    public List<Superhero> getSuperherosForOrganization(Organization organization) {
        final String SELECT_SUPERHEROS_FOR_ORGANIZATION = "SELECT s.* FROM superhero s JOIN "
                + "organization_superhero os ON os.superheroId = s.id WHERE os.organizationId = ?";
        List<Superhero> superheros = jdbc.query(SELECT_SUPERHEROS_FOR_ORGANIZATION, new SuperheroMapper(), organization.getId());
        associateOrganizations(superheros);
        return superheros;
    }
    
    public static final class SuperheroMapper implements RowMapper<Superhero> {
        
        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            return superhero;
        }
        
    }

}
