package com.crawl.dao;

import com.crawl.model.ProductDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;


@Repository
@Log
public class ProductDao {

    private static final String INSERT_PRODUCT_BY_PROVIDER = """
            INSERT INTO product (prod_id, shop_id, name, currency, image, images, price, min_price, max_price, price_before_discount, price_max_before_discount, price_min_before_discount, shop_location, description, stock, discount, provider_created_time, status, provider)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE name=VALUES(name), currency=VALUES(currency), image=VALUES(images)
            """;
    private static final String MAP_CATEGORY = """
            INSERT INTO category_product(cat_id, prod_id) VALUES (?,?)
            ON DUPLICATE KEY UPDATE cat_id=VALUES(cat_id), prod_id=VALUES(prod_id)
            """;
    @Autowired
    private JdbcTemplate jdbc;

    public void add(ProductDto dto) {
        try {
            var param = new Object[]{
                    dto.getProdId(), dto.getShopId(), dto.getName(), dto.getCurrency(),
                    dto.getImage(), dto.getImages(), dto.getPrice(), dto.getMinPrice(),
                    dto.getMaxPrice(), dto.getPriceBeforeDiscount(), dto.getPriceMaxBeforeDiscount(), dto.getPriceMinBeforeDiscount(),
                    dto.getShopLocation(), dto.getDescription(), dto.getStock(), dto.getDiscount(),
                    dto.getCtime(), dto.getStatus(), dto.getProvider()
            };
            jdbc.update(INSERT_PRODUCT_BY_PROVIDER, param);
        } catch (Exception exception) {
            log.log(Level.WARNING, "ProductDao >> exception >> ", exception);
        }
    }

    public void mapToCategory(Long catId, Long productId) {
        jdbc.update(MAP_CATEGORY, catId, productId);
    }
}
