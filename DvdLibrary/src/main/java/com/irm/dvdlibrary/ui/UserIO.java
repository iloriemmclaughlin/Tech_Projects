/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.dvdlibrary.ui;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 5/27/2021
 *purpose: DVD LIBRARY FINAL ASSESSMENT
 */
public interface UserIO {
    void print(String msg);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);

}
