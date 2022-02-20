/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary.dao;

import com.irm.dvdlibrary.dto.Dvd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public class DvdLibraryDaoFileImpl implements DvdLibraryDao {
    
    private final String LIBRARY_FILE;
    public static final String DELIMITER = "::";
    private Map<String, Dvd> dvds = new HashMap<>();
    private Set<String> titles = dvds.keySet();
    private ArrayList<String> titleList = new ArrayList<String>(titles);

    public DvdLibraryDaoFileImpl() {
        LIBRARY_FILE = "library.txt";
    }
    
    public DvdLibraryDaoFileImpl(String libraryTextFile) {
        LIBRARY_FILE = libraryTextFile;
    }
    
    @Override
    public Dvd addDvd(String title, Dvd dvd) throws DvdLibraryDaoException {
        loadLibrary();
        Dvd newDvd = dvds.put(title, dvd);
        writeLibrary();
        return newDvd;
    }

    @Override
    public Dvd removeDvd(String title) throws DvdLibraryDaoException {
        loadLibrary();
        Dvd removedDvd = dvds.remove(title);
        writeLibrary();
        return removedDvd;
    }

    @Override
    public Dvd editDvd(String title, Dvd dvd) throws DvdLibraryDaoException {
        loadLibrary();
        Dvd editedDvd = dvds.replace(title, dvd);
        writeLibrary();
        return editedDvd;
    }

    @Override
    public Dvd getDvd(String title) throws DvdLibraryDaoException {
        loadLibrary();
        return dvds.get(title);
    }

    @Override
    public List<Dvd> getAllDvds() throws DvdLibraryDaoException {
        loadLibrary();
        return new ArrayList<Dvd>(dvds.values());
    }
    
    @Override
    public List<Dvd> getDvdsByYear(String year) throws DvdLibraryDaoException {
        return dvds.values().stream()
                .filter(d -> d.getReleaseDate().equals(year))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdsByRating(String rating) throws DvdLibraryDaoException {
        return dvds.values().stream()
                .filter(d -> d.getRating().equalsIgnoreCase(rating))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdsByDirector(String director) throws DvdLibraryDaoException {
        return dvds.values().stream()
                .filter(d -> d.getDirectorName().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dvd> getDvdsByStudio(String studio) throws DvdLibraryDaoException {
        return dvds.values().stream()
                .filter(d -> d.getStudio().equalsIgnoreCase(studio))
                .collect(Collectors.toList());
    }

    @Override
    public int getDvdsAverageAge(int age) throws DvdLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dvd getDvdsNewest(String title) throws DvdLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Dvd getOldestDvd(String title) throws DvdLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAverageNumNotes(int notes) throws DvdLibraryDaoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Dvd unmarshallDvd(String dvdAsText) {
        String [] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        Dvd dvdFromFile = new Dvd(title);
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setRating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setNote(dvdTokens[5]);
        return dvdFromFile;
    }
    
    private void loadLibrary() throws DvdLibraryDaoException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DvdLibraryDaoException("-_- Could not load library data into memory.", e);
        }
        
        String currentLine;
        Dvd currentDvd;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentDvd = unmarshallDvd(currentLine);
            dvds.put(currentDvd.getTitle(), currentDvd);
        }
        scanner.close();
    }
    
    private String marshallDvd(Dvd aDvd) {
        String dvdAsText = aDvd.getTitle() + DELIMITER;
        dvdAsText += aDvd.getReleaseDate() + DELIMITER;
        dvdAsText += aDvd.getRating() + DELIMITER;
        dvdAsText += aDvd.getDirectorName() + DELIMITER;
        dvdAsText += aDvd.getStudio() + DELIMITER;
        dvdAsText += aDvd.getNote();
        return dvdAsText;
    }
    
    private void writeLibrary() throws DvdLibraryDaoException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException("Could not save dvd data.", e);
        }
        
        String dvdAsText;
        List<Dvd> dvdList = this.getAllDvds();
        for (Dvd currentDvd : dvdList) {
            dvdAsText = marshallDvd(currentDvd);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }   
}
