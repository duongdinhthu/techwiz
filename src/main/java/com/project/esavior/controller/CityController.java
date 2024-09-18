package com.project.esavior.controller;

import com.project.esavior.model.City;
import com.project.esavior.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    @Autowired
    private CityService cityService;
    @GetMapping
    public List<City> getAll() {
        return cityService.findAll();
    }
}
