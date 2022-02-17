/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.entities.Location;
import com.irm.SuperheroSightings.entities.Organization;
import com.irm.SuperheroSightings.entities.Power;
import com.irm.SuperheroSightings.entities.Sighting;
import com.irm.SuperheroSightings.entities.Superhero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author ilori
 */

@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
       
    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
        List<Power> powers = powerDao.getAllPowers();
        for(Power power : powers) {
            powerDao.deletePowerById(power.getId());
        }
        
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        for(Superhero superhero : superheros) {
            superheroDao.deleteSuperheroById(superhero.getId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddGetSightingById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setCity("Test City");
        organization.setCountry("Test Country");
        organization.setPhone("Test Phone");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate("2022-01-01");
        sighting.setSuperhero(superhero);
        sighting.setSuperheros(superheros);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setCity("Test City");
        organization.setCountry("Test Country");
        organization.setPhone("Test Phone");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate("2022-01-01");
        sighting.setSuperhero(superhero);
        sighting.setSuperheros(superheros);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate("2022-02-01");
        sighting2.setSuperhero(superhero);
        sighting2.setSuperheros(superheros);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setCity("Test City");
        organization.setCountry("Test Country");
        organization.setPhone("Test Phone");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setId(1);
        sighting.setDate("2022-01-01");
        sighting.setSuperhero(superhero);
        sighting.setSuperheros(superheros);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting.getId(), fromDao.getId());
        
        sighting.setDate("2022-02-02");
        
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setCity("Test City");
        organization.setCountry("Test Country");
        organization.setPhone("Test Phone");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate("2022-01-01");
        sighting.setSuperhero(superhero);
        sighting.setSuperheros(superheros);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForSuperhero method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForSuperhero() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        List<Power> powers = new ArrayList<>();
        powers.add(power);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setCity("Test City");
        organization.setCountry("Test Country");
        organization.setPhone("Test Phone");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero name 2");
        superhero2.setDescription("Test Description 2");
        superhero2.setPowers(powers);
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);
        List<Superhero> superheros2 = new ArrayList<>();
        superheros2.add(superhero2);
        
        Sighting sighting = new Sighting();
        sighting.setDate("2022-01-01");
        sighting.setSuperhero(superhero);
        sighting.setSuperheros(superheros);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate("2022-02-01");
        sighting2.setSuperhero(superhero2);
        sighting2.setSuperheros(superheros2);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getSightingsForSuperhero(superhero.getId());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
    }
}
