package com.epsilon.vtr.dao;

import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Item;

public interface ItemDao {

    Item findById(int id);

    void saveItem(Item item);

    void deleteItemById(int itemId);

    List<Item> findAllItems();

    public List<Item> findItemsThatDoesHasTrailPhoto(Employee employee);

}
