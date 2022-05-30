package com.product.service;

import com.product.dao.CategoryDao;
import com.product.dao.CrawlProductQueueDao;
import com.product.dao.CrawlRequestDao;
import com.product.model.CategoryResponse;
import com.product.model.CrawlQueue;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.product.utils.Constant.GET_PRODUCT_BY_CAT;
import static com.product.utils.Constant.objectMapper;

@Service
@Log
public class CategoryCrawlerService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CrawlRequestDao crawlRequestDao;
    @Autowired
    private CrawlProductQueueDao crawlProductQueueDao;

    public void doCrawlCategory(String response, CrawlQueue queue) throws Exception {
        var listCategory = objectMapper.readValue(response, CategoryResponse.class);
        insertDB(listCategory.getData().getCategoryLists(), queue);
    }

    private void insertDB(List<CategoryResponse.CategoryItem> categoryItems, CrawlQueue queue) {
        categoryItems.forEach(item -> {
            categoryDao.add(item);
            crawlProductQueueDao.add(item.getCatId(), queue.getQueueId());
            if (item.getChildren() != null) {
                insertDB(item.getChildren(), queue);
            }
        });
    }
}
