package com.crawl.utils;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Constant {
    private Constant() {}

    public static final ExecutorService executorService = Executors.newCachedThreadPool();
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
