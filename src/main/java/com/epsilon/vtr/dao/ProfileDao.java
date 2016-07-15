package com.epsilon.vtr.dao;

import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Profile;

public interface ProfileDao {

    Profile findById(int id);

    void saveProfile(Profile profile);

    void deleteProfileById(int id);

    List<Profile> findAllProfiles();

}
