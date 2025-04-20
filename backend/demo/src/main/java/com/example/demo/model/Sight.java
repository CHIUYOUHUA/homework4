package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sights") // 對應到 MongoDB 中的 "sights" collection
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sight {

    @Id
    private String id;           // MongoDB 的 _id（自動產生）

    private String sightName;   // 景點名稱
    private String zone;        // 所在區域
    private String category;    // 分類
    private String photoUrl;    // 圖片網址
    private String description; // 描述
    private String address;     // 地址

    // 自訂 Constructor（可保留）
    public Sight(String sightName, String photoUrl) {
        this.sightName = sightName;
        this.photoUrl = photoUrl;
    }
}
