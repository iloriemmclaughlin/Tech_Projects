/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.myretail.exceptions;

import java.time.LocalDateTime;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 
 *purpose: 
 */

public class Error {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String details;
    
    public Error(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    

}
