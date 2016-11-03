package com.epsilon.vtr.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epsilon.vtr.dao.EmployeeDao;
import com.epsilon.vtr.dao.ItemDao;
import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Item;

@Service("inventoryService")
@Transactional
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ItemDao dao;

    public Item findById(int id) {
        return dao.findById(id);
    }

    public void saveItem(Item item) {
        dao.saveItem(item);
    }

    public void manualSave() {
        Item item = new Item();
        item.setName("tshirt");

        File file = new File("C:\\Users\\asabtharishi\\Desktop\\files\\files\\new.jpg");

        try {
            byte[] data = Files.readAllBytes(file.toPath());
            item.setPhoto(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        item.setPhotoContentType("JPG");
        item.setPrice(1000.00);
        dao.saveItem(item);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateItem(Item item) {
        Item entity = dao.findById(item.getId());
        if(entity!=null){
            entity.setName(item.getName());
            entity.setPhoto(item.getPhoto());
            entity.setPrice(item.getPrice());
            entity.setPhotoContentType(item.getPhotoContentType());
        }
    }

    public void deleteItemById(int itemId) {
        dao.deleteItemById(itemId);
    }

    public List<Item> findAllItems() {
        return dao.findAllItems();
    }


    public List<Item> findItemsThatDoesHasTrailPhoto(Employee employee) {
        return dao.findItemsThatDoesHasTrailPhoto(employee);
    }

    public boolean isItemNameUnique(Integer id, String itemName) {
        Item item = findById(id);
        return ( item == null || ((id != null) && (item.getId() == id)));
    }

}
