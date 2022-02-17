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
import com.irm.SuperheroSightings.entities.Power;
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
 *date: 1/23/2021
 *purpose: Superhero Sightings Project
 */

@Controller
public class PowerController {
    
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
    
    Set<ConstraintViolation<Power>> violations = new HashSet<>();
    
    @GetMapping("powers")
    public String displayPowers(Model model) {
        Power power = new Power();
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("errors", violations);
        model.addAttribute("power", power);
        return "powers";
    }
    
    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        powerDao.deletePowerById(id);
        return "redirect:/powers";
    }
    
    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);
        
        model.addAttribute("power", power);
        return "editPower";
    }
    
    @PostMapping("addPower")
    public String addPower(@Valid Power power, BindingResult result, HttpServletRequest request, Model model) {
        power.setName(request.getParameter("name"));
        List<Power> powers = powerDao.getAllPowers();
        
        if(result.hasErrors()) {
            model.addAttribute("powers", powers);
            model.addAttribute("power", power);
            return "powers";
        } else {
            powerDao.addPower(power);
        }
        
        return "redirect:/powers";
    }
    
    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result) {
        if(result.hasErrors()) {
            return "editPower";
        }
        
        powerDao.updatePower(power); 
        return "redirect:/powers";
    }

}
