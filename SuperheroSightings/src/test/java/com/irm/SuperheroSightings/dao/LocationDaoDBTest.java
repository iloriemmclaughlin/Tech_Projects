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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author ilori
 */

@SpringBootTest
public class LocationDaoDBTest {
    
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
    
    public LocationDaoDBTest() {
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
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setCity("Test City 2");
        location2.setCountry("Test Country 2");
        location2.setLatitude(new BigDecimal("61.00000").setScale(5));
        location2.setLongitude(new BigDecimal("78.00000").setScale(5));
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setName("New test name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
        location.setLatitude(new BigDecimal("42.00000").setScale(5));
        location.setLongitude(new BigDecimal("52.00000").setScale(5));
        location = locationDao.addLocation(location);
        List<Location> locations = new ArrayList<>();
        locations.add(location);
        
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
        List<Organization> organizations = new ArrayList<>();
        organization = organizationDao.addOrganization(organization);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        List<Superhero> superheros = new ArrayList<>();
        superheros.add(superhero);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getLocationsForSuperhero method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsForSuperhero() {
        
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
        sighting.setLocation(location);
        sighting.setSuperheros(superheros);
        sighting = sightingDao.addSighting(sighting);
        
        List<Location> list = locationDao.getLocationsForSuperhero(superhero);
        assertEquals(1, list.size());
    }
    
}
