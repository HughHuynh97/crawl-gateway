package com.product.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.product.utils.Constant.objectMapper;

@Data
public class CrawlProductQueue {
    private Long productQueueId;
    private Request request;

    private String cause;
    private String status;

    public CrawlProductQueue(ResultSet rs) throws SQLException {
        this.productQueueId = rs.getLong("product_queue_id");
        this.cause = rs.getString("cause");
        try {
            this.request = objectMapper.readValue(rs.getString("request"), Request.class) ;
        } catch (Exception ex) {
            this.request = null;
        }
        this.status = rs.getString("status");
    }

    @Data
    @AllArgsConstructor
    public static class Request {
        private Long catId;
        private int limit;
        private int newest;
    }
}
