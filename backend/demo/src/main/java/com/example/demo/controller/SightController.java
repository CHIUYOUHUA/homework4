package com.example.demo.controller;

import com.example.demo.model.Sight;
import com.example.demo.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/sights")
public class SightController {

    @Autowired
    private SightService sightService;

    // GET http://localhost:8080/api/sights/all
    @GetMapping("/all")
    public List<Sight> getAllSights() {
        return sightService.getAll();  // ← 修改這行
    }

    // GET http://localhost:8080/api/sights?zone=安樂區
    @GetMapping
    public List<Sight> getSightsByZone(@RequestParam String zone) {
        return sightService.getByZone(zone);  // ← 修改這行
    }
}
