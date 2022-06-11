package com.crawl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long catId;
    private String name;

    private String image;
    private String code;
    private Long parentId;

    private String provider;

    public CategoryDto(ResultSet rs) throws SQLException {
        this.catId = rs.getLong("cat_id");
        this.code = rs.getString("code");
        this.name = rs.getString("name");
        this.parentId = rs.getLong("parent_cat_id");
    }

}
