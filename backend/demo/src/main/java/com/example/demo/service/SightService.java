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

    @Autowired
    private CrawlerService crawlerService; // 你的抓取資料 Service

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

    // 清空所有景點資料
    public void clearAll() {
        sightRepository.deleteAll();
    }

    // 新增：重新抓取資料並存回 MongoDB
    public List<Sight> fetchAndSaveSights() {
        // 1. 呼叫 CrawlerService 取得新資料
        List<Sight> newData = crawlerService.getAllSights();

        // 2. 存入 MongoDB
        sightRepository.saveAll(newData);

        // 3. 回傳抓取的資料（可用於回傳給 Controller）
        return newData;
    }
}
