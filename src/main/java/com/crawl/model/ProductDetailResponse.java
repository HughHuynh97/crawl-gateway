package com.crawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponse {
    @JsonProperty("error_msg")
    private String errorMsg;
    private Detail data;

    @Data
    public static class Detail {
        @JsonProperty("item_id")
        private Long itemId;
        @JsonProperty("shop_id")
        private Long shopId;
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
        @JsonProperty("stock")
        private Long stock;
        @JsonProperty("catid")
        private Long catId;
        @JsonProperty("item_status")
        private String itemStatus;
        @JsonProperty("status")
        private Integer status;
    }
}
