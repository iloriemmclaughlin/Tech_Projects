/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.myretail.data;

import com.irm.myretail.TestApplicationConfiguration;
import com.irm.myretail.entities.Price;
import com.irm.myretail.entities.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ilori
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class myRetailDaoDBTest {
    
    @Autowired
    myRetailDao dao;
    
    public myRetailDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Product> products = dao.getAllProducts();
        for (Product product : products) {
            dao.deleteProduct(product.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findById method, of class myRetailDaoDB.
     */
    @Test
    public void testFindById() {
        Price price = new Price();
        price.setValue(new BigDecimal("3.50"));
        price.setCurrencyCode("USD");
        
        Product product = new Product();
        product.setId(1);
        product.setName("Doritos");
        product.setPrice(price);
        dao.addProduct(product);
        
        Product fromDao = dao.findById(product.getId());
        
        assertEquals(product, fromDao);
    }

    /**
     * Test of findProductName method, of class myRetailDaoDB.
     */
    @Test
    public void testFindProductName() {
        Price price = new Price();
        price.setValue(new BigDecimal("3.50"));
        price.setCurrencyCode("USD");
        
        Product product = new Product();
        product.setId(1);
        product.setName("Doritos");
        product.setPrice(price);
        dao.addProduct(product);
        
        String fromDao = dao.findProductName(product.getId());
        
        assertEquals(product, fromDao);
    }

    /**
     * Test of update method, of class myRetailDaoDB.
     */
    @Test
    public void testUpdate() {
        Price price = new Price();
        price.setValue(new BigDecimal("3.50"));
        price.setCurrencyCode("USD");
        
        Product product = new Product();
        product.setId(1);
        product.setName("Doritos");
        product.setPrice(price);
        dao.addProduct(product);
        
        Product fromDao = dao.findById(product.getId());
        
        assertEquals(product, fromDao);
        
        price.setValue(new BigDecimal("5.00"));
        
        dao.update(product);
        
        assertNotEquals(product, fromDao);
        
        fromDao = dao.findById(product.getId());
        
        assertEquals(product, fromDao);
    } 
    
}
