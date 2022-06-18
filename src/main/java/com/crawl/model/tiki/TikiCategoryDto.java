package com.crawl.model.tiki;

import lombok.Data;

@Data
public class TikiCategoryDto {
    private Long catId;
    private String name;
    private String url;
    private String image;
    private Integer level;
    private String status;
    private Long productCont;
    private String description;
    private Long parentId;
}
