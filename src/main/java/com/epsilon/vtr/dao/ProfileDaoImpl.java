package com.epsilon.vtr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.Profile;

@Repository("profileDao")
public class ProfileDaoImpl extends AbstractDao<Integer, Profile> implements ProfileDao {

    @Override
    public Profile findById(int id) {
        return getByKey(id);
    }

    @Override
    public void saveProfile(Profile profile) {
        persist(profile);
    }

    @Override
    public void deleteProfileById(int id) {
        Query query = getSession().createSQLQuery("delete from Profile where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Profile> findAllProfiles() {
        Criteria criteria = createEntityCriteria();
        return (List<Profile>) criteria.list();
    }

}
