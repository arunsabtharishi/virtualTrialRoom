package com.epsilon.vtr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;
import com.epsilon.vtr.service.ProfileService;
import com.epsilon.vtr.service.TrialRoomService;
import com.epsilon.vtr.vo.FileBucket;
import com.epsilon.vtr.vo.ProfileVO;
import com.epsilon.vtr.vo.TrailRoomVO;

@RestController
public class ProfileController1 {

    @Autowired
    ProfileService profileService;


    @Autowired
    TrialRoomService trialRoomService;

    @Autowired
    MessageSource messageSource;


    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = { "/profiles1/" }, method = RequestMethod.GET)
    public ResponseEntity<List<ProfileVO>> listProfiles() {

        List<Profile> profileList = profileService.findAllProfiles();
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

    /*
     * This method will provide the medium to add a new Profile.
     */
    @RequestMapping(value = { "/profiles1/" }, method = RequestMethod.POST)
    public String newProfile(ModelMap model) {
        ProfileVO profileVO = new ProfileVO();
        profileVO.setPhoto(new FileBucket());
        model.addAttribute("profileVO", profileVO);
        model.addAttribute("edit", false);
        return "register-profile";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST )
    public String saveProfile(@Valid ProfileVO profileVO, BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            return "register-profile";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        Profile profile = new Profile();
        if(!profileService.isProfileUnique(profileVO.getId())){
            FieldError ssnError =new FieldError("profile","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profileVO.getFirstName()}, Locale.getDefault()));
            result.addError(ssnError);
            return "register-profile";
        }
        profile.setFirstName(profileVO.getFirstName());
        profile.setLastName(profileVO.getLastName());
        profile.setSex(profileVO.getSex());
        profile.setEmailAddress(profileVO.getEmailAddress());
                profile.setBirthDate(profileVO.getBirthDate());
        if(profileVO.getPhoto() !=null && !profileVO.getPhoto().getFile().isEmpty()) {
            try {
                profile.setProfilePhoto(profileVO.getPhoto().getFile().getBytes());
                profile.setProfilePhotoContentType(profileVO.getPhoto().getFile().getContentType());
                profile.setProfilePhotoName(profileVO.getPhoto().getFile().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profileService.saveProfile(profile);

        model.addAttribute("success", "Profile " + profile.getFirstName() + " " +  profile.getLastName()+ " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = { "/edit-{id}-profile" }, method = RequestMethod.GET)
    public String editEmployee(@PathVariable int id, ModelMap model) {
        Profile profile = profileService.findById(id);

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
        }

        model.addAttribute("profileVO", profileVO);
        model.addAttribute("edit", true);
        return "register-profile";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{id}-employee" }, method = RequestMethod.POST)
    public String updateProfile(@Valid ProfileVO profileVO, BindingResult result,
            ModelMap model, @PathVariable int id) {

        if (result.hasErrors()) {
            return "register-profile";
        }

        Profile profile = profileService.findById(id);

        if(!profileService.isProfileUnique(profile.getId())){
            FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{profile.getFirstName()}, Locale.getDefault()));
            result.addError(ssnError);
            return "register-profile";
        }


        profile.setFirstName(profileVO.getFirstName());
        profile.setSex(profileVO.getSex());
        profile.setEmailAddress(profileVO.getEmailAddress());
                profile.setBirthDate(profileVO.getBirthDate());
        if(profileVO.getPhoto() !=null && !profileVO.getPhoto().getFile().isEmpty()) {
            try {
                profile.setProfilePhoto(profileVO.getPhoto().getFile().getBytes());
                profile.setProfilePhotoContentType(profileVO.getPhoto().getFile().getContentType());
                profile.setProfilePhotoName(profileVO.getPhoto().getFile().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profileService.updateProfile(profile);

        model.addAttribute("success", "Employee " + profile.getFirstName() + " " + profile.getLastName() +" updated successfully");
        return "success";
    }


/*    @RequestMapping(value={"/trail-{ssn}"},method=RequestMethod.GET)
    public String tryTrialRoom(@PathVariable String ssn,ModelMap model) throws IOException{
        Employee employee = service.findEmployeeBySsn(ssn);
        virtualTrialService.virtualTrial(employee);
        List<EmployeeTrailRoom> findAllTrailsFor = virtualTrialService.findAllTrailsFor(employee);
        List<EmployeeTrailRoomVO> employeeTrailRoomVOList = new ArrayList<>();
        findAllTrailsFor.forEach( employeeTrailRoom -> {
            EmployeeTrailRoomVO trailRoomVO = new EmployeeTrailRoomVO();
            trailRoomVO.setId(employeeTrailRoom.getId());
            trailRoomVO.setEmployeeName(employee.getName());
            try {
                byte[] encodeBase64ForEmployee = Base64.encodeBase64(employeeTrailRoom.getEmpProfilePhoto());
                String base64EncodedForEmployee = new String(encodeBase64ForEmployee, "UTF-8");
                trailRoomVO.setBase64EncodedForEmployeePhoto(base64EncodedForEmployee);
                byte[] encodeBase64ForNewArrival = Base64.encodeBase64(employeeTrailRoom.getEmpTrailProfilePhoto());
                String base64EncodedForNewArrival = new String(encodeBase64ForNewArrival, "UTF-8");
                trailRoomVO.setBase64EncodedForEmployeeTrailPhoto(base64EncodedForNewArrival);
            } catch (Exception e) {
                e.printStackTrace();
            }
            employeeTrailRoomVOList.add(trailRoomVO);
        });
        model.addAttribute("employeeTrailRoomVOList", employeeTrailRoomVOList);
        return "showTrailRoom";
    }*/


    @RequestMapping(value={"/view-{id}-trailroom"},method=RequestMethod.GET)
    public String viewTrialRoom(@PathVariable int id,ModelMap model) throws IOException{
        Profile employee = profileService.findById(id);
        List<TrailRoom> findAllTrailsFor = trialRoomService.findAllTrailsFor(employee);
        List<TrailRoomVO> trailRoomVOList = new ArrayList<>();
        System.out.println(findAllTrailsFor.size());
        for(TrailRoom trailRoom:findAllTrailsFor ) {
            TrailRoomVO trailRoomVO = new TrailRoomVO();
            trailRoomVO.setId(trailRoom.getId());
            //trailRoomVO.setEmployeeFirstName(employee.getFirstName());
            //trailRoomVO.setEmployeeLastName(employee.getLastName());
            try {
                byte[] encodeBase64ForNewArrival = Base64.encodeBase64(trailRoom.getTrailProfilePhoto());
                String base64EncodedForNewArrival = new String(encodeBase64ForNewArrival, "UTF-8");
                trailRoomVO.setBase64EncodedForProfileTrailPhoto(base64EncodedForNewArrival);
            } catch (Exception e) {
                e.printStackTrace();
            }
            trailRoomVOList.add(trailRoomVO);
        }
        model.addAttribute("trailRoomVOList", trailRoomVOList);
        return "profile-trailRoom";
    }


    /*
     * This method will delete an Profile by it's id value.
     */
    @RequestMapping(value = { "/delete-{id}-profile" }, method = RequestMethod.GET)
    public String deleteProfile(@PathVariable int id) {
        profileService.deleteProfileById(id);
        return "redirect:/list";
    }

}
