package com.crawl.service;

import com.crawl.model.CrawlQueue;

public interface CrawlService {

    void doCrawJob(CrawlQueue queue);
}
