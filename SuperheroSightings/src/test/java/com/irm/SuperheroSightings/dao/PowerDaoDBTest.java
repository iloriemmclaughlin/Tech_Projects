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
public class PowerDaoDBTest {
    
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
    
    public PowerDaoDBTest() {
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
     * Test of getPowerById method, of class PowerDaoDB.
     */
    @Test
    public void testAddGetPowerById() {
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        
        Power fromDao = powerDao.getPowerById(power.getId());
        
        assertEquals(power, fromDao);
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        
        Power power2 = new Power();
        power2.setName("Test Power Name 2");
        power2 = powerDao.addPower(power2);
        
        List<Power> powers = powerDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }

    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        
        Power fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);
        
        power.setName("New Test Name");
        powerDao.updatePower(power);
        
        assertNotEquals(power, fromDao);
        
        fromDao = powerDao.getPowerById(power.getId());
        
        assertEquals(power, fromDao);
    }

    /**
     * Test of deletePowerById method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerById() {
        Power power = new Power();
        power.setName("Test Power Name");
        power = powerDao.addPower(power);
        
        Power fromDao = powerDao.getPowerById(power.getId());
        assertEquals(power, fromDao);
        
        powerDao.deletePowerById(power.getId());
        
        fromDao = powerDao.getPowerById(power.getId());
        assertNull(fromDao);
    }
    
}
