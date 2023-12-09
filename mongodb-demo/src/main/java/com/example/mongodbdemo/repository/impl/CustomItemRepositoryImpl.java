package com.example.mongodbdemo.repository.impl;

import com.example.mongodbdemo.model.GroceryItem;
import com.example.mongodbdemo.repository.CustomItemRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CustomItemRepositoryImpl implements CustomItemRepository {

    /**
     * 注入 mongoTemplate
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateItemQuantity(String name, float newQuantity) {
        // 构建查询对象
        Query query = new Query(Criteria.where("name").is(name));

        // 构建更新对象
        Update update = new Update();
        update.set("quantity", newQuantity);

        // 调用 mongoTemplate 的 updateFirst 方法更新第一个找到的 Document
        UpdateResult result = mongoTemplate.updateFirst(query, update, GroceryItem.class);

        if (result == null)
            System.out.println("\n没有 Document 被更新");
        else
            System.out.println("\n" + result.getModifiedCount() + " 个文档被更新......");
    }

}
