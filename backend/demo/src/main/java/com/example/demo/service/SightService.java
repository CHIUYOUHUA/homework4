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

    // 儲存多筆景點
    public void saveAll(List<Sight> sights) {
        sightRepository.saveAll(sights);
    }

    // 取得所有景點
    public List<Sight> getAll() {
        return sightRepository.findAll();
    }

    // 依區域取得景點
    public List<Sight> getByZone(String zone) {
        return sightRepository.findByZone(zone);
    }

    // 新增：清空所有景點資料
    public void clearAll() {
        sightRepository.deleteAll();
    }
}
