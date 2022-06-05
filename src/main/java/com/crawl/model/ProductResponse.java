package com.crawl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

    private List<Product> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        @JsonProperty("itemid")
        private Long productId;
        @JsonProperty("shopid")
        private Long shopId;
        @JsonProperty("item_basic")
        private ProductDetail productDetail;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDetail {
        @JsonProperty("name")
        private String name;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("image")
        private String image;
        @JsonProperty("images")
        private List<String> images;
        @JsonProperty("price")
        private Long price;
        @JsonProperty("price_max")
        private Long minPrice;
        @JsonProperty("price_min")
        private Long maxPrice;
        @JsonProperty("price_before_discount")
        private Long priceBeforeDiscount;
        @JsonProperty("price_max_before_discount")
        private Long priceMaxBeforeDiscount;
        @JsonProperty("price_min_before_discount")
        private Long priceMinBeforeDiscount;
        @JsonProperty("shop_location")
        private String shopLocation;
        @JsonProperty("ctime")
        private Long ctime;
        @JsonProperty("discount")
        private String discount;
        @JsonProperty("catid")
        private Long catId;
        @JsonProperty("status")
        private Integer status;
    }
}
