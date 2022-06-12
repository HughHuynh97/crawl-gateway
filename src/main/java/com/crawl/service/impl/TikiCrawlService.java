package com.crawl.service.impl;

import com.crawl.model.CrawlQueue;
import com.crawl.service.CrawlService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class TikiCrawlService implements CrawlService {
    private static final String GET_PRODUCT_BY_CAT = "https://tiki.vn/api/personalish/v1/blocks/listings?limit=48&category=24238&page=2";
    private static final String GET_PRODUCT_DETAIL = "https://tiki.vn/api/v2/products/172709165?platform=web&spid=172709167";
    private static final String GET_SHOP_DETAIL = "https://tiki.vn/api/shopping/v2/widgets/seller?seller_id=93803&mpid=172709165&spid=172709167&platform=desktop";
    @Override
    public void doCrawJob(CrawlQueue queue) {

    }
}
