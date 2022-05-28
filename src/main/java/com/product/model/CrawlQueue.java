package com.product.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CrawlQueue {
    private Long queueId;
    private String targetDate;
    private String cause;
    private String status;

    public CrawlQueue(ResultSet rs) throws SQLException {
        this.queueId = rs.getLong("queue_id");
        this.targetDate = rs.getString("target_date");
        this.cause = rs.getString("cause");
        this.status = rs.getString("status");
    }
}
