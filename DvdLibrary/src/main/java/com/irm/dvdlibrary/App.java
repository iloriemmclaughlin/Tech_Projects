/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary;

import com.irm.dvdlibrary.controller.DvdLibraryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public class App {
    
    public static void main(String[] args) {
        // UserIO myIo = new UserIOConsoleImpl();
        // DvdLibraryView myView = new DvdLibraryView(myIo);
        // DvdLibraryDao myDao = new DvdLibraryDaoFileImpl();
        // DvdLibraryController controller = new DvdLibraryController(myDao, myView);
        // controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        DvdLibraryController controller = ctx.getBean("controller", DvdLibraryController.class);
        controller.run();
    }

}
