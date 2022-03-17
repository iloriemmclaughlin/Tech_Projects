/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.irm.myretail.data.myRetailDao;
import com.irm.myretail.entities.Price;
import com.irm.myretail.entities.Product;
import com.irm.myretail.exceptions.ProductNotFoundException;
import com.irm.myretail.service.myRetailServiceLayer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review 
 */

@RestController
@RequestMapping("/api/myRetail")
public class myRetailController {
    
    @Autowired
    myRetailDao dao;
    
    @Autowired
    myRetailServiceLayer service;
    
    @GetMapping("/products/{id}")
    public ResponseEntity findById(@PathVariable int id) {
        try {
            return new ResponseEntity(service.findById(id), HttpStatus.OK);
        } catch(ProductNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/products/name/{id}")
    public ResponseEntity<String> findProductName(@PathVariable int id) {
        try {
            String result = service.findProductName(id);
            return ResponseEntity.ok(result);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Product product) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);     
        try {
            service.update(product);
            return response;
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    // Getting product from "external API"
    @GetMapping(value = "/product")
    public Product getProduct() {
        Price price = new Price();
        price.setValue(new BigDecimal("16.25"));
        price.setCurrencyCode("USD");
        return new Product(1, "Water Bottle", price);
    }
    
    @GetMapping(value = "/getproductstring")
    private String getProductString() {
        String uri = "http://localhost:8080/api/myRetail/product";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }
    
    @GetMapping(value = "/getproduct")
    private Product getProductObject() {
        String uri = "http://localhost:8080/api/myRetail/product";
        RestTemplate restTemplate = new RestTemplate();
        Product result = restTemplate.getForObject(uri, Product.class);
        return result;
    }
    
    @GetMapping(value = "/getproductname")
    private String getProductName() {
        String uri = "http://localhost:8080/api/myRetail/product";
        RestTemplate restTemplate = new RestTemplate();
        Product product = restTemplate.getForObject(uri, Product.class);
        String result = product.getName();
        return result;
    }

}
