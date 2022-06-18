package com.crawl.model.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ShopeeItemCrawler {
    @JsonProperty("items")
    private List<Product> items;
    @JsonProperty("total_count")
    private Long totalCount;

    @Data
    public static class Product {
        @JsonProperty("itemid")
        private Long productId;
        @JsonProperty("shopid")
        private Long shopId;
        @JsonProperty("item_basic")
        private ItemBasic itemBasic;
    }

    @Data
    public static class ItemBasic {
        @JsonProperty("currency")
        private String currency;
    }
}
