package com.crawl.model.shopee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopeeItemDetailCrawler {
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
        private String ctime;
        @JsonProperty("discount")
        private String discount;
        @JsonProperty("stock")
        private Long stock;
        @JsonProperty("categories")
        private List<Category> categories;
        @JsonProperty("description")
        private String description;
        @JsonProperty("item_status")
        private String itemStatus;
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("liked_count")
        private Long likeCount;
        @JsonProperty("item_rating")
        private ItemRating itemRating;
        @JsonProperty("attributes")
        private List<Attribute> attributes;
        @JsonProperty("tier_variations")
        private List<Sku> sku;
    }
    @Data
    public static class ItemRating {
        @JsonProperty("rating_count")
        private List<Long> ratingCount;
        @JsonProperty("rating_star")
        private BigDecimal ratingStar;
    }

    @Data
    public static class Attribute {
        @JsonProperty("name")
        private String name;
        @JsonProperty("value")
        private String value;
    }

    @Data
    private static class Sku {
        @JsonProperty("images")
        private List<String> images;
        @JsonProperty("name")
        private String name;
        @JsonProperty("options")
        private List<String> options;
    }

    @Data
    public static class Category {
        @JsonProperty("catid")
        private Long catId;
    }
}
