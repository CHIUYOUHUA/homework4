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
        return sightService.getAll();
    }

    // GET http://localhost:8080/api/sights?zone=安樂區
    @GetMapping
    public List<Sight> getSightsByZone(@RequestParam String zone) {
        return sightService.getByZone(zone);
    }

    // GET http://localhost:8080/api/sights/clear
    @GetMapping("/clear")
    public String clearAllSights() {
        sightService.clearAll();
        return "所有景點資料已清空！";
    }

    // GET http://localhost:8080/api/sights/reset
    @GetMapping("/reset")
    public String resetSights() {
        // 1. 清空資料
        sightService.clearAll();

        // 2. 重新抓取資料
        List<Sight> newData = sightService.fetchAndSaveSights();
        // fetchAndSaveSights() 需要在 SightService 實作，抓資料並存回 MongoDB

        return "資料已清空並重新抓取完成！共抓取 " + newData.size() + " 筆資料。";
    }
}
