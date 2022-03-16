/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.exceptions;

import com.irm.myretail.exceptions.ProductNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 3/12/2022
 *purpose: Target Interview Code Review
 */

@ControllerAdvice
@RestController
public class myRetailGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Error> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        Error error = new Error(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
