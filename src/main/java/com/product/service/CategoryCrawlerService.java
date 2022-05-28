package com.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.dao.CategoryDao;
import com.product.dao.CrawlQueueDao;
import com.product.model.CategoryResponse;
import com.product.model.CrawlQueue;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static com.product.utils.Constant.DONE;
import static com.product.utils.Constant.objectMapper;

@Service
@Log
public class CategoryCrawlerService {

    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CrawlQueueDao crawlQueueDao;

    public void doCrawlCategory(CrawlQueue queue, String response) throws JsonProcessingException {
        var listCategory = objectMapper.readValue(response, CategoryResponse.class);
        categoryDao.add(listCategory.data.getCategoryLists());
        crawlQueueDao.updateByStatus(queue, DONE);
    }
}
