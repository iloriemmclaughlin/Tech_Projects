/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary.dao;

import com.irm.dvdlibrary.dto.Dvd;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public interface DvdLibraryDao {
    
    /**
     * Adds given Dvd to library and associates it with given title.
     * If Dvd already exists, it will return that Dvd, otherwise return null.
     * 
     * @param title id for Dvd to be associated with
     * @param dvd dvd to be added to library
     * @return Dvd object previously associated with given title if it exists, otherwise null
     */
    Dvd addDvd(String title, Dvd dvd) throws DvdLibraryDaoException;
    
    /**
     * Removes given Dvd from library and associates it with given title
     * Returns dvd object that is being removed or null if no dvd is associated with given title
     * 
     * @param title id of dvd to be removed
     * @return Dvd object that was removed or null if no Dvd is associated with it
     */
    Dvd removeDvd(String title) throws DvdLibraryDaoException;
    
    /**
     * Edits given Dvd to library and re-associates it with given title
     * If given title is not associated with dvd, it will return null
     * 
     * @param title id with which dvd is to be associated
     * @param dvd dvd object to be re-added to library
     * @return 
     */
    Dvd editDvd(String title, Dvd dvd) throws DvdLibraryDaoException;
    
    /**
     * Returns dvd associated with given title
     * Returns null if no such dvd exists
     * 
     * @param title id of dvd to retrieve
     * @return dvd object associated with given title, null if no such dvd exists
     */
    Dvd getDvd(String title) throws DvdLibraryDaoException;
    
    /**
     * Returns list of all DVD's in library
     * 
     * @return 
     */
    List<Dvd> getAllDvds() throws DvdLibraryDaoException;
   
    List<Dvd> getDvdsByYear(String year) throws DvdLibraryDaoException;
    
    List<Dvd> getDvdsByRating(String rating) throws DvdLibraryDaoException;
    
    List<Dvd> getDvdsByDirector(String director) throws DvdLibraryDaoException;
    
    List<Dvd> getDvdsByStudio(String studio) throws DvdLibraryDaoException;
    
    int getDvdsAverageAge(int age) throws DvdLibraryDaoException;
    
    Dvd getDvdsNewest(String title) throws DvdLibraryDaoException;
    
    Dvd getOldestDvd(String title) throws DvdLibraryDaoException;
    
    int getAverageNumNotes(int notes) throws DvdLibraryDaoException;
}
