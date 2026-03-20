package com.jerryyang.springbootmall.dao.impl;

import com.jerryyang.springbootmall.dao.ProductDao;
import com.jerryyang.springbootmall.dto.ProductQueryParams;
import com.jerryyang.springbootmall.dto.ProductRequest;
import com.jerryyang.springbootmall.model.Product;
import com.jerryyang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";  // WHERE 1=1 不影響查詢，為了方便連接sql

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, productQueryParams);

        //queryForObject 將count的值轉換成Integer類型
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date FROM product WHERE 1=1"; // WHERE 1=1 不影響查詢，為了方便連接sql

        Map<String, Object> map = new HashMap<>();

        //查詢商品分類
        sql = addFilteringSql(sql, map, productQueryParams);

        //排序，使用字串拼接方式把sql拼起來。兩個參數有預設值，不需有null判斷式
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        //使用query()查詢商品數據
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        //定義 SQL 查詢指令，選取所需欄位，並使用 :productId 具名參數來進行過濾
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date FROM product WHERE product_id = :productId";

        //建立 HashMap 容器，將方法參數 productId 存入，以便後續映射至 SQL 中的 :productId
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        //執行查詢，傳入 SQL、參數 Map，並透過 ProductRowMapper 將資料庫結果轉換成 Java Object
        //query 方法預設會回傳一個 List 集合
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        //檢查查詢結果，如果集合長度大於 0，代表有找到資料
        if(productList.size() > 0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        //定義 SQL 新增指令，使用具名參數來預留欄位位置，防止 SQL Injection
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:product_name, :category, :image_url, :price, :stock, :description, " +
                ":created_date, :last_modified_date)";
        //建立一個 Map 容器，用來將 Java 物件的資料與 SQL 中的具名參數進行映射
        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        //將 Enum 類型轉為字串存入，確保與資料庫的 VARCHAR/TEXT 類型匹配
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        //初始化時間，確保資料的新增時間與最後修改時間同步
        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        //宣告一個 KeyHolder 物件，用來儲存資料庫自動生成的ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //執行 SQL 更新動作。使用 MapSqlParameterSource 包裝參數，並傳入 keyHolder 用於接住生成的 ID
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        //從 keyHolder 中取得剛產生的 ID，並轉為 int 型態回傳給 Service 層
        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    //更新商品功能
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :product_name, category = :category, image_url = :image_url, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :last_modified_date" +
                " WHERE product_id = :product_id";

        // 將 productId 映射至 SQL 具名參數 :product_id
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        // 將 ProductRequest 中的資料映射至 SQL 具名參數
        // 將前端傳入的 DTO 欄位逐一填入 Map，Key 值必須與 SQL 裡的 :name 完全一致
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        //更新 新的修改時間
        map.put("last_modified_date", new Date());

        // 傳入 SQL 與參數映射表，執行資料庫修改
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :product_id";

        // 將 productId 映射至 SQL 具名參數 :product_id
        Map<String, Object> map = new HashMap<>();
        map.put("product_id", productId);

        // 傳入 SQL 與參數映射表，執行資料庫的刪除動作
        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams){

        //在controller中設定category、search為不必填參數，有可能為null，所以需有if判斷式不是null時才拼上sql語法
        //查詢商品分類
        if(productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category"; //AND前面必需要有空白
            map.put("category", productQueryParams.getCategory().name());
        }

        //篩選出包含這個關鍵字的商品
        if(productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%"); //模糊查詢%需要寫在map的值中，寫在sql語法會報錯
        }

        return sql;
    }
}
