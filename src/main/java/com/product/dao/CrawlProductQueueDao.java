package com.product.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.model.CrawlProductQueue;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static com.product.utils.Constant.*;

@Service
@Log
public class CrawlProductQueueDao {

    @Autowired
    private JdbcTemplate jdbc;

    public List<CrawlProductQueue> getByStatus(String status) {
        return jdbc.query(GET_PRODUCT_QUEUE_BY_STATUS, (rs, i) -> new CrawlProductQueue(rs), status);
    }

    public void add(Long catId, Long queueId)  {
        try {
            CrawlProductQueue.Request request = new CrawlProductQueue.Request(catId, 60, 0);
            jdbc.update(INSERT_PRODUCT_QUEUE, queueId, objectMapper.writeValueAsString(request), NEW);
        } catch (JsonProcessingException e) {
            log.log(Level.WARNING, "CrawlProductQueueDao >> add >> ", e);
        }
    }
}
