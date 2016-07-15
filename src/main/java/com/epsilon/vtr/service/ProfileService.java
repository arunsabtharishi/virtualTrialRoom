package com.epsilon.vtr.service;

import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Profile;

public interface ProfileService {

    Profile findById(int id);

    void saveProfile(Profile employee);

    void updateProfile(Profile employee);

    void deleteProfileById(int id);

    List<Profile> findAllProfiles();

    boolean isProfileUnique(Integer id);

}
