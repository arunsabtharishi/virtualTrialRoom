package com.epsilon.vtr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.service.ProfileService;
import com.epsilon.vtr.service.TrialRoomService;
import com.epsilon.vtr.vo.FileBucket;
import com.epsilon.vtr.vo.ProfileVO;

@RestController
public class ProfileRestController {

    @Autowired
    ProfileService service;

    @Autowired
    TrialRoomService trialRoomService;

    @Autowired
    MessageSource messageSource;


    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = { "/profiles/" }, method = RequestMethod.GET)
    public ResponseEntity<List<ProfileVO>> listProfiles() {

        List<Profile> profileList = service.findAllProfiles();
        List<ProfileVO> profileVOList = new ArrayList<>();
        profileList.forEach( profile  -> {
            ProfileVO profileVO = new ProfileVO();
            profileVO.setId(profile.getId());
            profileVO.setFirstName(profile.getFirstName());
            profileVO.setLastName(profile.getLastName());
            profileVO.setBirthDate(profile.getBirthDate());
            profileVO.setSex(profile.getSex());
            profileVO.setEmailAddress(profile.getEmailAddress());
            if(profile.getProfilePhoto()!=null) {
                FileBucket fileBucket = new FileBucket();
                profileVO.setPhoto(fileBucket);
                try {
                    byte[] encodeBase64ForProfilePhoto = Base64.encodeBase64(profile.getProfilePhoto());
                    String base64EncodedForProfile = new String(encodeBase64ForProfilePhoto, "UTF-8");
                    profileVO.setBase64Encoded(base64EncodedForProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            profileVOList.add(profileVO);
        });

        return new ResponseEntity<List<ProfileVO>>(profileVOList, HttpStatus.OK);
    }




    @RequestMapping(value = "/profiles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProfileVO> getProfile(@PathVariable("id") int id) {
        System.out.println("Fetching User with id " + id);
        Profile profile = service.findById(id);
        if (profile == null) {
            System.out.println("profile with id " + id + " not found");
            return new ResponseEntity<ProfileVO>(HttpStatus.NOT_FOUND);
        }

        ProfileVO profileVO = new ProfileVO();
        profileVO.setId(profile.getId());
        profileVO.setFirstName(profile.getFirstName());
        profileVO.setLastName(profile.getLastName());
        profileVO.setBirthDate(profile.getBirthDate());
        profileVO.setSex(profile.getSex());
        profileVO.setEmailAddress(profile.getEmailAddress());
        if(profile.getProfilePhoto()!=null) {
            FileBucket fileBucket = new FileBucket();
            profileVO.setPhoto(fileBucket);
            try {
                byte[] encodeBase64ForProfilePhoto = Base64.encodeBase64(profile.getProfilePhoto());
                String base64EncodedForProfile = new String(encodeBase64ForProfilePhoto, "UTF-8");
                profileVO.setBase64Encoded(base64EncodedForProfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseEntity<ProfileVO>(profileVO, HttpStatus.OK);
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/profiles3" }, method = RequestMethod.POST)
    //public ResponseEntity<Void> saveProfile(@PathVariable("file") MultipartFile file, UriComponentsBuilder ucBuilder) {
    public ResponseEntity<Void> saveProfile(@RequestParam("file") MultipartFile file) {
        ProfileVO profileVO = new ProfileVO();
        Profile profile = new Profile();
        if(!service.isProfileUnique(profileVO.getId())){
            FieldError ssnError =new FieldError("profile","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profileVO.getFirstName()}, Locale.getDefault()));
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        profile.setFirstName(profileVO.getFirstName());
        profile.setLastName(profileVO.getLastName());
        profile.setSex(profileVO.getSex());
        profile.setEmailAddress(profileVO.getEmailAddress());
        profile.setBirthDate(new Date());
        if(profileVO.getPhoto() !=null && !profileVO.getPhoto().getFile().isEmpty()) {
            try {
                profile.setProfilePhoto(profileVO.getPhoto().getFile().getBytes());
                profile.setProfilePhotoContentType(profileVO.getPhoto().getFile().getContentType());
                profile.setProfilePhotoName(profileVO.getPhoto().getFile().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveProfile(profile);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }


    @RequestMapping(value = { "/profiles" }, method = RequestMethod.POST)
    //public ResponseEntity<Void> saveProfile(@PathVariable("file") MultipartFile file, UriComponentsBuilder ucBuilder) {
    public ResponseEntity<Void> saveProfile3(@RequestParam("file") MultipartFile file,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("sex") String sex,@RequestParam("emailAddress") String emailAddress, UriComponentsBuilder ucBuilder) {
        ProfileVO profileVO = new ProfileVO();
        Profile profile = new Profile();
        if(!service.isProfileUnique(profileVO.getId())){
            FieldError ssnError =new FieldError("profile","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profileVO.getFirstName()}, Locale.getDefault()));
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setSex(sex);
        profile.setEmailAddress(emailAddress);
        profile.setBirthDate(new Date());
        if(file !=null && !file.isEmpty()) {
            try {
                profile.setProfilePhoto(file.getBytes());
                profile.setProfilePhotoContentType(file.getContentType());
                profile.setProfilePhotoName(file.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveProfile(profile);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/profiles/{id}").buildAndExpand(profile.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }


    @RequestMapping(value = { "/profiles1/" }, method = RequestMethod.POST,headers={MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.ALL_VALUE},consumes={MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.ALL_VALUE})
    //public ResponseEntity<Void> saveProfile(@PathVariable("file") MultipartFile file, UriComponentsBuilder ucBuilder) {
    public ResponseEntity<Void> saveProfile1(@RequestBody ProfileVO profileVO, UriComponentsBuilder ucBuilder) {
        //ProfileVO profileVO = new ProfileVO();
        Profile profile = new Profile();
        if(!service.isProfileUnique(profileVO.getId())){
            FieldError ssnError =new FieldError("profile","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profileVO.getFirstName()}, Locale.getDefault()));
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        profile.setFirstName(profileVO.getFirstName());
        profile.setLastName(profileVO.getLastName());
        profile.setSex(profileVO.getSex());
        profile.setEmailAddress(profileVO.getEmailAddress());
        profile.setBirthDate(new Date());
        if(profileVO.getPhoto() !=null && !profileVO.getPhoto().getFile().isEmpty()) {
            try {
                profile.setProfilePhoto(profileVO.getPhoto().getFile().getBytes());
                profile.setProfilePhotoContentType(profileVO.getPhoto().getFile().getContentType());
                profile.setProfilePhotoName(profileVO.getPhoto().getFile().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveProfile(profile);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/profiles/{id}").buildAndExpand(profile.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

    }


    @RequestMapping(value = { "/profiles2" }, method = RequestMethod.POST)
    //public ResponseEntity<Void> saveProfile(@PathVariable("file") MultipartFile file, UriComponentsBuilder ucBuilder) {
    public ResponseEntity<Void> saveProfile2(@RequestBody ProfileVO profileVO) {
        //ProfileVO profileVO = new ProfileVO();
        Profile profile = new Profile();
        if(!service.isProfileUnique(profileVO.getId())){
            FieldError ssnError =new FieldError("profile","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profileVO.getFirstName()}, Locale.getDefault()));
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        profile.setFirstName(profileVO.getFirstName());
        profile.setLastName(profileVO.getLastName());
        profile.setSex(profileVO.getSex());
        profile.setEmailAddress(profileVO.getEmailAddress());
        profile.setBirthDate(new Date());
        if(profileVO.getPhoto() !=null && !profileVO.getPhoto().getFile().isEmpty()) {
            try {
                profile.setProfilePhoto(profileVO.getPhoto().getFile().getBytes());
                profile.setProfilePhotoContentType(profileVO.getPhoto().getFile().getContentType());
                profile.setProfilePhotoName(profileVO.getPhoto().getFile().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.saveProfile(profile);

        return new ResponseEntity<Void>(HttpStatus.OK);

    }


    @RequestMapping(value = "/profiles/{id}", method = RequestMethod.PUT)
    //public ResponseEntity<Void> updateProfile(@PathVariable("id") int id, @RequestBody ProfileVO profileVO) {
    public ResponseEntity<Void> updateProfile(@PathVariable("id") int id,@RequestParam("file") MultipartFile file,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("sex") String sex,@RequestParam("emailAddress") String emailAddress, UriComponentsBuilder ucBuilder) {
        System.out.println("Updating User " + id);

        Profile currentProfile = service.findById(id);

        if (currentProfile==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        currentProfile.setFirstName(firstName);
        currentProfile.setLastName(lastName);
        currentProfile.setSex(sex);
        currentProfile.setEmailAddress(emailAddress);
        currentProfile.setBirthDate(new Date());
        if(file !=null && !file.isEmpty()) {
            try {
                currentProfile.setProfilePhoto(file.getBytes());
                currentProfile.setProfilePhotoContentType(file.getContentType());
                currentProfile.setProfilePhotoName(file.getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        service.updateProfile(currentProfile);
        //profileVO.setId(currentProfile.getId());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProfileVO> deleteUser(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting User with id " + id);

        Profile profile = service.findById(id);
        if (profile == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<ProfileVO>(HttpStatus.NOT_FOUND);
        }

        service.deleteProfileById(id);
        return new ResponseEntity<ProfileVO>(HttpStatus.NO_CONTENT);
    }
}
