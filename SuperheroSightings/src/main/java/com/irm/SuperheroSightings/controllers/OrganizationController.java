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
import com.irm.SuperheroSightings.entities.Superhero;
import java.util.ArrayList;
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
 *purpose: Superhero Sightings Project
 */

@Controller
public class OrganizationController {
    
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
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        Organization organization = new Organization();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("organizations", organizations);
        model.addAttribute("superheros", superheros);
        model.addAttribute("errors", violations);
        model.addAttribute("organization", organization);
        return "organizations";
    }
    
    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        organization.setName(request.getParameter("name"));
        organization.setDescription(request.getParameter("description"));
        organization.setCity(request.getParameter("city"));
        organization.setCountry(request.getParameter("country"));
        organization.setPhone(request.getParameter("phone"));
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        if(result.hasErrors()) {
            model.addAttribute("organizations", organizations);
            model.addAttribute("organization", organization);
            return "organizations";
        } else {
            organizationDao.addOrganization(organization);
        }
        
        return "redirect:/organizations";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result) {
        if(result.hasErrors()) {
            return "editOrganization";
        }
        organizationDao.updateOrganization(organization);
        return "redirect:/organizations";
    }

}
