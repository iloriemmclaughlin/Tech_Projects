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
import com.irm.SuperheroSightings.entities.Sighting;
import com.irm.SuperheroSightings.entities.Superhero;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 1/23/2022
 *purpose: Superhero Sightings Project
 */

@Controller
public class SightingController {
    
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
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        Sighting sighting = new Sighting();
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheros", superheros);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        model.addAttribute("sighting", sighting);
        return "sightings";   
    }
  
    @GetMapping("sightingsDate")
    public String searchSightingsByDate(Model model) {
        Sighting sighting = new Sighting();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        model.addAttribute("sighting", sighting);
        return "sightingsDate";
    }
    
    @GetMapping("viewSightingsByDate")
    public String displaySightingsByDate(Sighting sighting, Model model) {
        List<Sighting> sightings = sightingDao.getSightingsByDate(sighting.getDate());
        model.addAttribute("sightings", sightings);
        model.addAttribute("sighting", sighting);
        return "viewSightingsByDate";
    }
    
    @GetMapping("sightingsLocation")
    public String searchSightingsByLocation(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("locations", locations);
        model.addAttribute("sightings", sightings);
        return "sightingsLocation";
    }
    
    @GetMapping("viewSightingsByLocation")
    public String displaySightingsByLocation(Sighting sighting, Model model) {
        List<Location> locations = locationDao.getAllLocations();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("locations", locations);
        model.addAttribute("sightings", sightings);
        return "viewSightingsByLocation";
    }
    
    @GetMapping("sightingsSuperhero")
    public String searchSightingsBySuperhero(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheros", superheros);
        return"sightingsSuperhero";
    }
    
    @GetMapping("viewSightingsBySuperhero")
    public String displaySightingsBySuperhero(Sighting sighting, Model model) {
        List<Sighting> sightings = sightingDao.getSightingsForSuperhero(sighting.getSuperhero().getId());
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheros", superheros);
        return"viewSightingsBySuperhero";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheros", superheros);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) throws ParseException {
        LocalDate ld = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd"));
        String formatted = ld.format(formatter);
        sighting.setDate(request.getParameter("date"));
        String sightingDate = sighting.getDate();
        if(!sightingDate.isBlank() || !sightingDate.isEmpty()) {
            Date theSightingDate = new SimpleDateFormat("yyyy-MM-dd").parse(sightingDate);
            Date tomorrow = new SimpleDateFormat("yyyy-MM-dd").parse(formatted);
            Boolean compare1 = sightingDate.equalsIgnoreCase(formatted);
            Boolean compare2 = theSightingDate.after(tomorrow);
            if (compare1 == true || compare2 == true) {
                FieldError error = new FieldError("sighting", "date", "Date cannot be in the future.");
                result.addError(error);
            }
        }
        
        String locationId = request.getParameter("locationId");
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        String[] superheroIds = request.getParameterValues("superheroId");
        
        List<Superhero> superheros = new ArrayList<>();
        if(superheroIds != null) {
            for(String superheroId : superheroIds) {
                superheros.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            FieldError error = new FieldError("sighting", "superheros", "Must include one superhero");
            result.addError(error);
        }
        
        sighting.setSuperheros(superheros);
        
        if(result.hasErrors()) {
            model.addAttribute("superheros", superheroDao.getAllSuperheros());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sighting", sighting);
            return "sightings";
        } else {
            sightingDao.addSighting(sighting);
        }

        return "redirect:/sightings";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        sighting.setDate(request.getParameter("date"));
        String[] superheroIds = request.getParameterValues("superheroId");
        String locationId = request.getParameter("locationId");
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        
        List<Superhero> superheros = new ArrayList<>();
        if(superheroIds != null) {
            for(String superheroId : superheroIds) {
                superheros.add(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
            }
        } else {
            FieldError error = new FieldError("sighting", "superheros", "Must include one superhero");
            result.addError(error);
        }
        
        sighting.setSuperheros(superheros);
        
        if(result.hasErrors()) {
            model.addAttribute("superheros", superheroDao.getAllSuperheros());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sighting", sighting);
            return "editSighting";
        } else {
            sightingDao.updateSighting(sighting);
        }
        
        return "redirect:/sightings";
    }
    
    @PostMapping("viewSightingsByDate")
    public String performSightingsByDate(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) throws ParseException {
        LocalDate ld = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd"));
        String formatted = ld.format(formatter);
        sighting.setDate(request.getParameter("date"));
        String sightingDate = sighting.getDate();
        if(!sightingDate.isBlank() || !sightingDate.isEmpty()) {
            Date theSightingDate = new SimpleDateFormat("yyyy-MM-dd").parse(sightingDate);
            Date tomorrow = new SimpleDateFormat("yyyy-MM-dd").parse(formatted);
            Boolean compare1 = sightingDate.equalsIgnoreCase(formatted);
            Boolean compare2 = theSightingDate.after(tomorrow);
            if (compare1 == true || compare2 == true) {
                FieldError error = new FieldError("sighting", "date", "Date cannot be in the future.");
                result.addError(error);
            }
        }
        
        if(result.hasErrors()) {
            model.addAttribute("superheros", superheroDao.getAllSuperheros());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sighting", sighting);
            return "viewSightingsByDate";
        }
        
        List<Sighting> sightings = sightingDao.getSightingsByDate(request.getParameter("date"));
        model.addAttribute("sightings", sightings);
        return "viewSightingsByDate";
    }
    
    @PostMapping("viewSightingsByLocation")
    public String perfromSightingsByLocation(Sighting sighting, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        List<Sighting> sightings = sightingDao.getSightingsByLocation(Integer.parseInt(locationId));
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "viewSightingsByLocation";
    }
    
    @PostMapping("viewSightingsBySuperhero")
    public String performSightingsBySuperhero(Sighting sighting, HttpServletRequest request, Model model) {
        String superheroId = request.getParameter("superheroId");
        List<Sighting> sightings = sightingDao.getSightingsForSuperhero(Integer.parseInt(superheroId));
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheros", superheros);
        return "viewSightingsBySuperhero";
    }
}
