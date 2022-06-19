package com.crawl.service.impl;

import com.crawl.dao.CategoryDao;
import com.crawl.model.CrawlQueue;
import com.crawl.model.tiki.TikiCategoryDto;
import com.crawl.model.tiki.TikiHomeCategoryCrawler;
import com.crawl.service.CrawlService;
import com.crawl.service.MapperService;
import com.crawl.utils.HttpUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

@Service
@Log
public class TikiCrawlService implements CrawlService {
    private static final String GET_CATEGORY_BY_ID = "https://tiki.vn/api/v2/categories/";
    private static final String GET_PRODUCT_BY_CAT = "https://tiki.vn/api/personalish/v1/blocks/listings?limit=48&category=24238&page=2";
    private static final String GET_PRODUCT_DETAIL = "https://tiki.vn/api/v2/products/159004131?platform=web";
    private static final String GET_SHOP_DETAIL = "https://tiki.vn/api/shopping/v2/widgets/seller?seller_id=93803&mpid=172709165&spid=172709167&platform=desktop";
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private MapperService mapperService;
    @PostConstruct
    public void initCategory() throws IOException {
        var html = HttpUtil.get("http://tiki.vn");
        var jsoup = Jsoup.parse(html);
        var footerCatElements = jsoup.select(".styles__StyledColumns-sc-17y817k-2");
        var catLinks = footerCatElements.get(0).select("a");
        for (Element link : catLinks) {
            var catId = link.attr("href").split("/")[link.attr("href").split("/").length - 1].replace("c", "");
            var response = HttpUtil.get(GET_CATEGORY_BY_ID + catId, Map.of("include", "ancestors"), null);
            var category = mapperService.readValue(response, TikiHomeCategoryCrawler.class);
            var categoryDto = new TikiCategoryDto();
            if (category.getId() != null) {
                log.log(Level.INFO, "TikiCrawlService >> Start Sync Category >> {0}", category.getId());
                categoryDto.setCatId(category.getId());
                categoryDto.setName(category.getName());
                categoryDto.setLevel(category.getLevel());
                categoryDto.setUrl(categoryDto.getUrl());
                categoryDto.setProductCont(category.getProductCont());
                categoryDto.setDescription(category.getDescription());
                categoryDto.setImage(category.getImage());
                categoryDto.setStatus(categoryDto.getStatus());
                categoryDao.add(categoryDto);
            }
        }
    }
    @Override
    public void doCrawJob(CrawlQueue queue) {

    }
}
