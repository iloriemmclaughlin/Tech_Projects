/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary.controller;

import com.irm.dvdlibrary.dao.DvdLibraryDao;
import com.irm.dvdlibrary.dao.DvdLibraryDaoException;
import com.irm.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import com.irm.dvdlibrary.dto.Dvd;
import com.irm.dvdlibrary.ui.DvdLibraryView;
import com.irm.dvdlibrary.ui.UserIO;
import com.irm.dvdlibrary.ui.UserIOConsoleImpl;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public class DvdLibraryController {
    
    private DvdLibraryView view;
    private DvdLibraryDao dao;
    private UserIO io = new UserIOConsoleImpl();
    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addDvd();
                        break;
                    case 2:
                        removeDvd();
                        break;
                    case 3:
                        editDvd();
                        break;
                    case 4:
                        findDvd();
                        break;
                    case 5:
                        listAllDvds();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (DvdLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void addDvd() throws DvdLibraryDaoException {
        view.displayAddDvdBanner();
        Dvd newDvd = view.getNewDvdInfo();
        dao.addDvd(newDvd.getTitle(), newDvd);
        view.displayAddSuccessBanner();
    }
    
    private void listAllDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllDvds();
        view.displayDvdList(dvdList);
    }
    
    private void editDvd() throws DvdLibraryDaoException {
        view.displayEditDvdBanner();
        String title = view.getTitleChoice();
        Dvd editedDvd = dao.getDvd(title);
        view.displayDvd(editedDvd);
        editedDvd = view.editDvdInfo(editedDvd);
        dao.editDvd(editedDvd.getTitle(), editedDvd);
        view.displayEditDvdSuccess(editedDvd);   
    }
    
    private void findDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String title = view.getTitleChoice();
        Dvd dvd = dao.getDvd(title);
        view.displayDvd(dvd);
    }
    
    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        String title = view.getTitleChoice();
        Dvd removedDvd = dao.removeDvd(title);
        view.displayRemoveResult(removedDvd);
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

}
