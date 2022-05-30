package com.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse {
    @JsonProperty("data")
    private Data data;
    @JsonProperty("error")
    private int error;
    @JsonProperty("error_msg")
    private String errorMsg;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        @JsonProperty("category_list")
        private ArrayList<CategoryItem> categoryLists;
    }

    @lombok.Data
    public static class CategoryItem {
        @JsonProperty("catid")
        private Long catId;
        @JsonProperty("parent_catid")
        private Long parentCatId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("display_name")
        private String displayName;
        @JsonProperty("image")
        private String image;
        @JsonProperty("unselected_image")
        private String unselectedImage;
        @JsonProperty("selected_image")
        private String selectedImage;
        @JsonProperty("level")
        private int level;
        @JsonProperty("children")
        private List<CategoryItem> children;
        @JsonProperty("block_buyer_platform")
        private ArrayList<Integer> blockBuyerPlatform;
    }
}
