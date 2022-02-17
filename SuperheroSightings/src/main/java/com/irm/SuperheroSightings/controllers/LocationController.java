/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.controllers;

import com.irm.SuperheroSightings.dao.LocationDao;
import com.irm.SuperheroSightings.dao.OrganizationDao;
import com.irm.SuperheroSightings.dao.PowerDao;
import com.irm.SuperheroSightings.dao.SightingDao;
import com.irm.SuperheroSightings.dao.SuperheroDao;
import com.irm.SuperheroSightings.entities.Location;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 1/23/2022
 *purpose: Superhero Sightings
 */

@Controller
public class LocationController {
    
    @Autowired
    SuperheroDao superheroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    SightingDao sightingDao;
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        Location location = new Location();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        model.addAttribute("location", location);
        return "locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, HttpServletRequest request, Model model) {
        BigDecimal latitude = location.getLatitude();
        BigDecimal longitude = location.getLongitude();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setCity(request.getParameter("city"));
        location.setCountry(request.getParameter("country"));
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        
        List<Location> locations = locationDao.getAllLocations();
        
        if(result.hasErrors()) {
            model.addAttribute("locations", locations);
            model.addAttribute("location", location);
            return "locations";
        } else {
            locationDao.addLocation(location);
        }
        
        return "redirect:/locations";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if(result.hasErrors()) {
            return "editLocation";
        }
        
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }
}
