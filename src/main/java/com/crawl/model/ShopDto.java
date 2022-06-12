package com.crawl.model;

import lombok.Data;

@Data
public class ShopDto {
    private Long shopId;
    private Long userId;
    private String provider;
    private String name;
    private String username;
    private String avatar;
    private String background;
    private String shopLocation;
    private String ratingStar;
    private String ratingGood;
    private String ratingNormal;
    private String ratingBad;
    private Long totalProduct;
    private Long follower;
    private String description;
    private String country;
    private Integer status;
}
