package com.example.demo.crawler;

import com.example.demo.model.Sight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeelungSightsCrawler {

    private static final String BASE_URL = "https://www.travelking.com.tw";
    private static final String KEELUNG_URL = BASE_URL + "/tourguide/taiwan/keelungcity/";

    // 🔹 抓取指定區域景點（保留原有功能）
    public List<Sight> getItems(String targetZone) {
        List<Sight> sights = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(KEELUNG_URL).userAgent("Mozilla/5.0").get();
            Elements sightLinks = doc.select(".box a");

            for (Element link : sightLinks) {
                String sightName = link.text();
                String sightUrl = BASE_URL + link.attr("href");

                Sight sight = parseSightDetail(sightName, sightUrl);

                // ✅ 若符合指定區域，加入清單
                if (sight.getZone().equals(targetZone)) {
                    sights.add(sight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sights;
    }

    // ✅ 抓取基隆所有景點（新增）
    public List<Sight> getAllSights() {
        List<Sight> sights = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(KEELUNG_URL).userAgent("Mozilla/5.0").get();
            Elements sightLinks = doc.select(".box a");

            for (Element link : sightLinks) {
                String sightName = link.text();
                String sightUrl = BASE_URL + link.attr("href");

                Sight sight = parseSightDetail(sightName, sightUrl);

                // ✅ 避免無效資料
                if (sight.getZone() != null && !sight.getZone().equals("未知")) {
                    sights.add(sight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sights;
    }

    // 🔍 爬取景點詳細資訊
    private Sight parseSightDetail(String sightName, String sightUrl) {
        try {
            Document detailDoc = Jsoup.connect(sightUrl).userAgent("Mozilla/5.0").get();
            Sight sight = new Sight(sightName, sightUrl);

            // 1. 地區
            Elements zoneElements = detailDoc.select(".bc_last");
            String zone = zoneElements.isEmpty() ? "未知" : zoneElements.text().trim();
            sight.setZone(zone);

            // 2. 地址
            Elements addressElements = detailDoc.select("span[property=vcard:street-address]");
            String address = addressElements.isEmpty() ? "無地址資訊" : addressElements.text().trim();
            sight.setAddress(address);

            // 3. 描述
            Elements descElements = detailDoc.select("div.text");
            String description = descElements.isEmpty() ? "無描述" : descElements.text().trim();
            sight.setDescription(description);

            // 4. 分類
            Elements categoryElements = detailDoc.select("span[property=rdfs:label]");
            String category = categoryElements.isEmpty() ? "無類別" : categoryElements.text().trim();
            sight.setCategory(category);

            // ✅ 5. 照片網址（允許為空字串，避免遺失資料）
            Elements imgElements = detailDoc.select("meta[itemprop=image]");
            String photoUrl = imgElements.isEmpty() ? "" : imgElements.attr("abs:content");
            sight.setPhotoUrl(photoUrl);

            return sight;

        } catch (IOException e) {
            System.out.println("❌ 解析失敗：" + sightUrl);
            return new Sight(sightName, sightUrl); // 最少回傳名稱和網址
        }
    }
}
