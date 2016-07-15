package com.epsilon.vtr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epsilon.vtr.dao.EmployeeDao;
import com.epsilon.vtr.dao.ProfileDao;
import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Profile;

@Service("profileService")
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao dao;

    public Profile findById(int id) {
        return dao.findById(id);
    }

    public void saveProfile(Profile profile) {
        dao.saveProfile(profile);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateProfile(Profile profile) {
        Profile entity = dao.findById(profile.getId());
        if(entity!=null){
            entity.setFirstName(profile.getFirstName());
            entity.setLastName(profile.getLastName());
            entity.setSex(profile.getSex());
            entity.setEmailAddress(profile.getEmailAddress());
            entity.setBirthDate(profile.getBirthDate());
            entity.setProfilePhoto(profile.getProfilePhoto());
            entity.setProfilePhotoContentType(profile.getProfilePhotoContentType());
            entity.setProfilePhotoName(profile.getProfilePhotoName());
        }
    }

    public void deleteProfileById(int id) {
        dao.deleteProfileById(id);
    }

    public List<Profile> findAllProfiles() {
        return dao.findAllProfiles();
    }

    public boolean isProfileUnique(Integer id) {
        Profile profile = findById(id);
        return ( profile == null || ((id != null) && (profile.getId() == id)));
    }

}
