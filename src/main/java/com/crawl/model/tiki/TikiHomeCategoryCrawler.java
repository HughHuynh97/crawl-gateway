package com.crawl.model.tiki;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TikiHomeCategoryCrawler {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("product_count")
    private Long productCont;
    @JsonProperty("meta_description")
    private String description;
    @JsonProperty("thumbnail_url")
    private String image;
    @JsonProperty("status")
    private String status;
}
