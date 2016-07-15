package com.epsilon.vtr.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;
import com.epsilon.vtr.service.InventoryService;
import com.epsilon.vtr.service.ProfileService;
import com.epsilon.vtr.service.TrialRoomService;
import com.epsilon.vtr.vo.TrailRoomVO;

@Controller
@RequestMapping("/trailRoom")
public class TrailRoomController {

    @Autowired
    InventoryService service;

    @Autowired
    TrialRoomService trialRoomService;

    @Autowired
    ProfileService profileService;

    @Autowired
    MessageSource messageSource;


    @RequestMapping(value = { "/fetchAllTrailRoomItems/" }, method = RequestMethod.GET)
    public ResponseEntity<List<TrailRoomVO>> fetchAllTrailRoomItems() {

        List<TrailRoom> trailRooms = trialRoomService.findAllTrails();
        List<TrailRoomVO> trailRoomsVOList = new ArrayList<>();
        for(TrailRoom trailRoom:trailRooms) {
            TrailRoomVO trailRoomVO = new TrailRoomVO();
            trailRoomVO.setId(trailRoom.getId());
            Profile profile = profileService.findById(trailRoom.getProfileId());
            trailRoomVO.setFirstName(profile.getFirstName());
            trailRoomVO.setLastName(profile.getLastName());
            trailRoomVO.setEmailAddress(profile.getEmailAddress());
            if(trailRoom.getTrailProfilePhoto()!=null) {
                try {
                    byte[] encodeBase64 = Base64.encodeBase64(trailRoom.getTrailProfilePhoto());
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    trailRoomVO.setBase64EncodedForProfileTrailPhoto(base64Encoded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            trailRoomsVOList.add(trailRoomVO);
        }
        return new ResponseEntity<List<TrailRoomVO>>(trailRoomsVOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/generateTrailRoomItems", method = RequestMethod.GET)
    public ResponseEntity<Void> generateTrailRoomItems() throws IOException {

        trialRoomService.trialRoom();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = { "/sendEmail/{id}" }, method = RequestMethod.POST )
    public ResponseEntity<Void> sendEmail(@PathVariable int id) throws UnsupportedEncodingException {

        TrailRoom trailRoom = trialRoomService.findById(id);
        try {
            trialRoomService.sendEmail(trailRoom);
        } catch (MessagingException e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    public  ResponseEntity<Void> deleteTrailRoom(@PathVariable int itemId) {
        trialRoomService.deleteTrailById(itemId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
