package com.example.mongodbdemo.repository;

import com.example.mongodbdemo.model.GroceryItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {

    /**
     * 根据 name 值获取结果
     * ?0 是占位符，表示第几个参数
     */
    @Query("{name:'?0'}")
    GroceryItem findItemByName(String name);

    /**
     * 通过 category 值获取所有的结果
     * fields 表示此查询需要返回的字段
     */
    @Query(value = "{category:'?0'}", fields = "{'name' : 1, 'quantity' : 1}")
    List<GroceryItem> findAll(String category);

    /**
     *
     * 获取数量
     * @return 数量值
     */
    @Override
    long count();

}
