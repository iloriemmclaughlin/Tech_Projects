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
import com.irm.SuperheroSightings.entities.Organization;
import com.irm.SuperheroSightings.entities.Power;
import com.irm.SuperheroSightings.entities.Superhero;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
 *date: 1/20/2022
 *purpose: Superhero Sightings Project
 */

@Controller
public class SuperheroController {

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
    
    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();
    
    @GetMapping("superheros")
    public String displaySuperheros(Model model) {
       Superhero superhero = new Superhero();
       List<Superhero> superheros = superheroDao.getAllSuperheros();
       List<Power> powers = powerDao.getAllPowers();
       List<Organization> organizations = organizationDao.getAllOrganizations();
       model.addAttribute("superheros", superheros);
       model.addAttribute("powers", powers);
       model.addAttribute("organizations", organizations);
       model.addAttribute("errors", violations);
       model.addAttribute("superhero", superhero);
       return "superheros";
    }
    
    @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        return "superheroDetail";
    }
    
    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        superheroDao.deleteSuperheroById(id);
        return "redirect:/superheros";
    }
    
    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Power> powers = powerDao.getAllPowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("superhero", superhero);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "editSuperhero";
    }
    
    @PostMapping("addSuperhero")
    public String addSuperhero(@Valid Superhero superhero, BindingResult result, HttpServletRequest request, Model model) {        
        superhero.setName(request.getParameter("name"));
        superhero.setDescription(request.getParameter("description"));
        String[] powerIds = request.getParameterValues("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");
        
        List<Power> powers = new ArrayList<>();
        if(powerIds != null) {
            for(String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        } else {
            FieldError error = new FieldError("superhero", "powers", "Must include one power");
            result.addError(error);
        }
        
        superhero.setPowers(powers);
        
        List<Organization> organizations = new ArrayList<>();
        if(organizationIds != null) {
            for(String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("superhero", "organizations", "Must include one organization");
            result.addError(error);
        }
        
        superhero.setOrganizations(organizations);
        
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        
        if(result.hasErrors()) {
            model.addAttribute("superheros", superheros);
            model.addAttribute("powers", powerDao.getAllPowers());
            model.addAttribute("organizations", organizationDao.getAllOrganizations());
            model.addAttribute("superhero", superhero);
            return "superheros";
        } else {
            superheroDao.addSuperhero(superhero);
        }
        
        
        return "redirect:/superheros";
    }
    
    @PostMapping("editSuperhero")
    public String performEditSuperhero(@Valid Superhero superhero, BindingResult result, HttpServletRequest request, Model model) {
        String[] powerIds = request.getParameterValues("powerId");
        String[] organizationIds = request.getParameterValues("organizationId");
        
        List<Power> powers = new ArrayList<>();
        if(powerIds != null) {
            for(String powerId : powerIds) {
                powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
            }
        } else {
            FieldError error = new FieldError("superhero", "powers", "Must include one power");
            result.addError(error);
        }
        
        superhero.setPowers(powers);
        
        List<Organization> organizations = new ArrayList<>();
        if(organizationIds != null) {
            for(String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("superhero", "organizations", "Must include one organization");
            result.addError(error);
        }
        
        superhero.setOrganizations(organizations);
        
        if(result.hasErrors()) {
            model.addAttribute("powers", powerDao.getAllPowers());
            model.addAttribute("organizations", organizationDao.getAllOrganizations());
            model.addAttribute("superhero", superhero);
            return "editSuperhero";
        }
        
        superheroDao.updateSuperhero(superhero);
        
        return "redirect:/superheros";
    }
    
}
