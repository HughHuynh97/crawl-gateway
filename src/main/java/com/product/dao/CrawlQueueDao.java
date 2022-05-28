package com.product.dao;

import com.product.model.CrawlQueue;
import com.product.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.product.utils.Constant.*;

@Service
public class CrawlQueueDao {
    @Autowired
    private JdbcTemplate jdbc;

    public List<CrawlQueue> getByStatus(String status) {
        return jdbc.query(GET_CRAWL_QUEUE_BY_STATUS, (rs, i) -> new CrawlQueue(rs), status);
    }

    public void insertQueueByTargetDate(String targetDate) {
        jdbc.update(INSERT_QUEUE_BY_TARGET_DATE, targetDate);
    }

    public void updateByStatus(CrawlQueue queue, String status) {
        jdbc.update(UPDATE_QUEUE_BY_STATUS, status, queue.getCause(), queue.getQueueId());
    }

}
