package com.crawl.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CrawlQueue {
    private Long queueId;
    private String startDate;
    private String endDate;
    private String cause;
    private String status;

    public CrawlQueue(ResultSet rs) throws SQLException {
        this.queueId = rs.getLong("queue_id");
        this.startDate = rs.getString("start_date");
        this.startDate = rs.getString("start_date");
        this.startDate = rs.getString("start_date");
        this.endDate = rs.getString("end_date");
        this.cause = rs.getString("cause");
        this.status = rs.getString("status");
    }
}
