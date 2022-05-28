package com.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    @JsonProperty("data")
    public Data data;
    @JsonProperty("error")
    public int error;
    @JsonProperty("error_msg")
    public String errorMsg;

    @lombok.Data
    public static class Data {
        @JsonProperty("category_list")
        public ArrayList<CategoryList> categoryLists;
    }

    @lombok.Data
    public static class CategoryList {
        @JsonProperty("catid")
        public int catId;
        @JsonProperty("parent_catid")
        public int parentCatId;
        @JsonProperty("name")
        public String name;
        @JsonProperty("display_name")
        public String displayName;
        @JsonProperty("image")
        public String image;
        @JsonProperty("unselected_image")
        public String unselectedImage;
        @JsonProperty("selected_image")
        public String selectedImage;
        @JsonProperty("level")
        public int level;
        @JsonProperty("children")
        public Object children;
        @JsonProperty("block_buyer_platform")
        public ArrayList<Integer> blockBuyerPlatform;
    }
}
