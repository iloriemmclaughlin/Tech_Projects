/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.data;

import com.irm.myretail.models.Product;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review
 */


public interface myRetailDao {
    
    Product findById(int id);
    
    String findProductName(int id);
    
    void update(Product product);

}
