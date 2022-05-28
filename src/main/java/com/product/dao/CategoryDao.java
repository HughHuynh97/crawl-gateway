package com.product.dao;

import com.product.model.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.product.utils.Constant.INSERT_CATEGORY;

@Service
public class CategoryDao {

    @Autowired
    private JdbcTemplate jdbc;

    public void add(List<CategoryResponse.CategoryList> list) {
        var params = list.stream().map(item -> new Object[] {
                item.getCatId(), item.getName(), item.getDisplayName(), item.getImage(), item.getLevel(), item.getParentCatId()
        }).collect(Collectors.toList());
        jdbc.batchUpdate(INSERT_CATEGORY, params);
    }
}
