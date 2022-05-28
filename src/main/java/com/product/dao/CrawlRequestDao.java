package com.product.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static com.product.utils.Constant.*;

@Service
public class CrawlRequestDao {

    @Autowired
    private JdbcTemplate jdbc;

    public String getRequestUrlByCode(String code) {
        return jdbc.queryForObject(GET_REQUEST_URL_BY_CODE, String.class, code);
    }
}
