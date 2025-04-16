package com.example.demo.loader;

import com.example.demo.crawler.KeelungSightsCrawler;
import com.example.demo.model.Sight;
import com.example.demo.repository.SightRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final SightRepository sightRepository;
    private final KeelungSightsCrawler crawler;

    // 建構子注入
    public DataLoader(SightRepository sightRepository) {
        this.sightRepository = sightRepository;
        this.crawler = new KeelungSightsCrawler(); // 建立爬蟲實體
    }

    @Override
    public void run(ApplicationArguments args) {
        // 如果資料庫已經有資料，就不要重複爬蟲
        if (sightRepository.count() == 0) {
            System.out.println("開始爬蟲抓取基隆景點資料...");
            List<Sight> sights = crawler.getAllSights();
            sightRepository.saveAll(sights);
            System.out.println("已成功儲存 " + sights.size() + " 筆景點資料到 MongoDB！");
        } else {
            System.out.println("MongoDB 中已有景點資料，跳過爬蟲");
        }
    }
}
