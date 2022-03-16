/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.service;

import com.irm.myretail.exceptions.ProductNotFoundException;
import com.irm.myretail.models.Product;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/13/2022
 *purpose: Target Interview Code Review
 */


public interface myRetailServiceLayer {
    
    Product findById(int id) throws ProductNotFoundException;
    
    String findProductName(int id);
    
    void update(Product product);

}
