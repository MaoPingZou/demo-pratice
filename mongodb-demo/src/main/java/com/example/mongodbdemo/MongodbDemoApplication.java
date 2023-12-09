package com.example.mongodbdemo;

import com.example.mongodbdemo.model.GroceryItem;
import com.example.mongodbdemo.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * 注解 @EnableMongoRepositories 用于开启 MongoDB 的功能
 */
@SpringBootApplication
@EnableMongoRepositories
public class MongodbDemoApplication implements CommandLineRunner {

    @Autowired
    private GroceryItemRepository groceryItemRepo;

    public static void main(String[] args) {
        SpringApplication.run(MongodbDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("-----创建 GROCERY ITEMS-----\n");

        createGroceryItems();

        System.out.println("\n-----展示所有 GROCERY ITEMS-----\n");

        showAllGroceryItems();

        System.out.println("\n-----通过 Name 获取 Item-----\n");

        getGroceryItemByName("Whole Wheat Biscuit");

        System.out.println("\n-----通过 CATEGORY 获取 Items -----\n");

        getItemsByCategory("millets");

        System.out.println("\n-----更新 category 为 snacks 的 category 名称-----\n");

        updateCategoryName("snacks");

        System.out.println("\n-----根据 id 删除一个 Item-----\n");

        deleteGroceryItemById("Kodo Millet");

        System.out.println("\n-----GROCERY ITEMS 的最终数量-----\n");

        findCountOfGroceryItems();

        System.out.println("\n-----结束-----");

    }

    /*=====================================================*/

    //CREATE
    void createGroceryItems() {
        System.out.println("数据创建开始......");
        groceryItemRepo.save(new GroceryItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
        groceryItemRepo.save(new GroceryItem("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
        groceryItemRepo.save(new GroceryItem("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
        groceryItemRepo.save(new GroceryItem("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
        groceryItemRepo.save(new GroceryItem("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
        System.out.println("数据创建完成.......");
    }

    // READ

    /**
     * 显示所有的数据
     */
    public void showAllGroceryItems() {
        System.out.println("显示所有的数据");
        groceryItemRepo.findAll().forEach(item -> System.out.println(getItemDetails(item)));
    }

    /**
     * 通过 name 获取所有 item
     */
    public void getGroceryItemByName(String name) {
        System.out.println("通过 name 获取所有 item: " + name);
        GroceryItem item = groceryItemRepo.findItemByName(name);
        System.out.println(getItemDetails(item));
    }

    /**
     * 通过指定的 category 获取所有 item 的名称、数量两个字段值
     */
    public void getItemsByCategory(String category) {
        System.out.println("通过 category： " + category + "获取所有的 item 的 Name 名称、Quantity 数量");
        List<GroceryItem> list = groceryItemRepo.findAll(category);

        list.forEach(item -> System.out.println("Name: " + item.getName() + ", Quantity: " + item.getQuantity()));
    }

    /**
     * 获取 Collection 中的 Documents 的总数量
     */
    public void findCountOfGroceryItems() {
        long count = groceryItemRepo.count();
        System.out.println("Collection 中的 Document 的数量为 : " + count);
    }

    /**
     * 用可读的方式打印明细
     */
    public String getItemDetails(GroceryItem item) {
        System.out.println(
                "Item Name: " + item.getName() +
                        ", \nQuantity: " + item.getQuantity() +
                        ", \nItem Category: " + item.getCategory()
        );
        return "";
    }

    // UPDATE
    public void updateCategoryName(String category) {

        // Change to this new value
        String newCategory = "munchies";

        // Find all the items with the category snacks
        List<GroceryItem> list = groceryItemRepo.findAll(category);

        list.forEach(item -> {
            // Update the category in each document
            item.setCategory(newCategory);
        });

        // Save all the items in database
        List<GroceryItem> itemsUpdated = groceryItemRepo.saveAll(list);

        if (!itemsUpdated.isEmpty()) {
            System.out.println("成功更新： " + itemsUpdated.size() + " 条 items");
        }
    }

    // DELETE
    public void deleteGroceryItemById(String id) {
        groceryItemRepo.deleteById(id);
        System.out.println("Id 为： " + id + " 的 Item 被删除......");
    }

    /*=====================================================*/
}
