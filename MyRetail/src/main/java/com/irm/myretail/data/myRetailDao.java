/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.data;

import com.irm.myretail.entities.Product;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review
 */


public interface myRetailDao {
    
    /**
     * Returns the product object associated with the given product id.
     * Returns null if no such product exists.
     * 
     * @param id id of the product to retrieve
     * @return  the Product object associated with the given product id,
     * null if it does not exist.
     */
    Product findById(int id);
    
    /**
     * Returns the name of product associated with the given product id.
     * Returns null if no such product exists.
     * 
     * @param id id of the product name to retrieve
     * @return the product name associated with the given product id,
     * null if it does not exist.
     */
    String findProductName(int id);
    
    /**
     * Updates product information in the database
     * 
     * @param product updates product price with associated id information
     */
    void update(Product product);

}
