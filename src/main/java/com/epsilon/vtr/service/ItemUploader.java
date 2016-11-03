package com.epsilon.vtr.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epsilon.vtr.dao.ItemDao;
import com.epsilon.vtr.model.Item;

@Service
public class ItemUploader {

    @Autowired
    private ItemDao dao;

    public static void main(String[] args) {
        Item item = new Item();
        item.setId(1);
        item.setName("tshirt");

        File file = new File("C:\\Users\\asabtharishi\\Desktop\\files\\files\\new.jpg");

        try {
            byte[] data = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        item.setPhoto(null);
        item.setPhotoContentType("JPG");
        item.setPrice(1000.00);

    }

}
