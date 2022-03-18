/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irm.myretail.data;

import com.irm.myretail.entities.Price;
import com.irm.myretail.entities.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Iloriem McLaughlin email: iloriem.pena@gmail.com date: 3/12/2022
 * purpose: Target Interview Code Review
 */
@Repository
public class myRetailDaoDB implements myRetailDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Product findById(int id) {
        try {
            final String sql = "SELECT * FROM product WHERE id = ?;";
            Product product = jdbc.queryForObject(sql, new ProductMapper(), id);
            product.setPrice(getPriceForProduct(id));
            return product;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    private Price getPriceForProduct(int id) {
        final String sql = "SELECT c.*, p.value FROM currency c "
                + "JOIN product_currency pc ON pc.currencyId = c.id "
                + "JOIN product p ON pc.productId = p.id WHERE pc.currencyId = 1 AND pc.productId = ?;";
        return jdbc.queryForObject(sql, new PriceMapper(), id);
    }

    @Override
    public String findProductName(int id) {
        try {
            final String sql = "SELECT * FROM product WHERE id = ?;";
            Product product = jdbc.queryForObject(sql, new ProductMapper(), id);
            product.setPrice(getPriceForProduct(id));
            String result = product.getName();
            return result;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void update(Product product) {
        Price price = product.getPrice();
        final String sql = "UPDATE product SET "
                + "value = ? "
                + "WHERE id = ?;";
        jdbc.update(sql, price.getValue(), product.getId());
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
            price.setValue(rs.getBigDecimal("value"));
            price.setCurrencyCode(rs.getString("code"));
            return price;
        }
    }

}
