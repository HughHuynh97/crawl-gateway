package com.crawl.service;

import com.crawl.dao.CrawlDao;
import com.crawl.dao.CrawlRequestDao;
import com.crawl.model.CategoryResponse;
import com.crawl.model.ProductResponse;
import com.crawl.utils.HttpUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.crawl.utils.Constant.*;

@Service
@Log
public class CrawlService {

    @Autowired
    private CrawlDao crawlDao;

    @Autowired
    private MapperService mapperService;
    @Autowired
    private CrawlRequestDao crawlRequestDao;

    public void doCrawlCategory(String response) {
        var listCategory = mapperService.readValue(response, CategoryResponse.class);
        insertDB(listCategory.getData().getCategoryLists());
    }

    private void insertDB(List<CategoryResponse.CategoryItem> categoryItems) {
        categoryItems.forEach(item -> {
            crawlDao.add(item);
            if (item.getChildren() != null) {
                var response = HttpUtil.get(crawlRequestDao.getRequestUrlByCode(GET_PRODUCT_BY_CAT), Map.of("match_id", item.getCatId().toString(), "order", "DESC", "limit", "60", "newest", "0"),null);
                var product = mapperService.readValue(response, ProductResponse.class);
                insertProductDb(product);
                insertDB(item.getChildren());
            }
        });
    }

    private void insertProductDb(ProductResponse productResponse) {
        productResponse.getItems().forEach(product -> {
            var response = HttpUtil.get(crawlRequestDao.getRequestUrlByCode(GET_PRODUCT_DETAIL), Map.of("itemid", product.getProductId().toString(),"shopid", product.getShopId().toString()), null);
            System.out.println(response);
        });
    }
}
