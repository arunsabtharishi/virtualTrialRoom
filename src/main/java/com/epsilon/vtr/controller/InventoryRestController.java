package com.epsilon.vtr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.service.InventoryService;
import com.epsilon.vtr.service.TrialRoomService;
import com.epsilon.vtr.vo.ItemVO;

@Controller
@RequestMapping("/inventory")
public class InventoryRestController {

    @Autowired
    InventoryService service;

    @Autowired
    TrialRoomService trialRoomService;

    @Autowired
    MessageSource messageSource;



    @RequestMapping(value = { "/items/" }, method = RequestMethod.GET)
    public ResponseEntity<List<ItemVO>> listItems() {

        List<Item> items = service.findAllItems();
        List<ItemVO> itemVOList = new ArrayList<>();
        for(Item item:items) {
            ItemVO itemVO = new ItemVO();
            itemVO.setId(item.getId());
            itemVO.setName(item.getName());
            itemVO.setPrice(item.getPrice());
            if(item.getPhoto()!=null) {
                try {
                    byte[] encodeBase64 = Base64.encodeBase64(item.getPhoto());
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    itemVO.setBase64Encoded(base64Encoded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            itemVOList.add(itemVO);
        }
        return new ResponseEntity<List<ItemVO>>(itemVOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ItemVO> getItems(@PathVariable("id") int id) {

        Item item = service.findById(id);
            ItemVO itemVO = new ItemVO();
            itemVO.setId(item.getId());
            itemVO.setName(item.getName());
            itemVO.setPrice(item.getPrice());
            if(item.getPhoto()!=null) {
                try {
                    byte[] encodeBase64 = Base64.encodeBase64(item.getPhoto());
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    itemVO.setBase64Encoded(base64Encoded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        return new ResponseEntity<ItemVO>(itemVO, HttpStatus.OK);
    }

    @RequestMapping(value = { "/items/" }, method = RequestMethod.POST )
    public ResponseEntity<Void> saveItem(@RequestParam("file") MultipartFile file,@RequestParam("name") String name,@RequestParam("price") double price,UriComponentsBuilder ucBuilder) {

        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        if(file !=null && !file.isEmpty()) {
            try {
                item.setPhoto(file.getBytes());
                item.setPhotoContentType(file.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveItem(item);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/items/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = { "/items/{id}" }, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateEmployee(@RequestParam("file") MultipartFile file,@RequestParam("name") String name,@RequestParam("price") double price, @PathVariable int id) {

        Item item = service.findById(id);
        if(item == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        item.setName(name);
        item.setPrice(price);
        if(file !=null && !file.isEmpty()) {
            try {
                item.setPhoto(file.getBytes());
                item.setPhotoContentType(file.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        service.updateItem(item);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = { "/items/{id}" }, method = RequestMethod.DELETE)
    public  ResponseEntity<Void> deleteItem(@PathVariable int itemId) {
        service.deleteItemById(itemId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
