/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.myretail.service;

import com.irm.myretail.data.myRetailDao;
import com.irm.myretail.exceptions.ProductNotFoundException;
import com.irm.myretail.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Iloriem McLaughlin email: iloriem.pena@gmail.com date: 3/13/2022
 * purpose: Target Interview Code Review
 */
@Service
public class myRetailServiceLayerImpl implements myRetailServiceLayer {

    private final myRetailDao dao;

    @Autowired
    public myRetailServiceLayerImpl(myRetailDao dao) {
        this.dao = dao;
    }

    @Override
    public Product findById(int id) throws ProductNotFoundException {
        Product product = dao.findById(id);
        if (product != null) {
            return product;
        } else {
            throw new ProductNotFoundException("ERROR: Product not found.");
        }
    }

    @Override
    public String findProductName(int id) {
        String name = dao.findProductName(id);
        if (name != null) {
            return name;
        } else {
            throw new ProductNotFoundException("Error: Product name not found.");
        }
    }

    @Override
    public void update(Product product) {
        Product fromDao = findById(product.getId());
        if (fromDao != null) {
            dao.update(product);
        } else {
            throw new ProductNotFoundException("Error: Product name not found.");
        }
    }

}
