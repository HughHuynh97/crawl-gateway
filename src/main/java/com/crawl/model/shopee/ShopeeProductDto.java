package com.crawl.model.shopee;

import lombok.Data;

import java.util.Date;

@Data
public class ShopeeProductDto {
    private Long prodId;
    private Long shopId;
    private String name;
    private String currency;
    private String image;
    private String images;
    private Long price;
    private Long minPrice;
    private Long maxPrice;
    private Long priceBeforeDiscount;
    private Long priceMaxBeforeDiscount;
    private Long priceMinBeforeDiscount;
    private String shopLocation;
    private String description;
    private Long stock;
    private String discount;
    private Date ctime;
    private Integer status;
    private String rating;
    private Long likeCount;
    private String attributes;
    private String sku;
}
