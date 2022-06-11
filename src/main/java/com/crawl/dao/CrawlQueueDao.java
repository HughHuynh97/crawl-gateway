package com.crawl.dao;

import com.crawl.model.CrawlQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlQueueDao {

    public static final String GET_CRAWL_QUEUE_BY_STATUS = """
            SELECT queue_id, request, cause, status FROM crawl_queue WHERE status = ?
            """;
    public static final String INSERT_QUEUE_BY_TARGET_DATE = """
            INSERT INTO crawl_queue(request, cause, status) values (?, '', 'NEW');
            """;
    public static final String UPDATE_QUEUE_BY_STATUS = """
            UPDATE crawl_queue set status = ?, cause = ? WHERE queue_id = ?
            """;
    @Autowired
    private JdbcTemplate jdbc;

    public List<CrawlQueue> getByStatus(String status) {
        return jdbc.query(GET_CRAWL_QUEUE_BY_STATUS, (rs, i) -> new CrawlQueue(rs), status);
    }

    public void addByTargetDate(String requet) {
        jdbc.update(INSERT_QUEUE_BY_TARGET_DATE, requet);
    }

    public void updateByStatus(CrawlQueue queue, String status) {
        jdbc.update(UPDATE_QUEUE_BY_STATUS, status, queue.getCause(), queue.getQueueId());
    }

}
