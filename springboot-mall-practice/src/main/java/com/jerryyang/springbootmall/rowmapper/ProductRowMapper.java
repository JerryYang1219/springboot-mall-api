package com.jerryyang.springbootmall.rowmapper;

import com.jerryyang.springbootmall.constant.ProductCategory;
import com.jerryyang.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
  ProductRowMapper 負責將資料庫回傳的 ResultSet 轉換為 Product 實體物件。
  此類別為 DAO 層專用，確保資料存取邏輯與物件轉換邏輯的分離。
 */
public class ProductRowMapper implements RowMapper<Product> {
    @Nullable
    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();

        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        String categoryStr = resultSet.getString("category");
        ProductCategory category = ProductCategory.valueOf(categoryStr);
        product.setCategory(category);
        //與上三行一樣product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));

        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return product;
    }
}
