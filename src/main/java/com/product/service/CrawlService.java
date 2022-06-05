package com.product.service;

import com.product.dao.CrawlDao;
import com.product.model.CategoryResponse;
import com.product.model.ProductResponse;
import com.product.utils.HttpUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log
public class CrawlService {

    @Autowired
    private CrawlDao crawlDao;

    @Autowired
    private MapperService mapperService;

    public void doCrawlCategory(String response) {
        var listCategory = mapperService.readValue(response, CategoryResponse.class);
        insertDB(listCategory.getData().getCategoryLists());
    }

    private void insertDB(List<CategoryResponse.CategoryItem> categoryItems) {
        categoryItems.forEach(item -> {
            crawlDao.add(item);
            if (item.getChildren() != null) {
                var response = HttpUtil.get("https://shopee.vn/api/v4/search/search_items",
                        Map.of("match_id", item.getCatId().toString(), "order", "DESC", "limit", "60", "newest", "0"),null);
                var product = mapperService.readValue(response, ProductResponse.class);
                insertProductDb(product);
                insertDB(item.getChildren());
            }
        });
    }

    private void insertProductDb(ProductResponse productResponse) {

    }
}
