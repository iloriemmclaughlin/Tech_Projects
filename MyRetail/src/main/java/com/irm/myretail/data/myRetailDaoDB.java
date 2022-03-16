/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.myretail.data;

import com.irm.myretail.models.Price;
import com.irm.myretail.models.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Iloriem McLaughlin email: iloriem.pena@gmail.com date: 3/12/2022
 * purpose: Target Interview Code Review
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
        final String sql = "SELECT * FROM product WHERE id = ?;";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
            product.setPrice(getPriceForProduct(id));
            return product;
        } catch(EmptyResultDataAccessException ex) {
            return null;
        }

    }

    private Price getPriceForProduct(int id) {
        final String sql = "SELECT c.*, p.value FROM currency c "
                + "JOIN product_currency pc ON pc.currencyId = c.id "
                + "JOIN product p ON pc.productId = p.id WHERE pc.currencyId = 1 AND pc.productId = ?;";
        return jdbcTemplate.queryForObject(sql, new PriceMapper(), id);
    }

    @Override
    public String findProductName(int id) {
        final String sql = "SELECT * FROM product WHERE id = ?;";
        Product product = jdbcTemplate.queryForObject(sql, new ProductMapper(), id);
        product.setPrice(getPriceForProduct(id));
        String result = product.getName();
        return result;
    }

    @Override
    public void update(Product product) {
        Price price = product.getPrice();
        final String sql = "UPDATE product SET "
                + "value = ? "
                + "WHERE id = ?;";
        jdbcTemplate.update(sql, price.getValue(), product.getId());
    }

    private static final class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int index) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            return product;
        }
    }

    private static final class PriceMapper implements RowMapper<Price> {

        @Override
        public Price mapRow(ResultSet rs, int index) throws SQLException {
            Price price = new Price();
            price.setValue(rs.getString("value"));
            price.setCurrencyCode(rs.getString("code"));
            return price;
        }
    }

}
