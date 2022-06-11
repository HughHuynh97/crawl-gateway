package com.crawl.dao;

import com.crawl.model.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CategoryDao {

    private static final String INSERT_CATEGORY = """
            INSERT INTO category(cat_id, name, code, image, parent_cat_id, provider) VALUES (?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE cat_id = VALUES(cat_id), code = VALUES(code), name = VALUES(name),
            image = VALUES(image), parent_cat_id = VALUES(parent_cat_id), provider = VALUES(provider)
            """;
    private static final String GET_CATEGORY_BY_PARENT = """
            SELECT cat_id, name, code, parent_cat_id FROM category WHERE parent_cat_id = ?
            """;
    private static final String GET_CATEGORY_BY_ID = """
            SELECT cat_id, name, code, parent_cat_id FROM category WHERE cat_id IN (:catIds)
            """;
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private NamedParameterJdbcTemplate nameJdbc;

    public void add(CategoryDto item) {
        var param = new Object[]{item.getCatId(), item.getName(), item.getCode(), item.getImage(), item.getParentId(), item.getProvider()};
        jdbc.update(INSERT_CATEGORY, param);
    }

    public List<CategoryDto> getCategoryById(List<String> catIds) {
        return nameJdbc.query(GET_CATEGORY_BY_ID, Map.of("catIds", catIds), (rs, i) -> new CategoryDto(rs));
    }

    public List<CategoryDto> getCategoryByParentId(Long parentId) {
        return jdbc.query(GET_CATEGORY_BY_PARENT, (rs, i) -> new CategoryDto(rs), parentId);
    }
}
