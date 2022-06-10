package com.crawl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private List<Product> items;

    @Data
    public static class Product {
        @JsonProperty("itemid")
        private Long productId;
        @JsonProperty("shopid")
        private Long shopId;
    }
}
