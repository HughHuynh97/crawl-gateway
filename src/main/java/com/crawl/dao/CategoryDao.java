package com.crawl.dao;

import com.crawl.model.shopee.ShopeeCategoryDto;
import com.crawl.model.tiki.TikiCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CategoryDao {

    private static final String INSERT_SHOPEE_CATEGORY = """
            INSERT INTO shopee_category(cat_id, name, code, image, parent_cat_id) VALUES (?,?,?,?,?)
            ON DUPLICATE KEY UPDATE cat_id = VALUES(cat_id), code = VALUES(code), name = VALUES(name),
            image = VALUES(image), parent_cat_id = VALUES(parent_cat_id)
            """;
    private static final String INSERT_TIKI_CATEGORY = """
            INSERT INTO tiki_category(cat_id, name, url, image, status, product_count, level, parent_id)
            VALUES(?,?,?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE name = VALUES(name), url = VALUES(url), image = VALUES(image),
            status = VALUES(status), product_count = VALUES(product_count), level = VALUES(level), parent_id = VALUES(parent_id)
            """;
    private static final String GET_SHOPEE_CATEGORY_BY_PARENT = """
            SELECT cat_id, name, code, parent_cat_id FROM shopee_category WHERE parent_cat_id = ?
            """;
    private static final String GET_SHOPEE_CATEGORY_BY_ID = """
            SELECT cat_id, name, code, parent_cat_id FROM shopee_category WHERE cat_id IN (:catIds)
            """;
    private static final String UPDATE_TOTAL_ITEM_BY_SHOPEE_CAT_ID = """
            UPDATE shopee_category SET total_count = ? WHERE cat_id = ?
            """;
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private NamedParameterJdbcTemplate nameJdbc;

    public void add(ShopeeCategoryDto item) {
        var param = new Object[]{item.getCatId(), item.getName(), item.getCode(), item.getImage(), item.getParentId()};
        jdbc.update(INSERT_SHOPEE_CATEGORY, param);
    }

    public void add(TikiCategoryDto item) {
        var param = new Object[]{item.getCatId(), item.getName(), item.getUrl(), item.getImage(), item.getStatus(), item.getProductCont(), item.getLevel(), item.getParentId()};
        jdbc.update(INSERT_TIKI_CATEGORY, param);
    }

    public List<ShopeeCategoryDto> getCategoryById(List<String> catIds) {
        return nameJdbc.query(GET_SHOPEE_CATEGORY_BY_ID, Map.of("catIds", catIds), (rs, i) -> new ShopeeCategoryDto(rs));
    }

    public List<ShopeeCategoryDto> getCategoryByParentId(Long parentId) {
        return jdbc.query(GET_SHOPEE_CATEGORY_BY_PARENT, (rs, i) -> new ShopeeCategoryDto(rs), parentId);
    }

    public void updateTotalItemByShopeeCatId(Long totalCount, Long catId) {
        jdbc.update(UPDATE_TOTAL_ITEM_BY_SHOPEE_CAT_ID, totalCount, catId);
    }
}
