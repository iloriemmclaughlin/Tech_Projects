/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.irm.SuperheroSightings.dao;

import com.irm.SuperheroSightings.entities.Location;
import com.irm.SuperheroSightings.entities.Superhero;
import java.util.List;

/**
 *@author Iloriem McLaughlin
 *email: iloriem.pena@gmail.com
 *date: 1/18/2022
 *purpose: Superhero Sightings Project
 */

public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);
    
    List<Location> getLocationsForSuperhero(Superhero superhero);
}
