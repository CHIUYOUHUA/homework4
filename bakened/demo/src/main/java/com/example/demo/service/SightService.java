package com.example.demo.service;

import com.example.demo.model.Sight;
import com.example.demo.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SightService {

    @Autowired
    private SightRepository sightRepository;

    public void saveAll(List<Sight> sights) {
        sightRepository.saveAll(sights);
    }

    public List<Sight> getAll() {
        return sightRepository.findAll();
    }

    public List<Sight> getByZone(String zone) {
        return sightRepository.findByZone(zone);
    }
}
