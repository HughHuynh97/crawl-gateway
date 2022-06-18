package com.crawl.model.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopeeShopDetailCrawler {

    @JsonProperty("data")
    private Data data;
    @lombok.Data
    public static class Data {
        @JsonProperty("shopid")
        private Long shopId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("account")
        private Account account;
        @JsonProperty("cover")
        private String background;
        @JsonProperty("shop_location")
        private String shopLocation;
        @JsonProperty("rating_star")
        private String ratingStar;
        @JsonProperty("rating_good")
        private String ratingGood;
        @JsonProperty("rating_normal")
        private String ratingNormal;
        @JsonProperty("rating_bad")
        private String ratingBad;
        @JsonProperty("item_count")
        private Long totalProduct;
        @JsonProperty("follower_count")
        private Long follower;
        @JsonProperty("description")
        private String description;
        @JsonProperty("country")
        private String country;
        @JsonProperty("status")
        private Integer status;
    }
    @lombok.Data
    public static class Account {
        @JsonProperty("userid")
        private Long userId;
        @JsonProperty("username")
        private String username;
        @JsonProperty("portrait")
        private String avatar;
    }
}
