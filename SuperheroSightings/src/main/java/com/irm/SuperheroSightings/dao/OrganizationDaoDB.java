/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.dao.SuperheroDaoDB.SuperheroMapper;
import com.irm.SuperheroSightings.entities.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setSuperheros(getSuperherosForOrganization(organization));
            return organization;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, city, country, phone) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getCity(),
                organization.getCountry(),
                organization.getPhone());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, city = ?, "
                + "country = ?, phone = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getCity(),
                organization.getCountry(),
                organization.getPhone(),
                organization.getId());
        
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM organization_superhero WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, organization.getId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM organization_superhero WHERE organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }
    
    public List<Superhero> getSuperherosForOrganization(Organization organization) {
        final String SELECT_SUPERHEROS_FOR_ORGANIZATION = "SELECT s.* FROM superhero s JOIN "
                + "organization_superhero os ON os.superheroId = s.id WHERE os.organizationId = ?";
        List<Superhero> superheros = jdbc.query(SELECT_SUPERHEROS_FOR_ORGANIZATION, new SuperheroMapper(), organization.getId());
        return superheros;
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {
        
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setCity(rs.getString("city"));
            organization.setCountry(rs.getString("country"));
            organization.setPhone(rs.getString("phone"));
            return organization;
        }
    }

}
