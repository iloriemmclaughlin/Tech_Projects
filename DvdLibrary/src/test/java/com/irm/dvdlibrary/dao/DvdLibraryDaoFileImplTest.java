/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.dvdlibrary.dao;

import com.irm.dvdlibrary.dto.Dvd;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author ilori
 */
public class DvdLibraryDaoFileImplTest {
    
    DvdLibraryDao testDao;
    
    public DvdLibraryDaoFileImplTest() {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("dvdLibraryDao", DvdLibraryDaoFileImpl.class);
        
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testlibrary.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new DvdLibraryDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetDvd() throws Exception {
        // Create our method test inputs
        String title = "Toy Story";
        Dvd dvd = new Dvd(title);
        dvd.setReleaseDate("1995");
        dvd.setRating("PG");
        dvd.setDirectorName("Lasseter");
        dvd.setStudio("Pixar");
        dvd.setNote("N/A");
        
        // Add the dvd to the DAO
        testDao.addDvd(title, dvd);
        // Get the dvd from the DAO
        Dvd retrievedDvd = testDao.getDvd(title);
        
        // Check the data is equal
        assertEquals(dvd.getTitle(),
                retrievedDvd.getTitle(), "Checking title.");
        assertEquals(dvd.getReleaseDate(),
                retrievedDvd.getReleaseDate(), "Checking release date.");
        assertEquals(dvd.getRating(),
                retrievedDvd.getRating(), "Checking rating.");
        assertEquals(dvd.getDirectorName(),
                retrievedDvd.getDirectorName(), "Checking director name.");
        assertEquals(dvd.getStudio(), 
                retrievedDvd.getStudio(), "Checking studio.");
        assertEquals(dvd.getNote(),
                retrievedDvd.getNote(), "Checking note.");
    }
    
    @Test
    public void testAddGetAllDvds() throws Exception {
        // Create our first Dvd
        Dvd firstDvd = new Dvd("Toy Story");
        firstDvd.setReleaseDate("1995");
        firstDvd.setRating("PG");
        firstDvd.setDirectorName("Lasseter");
        firstDvd.setStudio("Pixar");
        firstDvd.setNote("N/A");
        
        // Create our second Dvd
        Dvd secondDvd = new Dvd("Inside Out");
        secondDvd.setReleaseDate("2015");
        secondDvd.setRating("PG");
        secondDvd.setDirectorName("Docter");
        secondDvd.setStudio("Pixar");
        secondDvd.setNote("N/A");
        
        // Add both our dvds to the DAO
        testDao.addDvd(firstDvd.getTitle(), firstDvd);
        testDao.addDvd(secondDvd.getTitle(), secondDvd);
        
        // Retrieve the list of all dvds within the DAO
        List <Dvd> allDvds = testDao.getAllDvds();
        
        // First check the general contents of the list
        assertNotNull(allDvds, "The list of dvds must be not null.");
        assertEquals(2, allDvds.size(), "List of dvds should have 2 dvds.");
        
        // Then the specifics
        assertTrue(testDao.getAllDvds().contains(firstDvd),
                "The list of dvds should include Toy Story.");
        assertTrue(testDao.getAllDvds().contains(secondDvd),
                "The list of dvds should inclue Inside Out.");
    }
    
    @Test
    public void testRemoveDvd() throws Exception {
        // Create two new dvds
        Dvd firstDvd = new Dvd("Toy Story");
        firstDvd.setReleaseDate("1995");
        firstDvd.setRating("PG");
        firstDvd.setDirectorName("Lasseter");
        firstDvd.setStudio("Pixar");
        firstDvd.setNote("N/A");
        
        Dvd secondDvd = new Dvd("Inside Out");
        secondDvd.setReleaseDate("2015");
        secondDvd.setRating("PG");
        secondDvd.setDirectorName("Docter");
        secondDvd.setStudio("Pixar");
        secondDvd.setNote("N/A");
        
        // Add both to the DAO
        testDao.addDvd(firstDvd.getTitle(), firstDvd);
        testDao.addDvd(secondDvd.getTitle(), secondDvd);
        
        // Remove the first dvd - Toy Story
        Dvd removedDvd = testDao.removeDvd(firstDvd.getTitle());
        
        // Check that the correct object was removed.
        assertEquals(removedDvd, firstDvd, "The removed dvd should be Toy Story.");
        
        // Get all the dvds
        List <Dvd> allDvds = testDao.getAllDvds();
        
        // First check the general contents of the list
        assertNotNull(allDvds, "All dvds list should be not null.");
        assertEquals(1, allDvds.size(), "All dvds should only have 1 dvd.");
        
        // Then the specifics
        assertFalse(allDvds.contains(firstDvd), "All dvds should NOT include Toy Story.");
        assertTrue(allDvds.contains(secondDvd), "All dvds should include Inside Out.");
        
        // Remove the second dvd
        removedDvd = testDao.removeDvd(secondDvd.getTitle());
        // Check that the correct object was removed
        assertEquals(removedDvd, secondDvd, "The removed dvd should be Inside Out.");
        
        // Retrieve all of the dvds again, and check the list
        allDvds = testDao.getAllDvds();
        
        // Check the contents of the list - it should be empty
        assertTrue(allDvds.isEmpty(), "The retrieved list of dvds should be empty.");
        
        // Try to 'get' both dvds by their old id - they should be null!
        Dvd retrievedDvd = testDao.getDvd(firstDvd.getTitle());
        assertNull(retrievedDvd, "Toy Story was removed, should be null.");
        
        retrievedDvd = testDao.getDvd(secondDvd.getTitle());
        assertNull(retrievedDvd, "Inside Out was removed, should be null.");
    }
    
    @Test
    public void testEditDvd() throws Exception {
        String title = "Toy Story";
        Dvd dvd = new Dvd(title);
        dvd.setReleaseDate("1995");
        dvd.setRating("PG");
        dvd.setDirectorName("Lasseter");
        dvd.setStudio("Pixar");
        dvd.setNote("N/A");
        
        // Add the dvd to the DAO
        testDao.addDvd(title, dvd);
        // Get the dvd from the DAO
        Dvd retrievedDvd = testDao.getDvd(title);
        
        // Check the data is equal
        assertEquals(dvd.getTitle(),
                retrievedDvd.getTitle(), "Checking title.");
        assertEquals(dvd.getReleaseDate(),
                retrievedDvd.getReleaseDate(), "Checking release date.");
        assertEquals(dvd.getRating(),
                retrievedDvd.getRating(), "Checking rating.");
        assertEquals(dvd.getDirectorName(),
                retrievedDvd.getDirectorName(), "Checking director name.");
        assertEquals(dvd.getStudio(), 
                retrievedDvd.getStudio(), "Checking studio.");
        assertEquals(dvd.getNote(),
                retrievedDvd.getNote(), "Checking note.");
        
        // Change contents of dvd
        
        
        
    }
    
}
