package com.crawl.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Constant {
    private Constant() {}

    public static final ExecutorService executorService = Executors.newCachedThreadPool();
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final String NEW = "NEW";
    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String DONE = "DONE";
    public static final String FAILED = "FAILED";
    public static final String GET_ALL_CATEGORY = "GET_ALL_CATEGORY";
    public static final String GET_PRODUCT_BY_CAT = "GET_PRODUCT_BY_CAT";
    public static final String GET_PRODUCT_DETAIL = "GET_PRODUCT_DETAIL";

    public static final String GET_REQUEST_URL_BY_CODE = """
            SELECT url FROM crawl_request WHERE code = ?
            """;
    public static final String GET_CRAWL_QUEUE_BY_STATUS = """
            SELECT queue_id, start_date, end_date, cause, status FROM crawl_queue WHERE status = ?
            """;

    public static final String INSERT_QUEUE_BY_TARGET_DATE = """
            insert into crawl_queue(start_date, end_date, cause, status) values (?,?, '', 'NEW');
            """;
    public static final String UPDATE_QUEUE_BY_STATUS = """
            update crawl_queue set status = ?, cause = ? WHERE queue_id = ?
            """;
    public static final String INSERT_CATEGORY = """
            INSERT INTO category(cat_id, code, name, image, level, parent_cat_id) VALUES (?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE cat_id = VALUES(cat_id), code = VALUES(code), name = VALUES(name),
            image = VALUES(image), level = VALUES(level), parent_cat_id = VALUES(parent_cat_id)
            """;
}
