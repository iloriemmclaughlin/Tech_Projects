/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.entities.Organization;
import com.irm.SuperheroSightings.entities.Power;
import com.irm.SuperheroSightings.entities.Superhero;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 1/18/2022
 *purpose: Superhero Sightings Project
 */

public interface SuperheroDao {
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperheros();
    Superhero addSuperhero(Superhero superhero);
    void updateSuperhero(Superhero superhero);
    void deleteSuperheroById(int id);
    
    List<Superhero> getSuperherosForPower(Power power);
    List<Superhero> getSuperherosForOrganization(Organization organization);

}
