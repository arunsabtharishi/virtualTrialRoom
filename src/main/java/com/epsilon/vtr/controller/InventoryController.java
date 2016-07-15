package com.epsilon.vtr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.service.InventoryService;
import com.epsilon.vtr.service.TrialRoomService;
import com.epsilon.vtr.vo.FileBucket;
import com.epsilon.vtr.vo.ItemVO;

@Controller
@RequestMapping("/inventory11")
public class InventoryController {

    @Autowired
    InventoryService service;

    @Autowired
    TrialRoomService trialRoomService;

    @Autowired
    MessageSource messageSource;


    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String listEmployees(ModelMap model) {

        List<Item> employees = service.findAllItems();
        List<ItemVO> itemVOList = new ArrayList<>();
        employees.forEach( item  -> {
            ItemVO itemVO = new ItemVO();
            itemVO.setName(item.getName());
            if(item.getPhoto()!=null) {
                FileBucket fileBucket = new FileBucket();
                itemVO.setPhoto(fileBucket);
                byte[] encodeBase64 = Base64.encodeBase64(item.getPhoto());
                String base64Encoded;
                try {
                    base64Encoded = new String(encodeBase64, "UTF-8");
                    itemVO.setBase64Encoded(base64Encoded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            itemVOList.add(itemVO);
        });
        model.addAttribute("itemVOList", itemVOList);
        return "allitems";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String newItem(ModelMap model) {
        ItemVO itemVO = new ItemVO();
        itemVO.setPhoto(new FileBucket());
        model.addAttribute("itemVO", itemVO);
        model.addAttribute("edit", false);
        return "item-registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST )
    public String saveItem(@Valid ItemVO itemVO, BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            return "item-registration";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        Item item = new Item();
        if(!service.isItemNameUnique(itemVO.getId(), itemVO.getName())){
            FieldError ssnError =new FieldError("item","itemName",messageSource.getMessage("non.unique.itemName", new String[]{itemVO.getName()}, Locale.getDefault()));
            result.addError(ssnError);
            return "item-registration";
        }
        item.setName(itemVO.getName());
        if(itemVO.getPhoto() !=null && !itemVO.getPhoto().getFile().isEmpty()) {
            try {
                item.setPhoto(itemVO.getPhoto().getFile().getBytes());
                item.setPhotoContentType(itemVO.getPhoto().getFile().getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveItem(item);

        model.addAttribute("success", "Item " + item.getName() + " registered successfully");
        return "item-success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = { "/edit-{itemId}-item" }, method = RequestMethod.GET)
    public String editEmployee(@PathVariable int itemId, ModelMap model) {
        Item item = service.findById(itemId);

        ItemVO itemVO = new ItemVO();
        itemVO.setName(item.getName());
        if(item.getPhoto()!=null) {
            FileBucket fileBucket = new FileBucket();
            itemVO.setPhoto(fileBucket);
        }

        model.addAttribute("itemVO", itemVO);
        model.addAttribute("edit", true);
        return "item-registration";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{itemId}-item" }, method = RequestMethod.POST)
    public String updateEmployee(@Valid ItemVO itemVO, BindingResult result,
            ModelMap model, @PathVariable int itemId) {

        if (result.hasErrors()) {
            return "item-registration";
        }

        Item item = service.findById(itemId);

        if(!service.isItemNameUnique(itemVO.getId(), itemVO.getName())){
            FieldError ssnError =new FieldError("itemVO","itemName",messageSource.getMessage("non.unique.itemName", new String[]{itemVO.getName()}, Locale.getDefault()));
            result.addError(ssnError);
            return "item-registration";
        }

        item.setName(itemVO.getName());
        if(itemVO.getPhoto() !=null && !itemVO.getPhoto().getFile().isEmpty()) {
            try {
                item.setPhoto(itemVO.getPhoto().getFile().getBytes());
                item.setPhotoContentType(itemVO.getPhoto().getFile().getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.updateItem(item);

        model.addAttribute("success", "Item " + item.getName()    + " updated successfully");
        return "item-success";
    }


    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/view-{itemId}-photo" }, method = RequestMethod.GET)
    public String viewItemPhoto(@PathVariable int itemId,ModelMap model) throws IOException {

        Item item = service.findById(itemId);

        ItemVO itemVO = new ItemVO();
        itemVO.setName(item.getName());
        if(item.getPhoto()!=null) {
            FileBucket fileBucket = new FileBucket();
            byte[] encodeBase64 = Base64.encodeBase64(item.getPhoto());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            itemVO.setBase64Encoded(base64Encoded);
            model.addAttribute("itemVO", itemVO);
        }

        return "item-display";
    }

    /*
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = { "/delete-{itemId}-item" }, method = RequestMethod.GET)
    public String deleteItem(@PathVariable int itemId) {
        service.deleteItemById(itemId);
        return "redirect:/inventory/list";
    }

}
