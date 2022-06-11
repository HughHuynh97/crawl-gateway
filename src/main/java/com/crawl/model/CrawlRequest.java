package com.crawl.model;

import lombok.Data;

import java.util.List;

@Data
public class CrawlRequest {
    private String pageFrom;
    private String pageTo;
    private List<String> catIds;
    private String provider;
}
