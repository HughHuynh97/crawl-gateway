package com.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private Long productId;
        private Long shopId;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDetail {
        private String name;
        private String currency;
        private String image;
        private Long price;
        private Long minPrice;
        private Long maxPrice;
        private String shopLocation;
        private int status;
    }
}
