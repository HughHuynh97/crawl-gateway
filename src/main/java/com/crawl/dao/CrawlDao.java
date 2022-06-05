package com.crawl.dao;

import com.crawl.model.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.crawl.utils.Constant.INSERT_CATEGORY;

@Service
public class CrawlDao {

    @Autowired
    private JdbcTemplate jdbc;

    public void add(CategoryResponse.CategoryItem item) {
        var param = new Object[] {item.getCatId(), item.getName(), item.getDisplayName(), item.getImage(), item.getLevel(), item.getParentCatId()};
        jdbc.update(INSERT_CATEGORY, param);
    }

    public void add(List<CategoryResponse.CategoryItem> list) {
        var params = list.stream().map(item -> new Object[] {
                item.getCatId(), item.getName(), item.getDisplayName(), item.getImage(), item.getLevel(), item.getParentCatId()
        }).collect(Collectors.toList());
        jdbc.batchUpdate(INSERT_CATEGORY, params);
    }
}
