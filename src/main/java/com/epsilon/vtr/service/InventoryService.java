package com.epsilon.vtr.service;

import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Item;

public interface InventoryService {

    Item findById(int id);

    void saveItem(Item item);

    void updateItem(Item item);

    void deleteItemById(int id);

    List<Item> findAllItems();

    boolean isItemNameUnique(Integer id, String itemName);

}
