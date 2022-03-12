/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.controllers;

import com.irm.myretail.data.myRetailDao;
import com.irm.myretail.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review 
 */

@RestController
@RequestMapping("/api/myRetail")
public class myRetailController {
    
    private final myRetailDao dao;
    
    public myRetailController(myRetailDao dao) {
        this.dao = dao;
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable int id) {
        Product result = dao.findById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Product product) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != product.getId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (dao.update(product)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

}
