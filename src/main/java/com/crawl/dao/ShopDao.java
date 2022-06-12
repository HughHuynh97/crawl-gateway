package com.crawl.dao;

import com.crawl.model.ShopDto;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;

@Repository
@Log
public class ShopDao {

    private static final String INSERT_SHOP = """
            INSERT INTO shop(shop_id, user_id, name, username, avatar, background, shop_location, rating_star, rating_good,
             rating_normal, rating_bad, total_product, follower, description, country, status, provider) 
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE name=VALUES(name), username=VALUES(username), avatar=VALUES(avatar), background=VALUES(background), shop_location=VALUES(shop_location),
            rating_star=VALUES(rating_star), rating_good=VALUES(rating_good), rating_bad=VALUES(rating_bad), total_product=VALUES(total_product), follower=VALUES(follower),
            description=VALUES(description), country=VALUES(country), status=VALUES(status), provider=VALUES(provider)
            """;

    @Autowired
    private JdbcTemplate jdbc;

    public void add(ShopDto dto) {
        try {
            var param = new Object[]{
                    dto.getShopId(), dto.getUserId(), dto.getName(), dto.getUsername(), dto.getAvatar(), dto.getBackground(), dto.getShopLocation(),
                    dto.getRatingStar(), dto.getRatingGood(), dto.getRatingNormal(), dto.getRatingBad(), dto.getTotalProduct(), dto.getFollower(), dto.getDescription(),
                    dto.getCountry(), dto.getStatus(), dto.getProvider()
            };
            jdbc.update(INSERT_SHOP, param);
        } catch (Exception exception) {
            log.log(Level.WARNING, "ShopDao >> exception >> ", exception);
        }
    }
}
