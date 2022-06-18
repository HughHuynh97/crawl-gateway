package com.crawl.model.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopeeShopInfoCrawler {
    @JsonProperty("data")
    private Account data;
    @Data
    public static class Account {
        @JsonProperty("name")
        private String username;
    }
}
