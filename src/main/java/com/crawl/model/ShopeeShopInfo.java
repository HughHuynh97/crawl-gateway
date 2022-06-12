package com.crawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShopeeShopInfo {
    @JsonProperty("data")
    private Account data;
    @Data
    public static class Account {
        @JsonProperty("username")
        private String username;
    }
}
