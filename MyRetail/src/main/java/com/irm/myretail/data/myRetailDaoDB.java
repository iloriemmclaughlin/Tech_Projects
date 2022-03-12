/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.data;

import com.irm.myretail.models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review
 */

@Repository
public class myRetailDaoDB implements myRetailDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public myRetailDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product findById(int id) {
        final String sql = "SELECT id, name, price, currency "
                + "FROM product WHERE id = ?;";
        
        return jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
    }

    @Override
    public Product findName(int id) {
        final String sql = "SELECT name FROM product WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
    }

    @Override
    public Boolean update(Product product) {
        final String sql = "UPDATE product SET price = ? WHERE id = ?;";
        
        return jdbcTemplate.update(sql, product.getPrice(), product.getId()) > 0;
    }
    
    private static final class ProductMapper implements RowMapper<Product> {
        
        @Override
        public Product mapRow(ResultSet rs, int index) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            product.setCurrency(rs.getString("currency"));
            return product;
        }
    }

}
