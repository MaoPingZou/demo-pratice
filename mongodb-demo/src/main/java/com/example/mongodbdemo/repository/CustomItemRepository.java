package com.example.mongodbdemo.repository;

public interface CustomItemRepository {

    /**
     * 根据 Name 名称更新 Quantity 字段值
     * @param name
     * @param newQuantity
     */
    void updateItemQuantity(String name, float newQuantity);

}
