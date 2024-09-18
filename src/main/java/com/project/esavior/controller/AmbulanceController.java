package com.project.esavior.controller;

import com.project.esavior.model.Ambulance;
import com.project.esavior.service.AmbulanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
public class AmbulanceController {

    @Autowired
    private AmbulanceService ambulanceService;

    @GetMapping
    public List<Ambulance> getAllAmbulances() {
        return ambulanceService.getAllAmbulances();
    }

    @GetMapping("/{id}")
    public Ambulance getAmbulanceById(@PathVariable Integer id) {
        return ambulanceService.getAmbulanceById(id);
    }

    @PostMapping
    public Ambulance createAmbulance(@RequestBody Ambulance ambulance) {
        return ambulanceService.saveAmbulance(ambulance);
    }

    @DeleteMapping("/{id}")
    public void deleteAmbulance(@PathVariable Integer id) {
        ambulanceService.deleteAmbulance(id);
    }
}
