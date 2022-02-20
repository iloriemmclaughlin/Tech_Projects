/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary.ui;

import com.irm.dvdlibrary.dto.Dvd;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public class DvdLibraryView {
    
    private UserIO io;
    
    public int printMenuAndGetSelection() {
            io.print("Main Menu");
            io.print("1. Add DVD");
            io.print("2. Remove DVD");
            io.print("3. Edit DVD");
            io.print("4. Find DVD");
            io.print("5. List All DVD's");
            io.print("6. Exit");
            
            return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public Dvd getNewDvdInfo() {
        String title = io.readString("Please enter Title");
        String releaseDate = io.readString("Please enter Release Date");
        String rating = io.readString("Please enter MPAA Rating");
        String directorName = io.readString("Please enter Director's Name");
        String studio = io.readString("Please enter Studio");
        String note = io.readString("Please enter any additional information");
        Dvd currentDvd = new Dvd(title);
        currentDvd.setReleaseDate(releaseDate);
        currentDvd.setRating(rating);
        currentDvd.setDirectorName(directorName);
        currentDvd.setStudio(studio);
        currentDvd.setNote(note);
        return currentDvd;
    }
    
    public void displayAddDvdBanner() {
        io.print("=== Add Dvd ===");
    }
    
    public void displayAddSuccessBanner() {
        io.print("Dvd successfully added to library.");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDvdList(List<Dvd> dvdList) {
        for (Dvd currentDvd : dvdList) {
            String dvdInfo = String.format("%s : %s, %s, %s, %s, %s",
                    currentDvd.getTitle(),
                    currentDvd.getReleaseDate(),
                    currentDvd.getRating(),
                    currentDvd.getDirectorName(),
                    currentDvd.getStudio(),
                    currentDvd.getNote());
            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner() {
        io.print("=== Display All Dvd's ===");
    }
    
    public void displayDisplayDvdBanner() {
        io.print("=== Display Dvd ===");
    }
    
    public String getTitleChoice() {
        return io.readString("Please enter the Title of Dvd.");
    }
    
    public void displayDvd(Dvd dvd) {
        if (dvd != null) {
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate());
            io.print(dvd.getRating());
            io.print(dvd.getDirectorName());
            io.print(dvd.getStudio());
            io.print(dvd.getNote());
        } else {
            io.print("No such dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveDvdBanner() {
        io.print("=== Remove Dvd ===");
    }
    
    public void displayRemoveResult(Dvd dvdRecord) {
        if(dvdRecord != null) {
            io.print("Dvd successfully removed.");
        } else {
            io.print("No such Dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayEditDvdBanner() {
        io.print("=== Edit Dvd ===");
    }
    
    public Dvd editDvdInfo(Dvd editedDvd) {
        String title = io.readString("Please enter Title");
        String releaseDate = io.readString("Please enter Release Date");
        String rating = io.readString("Please enter MPAA Rating");
        String directorName = io.readString("Please enter Director's Name");
        String studio = io.readString("Please enter Studio");
        String note = io.readString("Please enter any additional information");
        editedDvd.setReleaseDate(releaseDate);
        editedDvd.setRating(rating);
        editedDvd.setDirectorName(directorName);
        editedDvd.setStudio(studio);
        editedDvd.setNote(note);
        return editedDvd;
    }
    
    public void displayEditDvdSuccess(Dvd dvdRecord) {
        if(dvdRecord != null) {
            io.print("Dvd successfully edited.");
        } else {
            io.print("No such Dvd.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayExitBanner() {
        io.print("Goodbye!!!");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public DvdLibraryView(UserIO io) {
        this.io = io;
    }
    
    public void displayErrorMessage(String errorMessage) {
        io.print("=== ERROR ===");
        io.print(errorMessage);
    }
}
