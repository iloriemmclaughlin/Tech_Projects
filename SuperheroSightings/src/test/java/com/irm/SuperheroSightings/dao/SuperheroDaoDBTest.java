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
public class SuperheroDaoDBTest {
    
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
    
    public SuperheroDaoDBTest() {
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
     * Test of getSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testAddGetSuperheroById() {
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of getAllSuperheros method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheros() {
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
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero name 2");
        superhero2.setDescription("Test Description 2");
        superhero2.setPowers(powers);
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        assertEquals(2, superheros.size());
        assertTrue(superheros.contains(superhero));
        assertTrue(superheros.contains(superhero2));
    }

    /**
     * Test of updateSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testUpdateSuperhero() {
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
        
        superhero.setName("New Test Name");
        
        superheroDao.updateSuperhero(superhero);
        
        assertNotEquals(superhero, fromDao);
        
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of deleteSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testDeleteSuperheroById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setCity("Test City");
        location.setCountry("Test Country");
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertEquals(superhero, fromDao);
        
        superheroDao.deleteSuperheroById(superhero.getId());
        
        fromDao = superheroDao.getSuperheroById(superhero.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSuperherosForPower method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetSuperherosForPower() {
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
        
        List<Superhero> superheros = superheroDao.getSuperherosForPower(power);
        assertEquals(1, superheros.size());
    }
    
    @Test
    public void testGetSuperherosForOrganization() {
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
        List<Organization> organizationList1 = new ArrayList<>();
        organizationList1.add(organization);
        
        Organization organization2 = new Organization();
        organization2.setName("Test Name 2");
        organization2.setDescription("Test Description 2");
        organization2.setCity("Test City 2");
        organization2.setCountry("Test Country 2");
        organization2.setPhone("Test Phone 2");
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> organizationList2 = new ArrayList<>();
        organizationList2.add(organization2);
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Superhero name");
        superhero.setDescription("Test Description");
        superhero.setPowers(powers);
        superhero.setOrganizations(organizationList1);
        superhero = superheroDao.addSuperhero(superhero);
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Superhero name");
        superhero2.setDescription("Test Description");
        superhero2.setPowers(powers);
        superhero2.setOrganizations(organizationList2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        List<Superhero> list1 = superheroDao.getSuperherosForOrganization(organization);
        assertEquals(1, list1.size());
        
        List<Superhero> list2 = superheroDao.getSuperherosForOrganization(organization2);
        assertEquals(1, list2.size());
    }
    
}
