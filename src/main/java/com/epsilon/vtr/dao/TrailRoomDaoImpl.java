package com.epsilon.vtr.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.EmployeeTrailRoom;
import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;

@Repository("trailRoomDao")
public class TrailRoomDaoImpl extends AbstractDao<Integer, TrailRoom> implements TrailRoomDao {


    @Override
    public TrailRoom findById(int id) {
        return getByKey(id);
    }

    public List<TrailRoom> findAllTrailsByProfileId(int profileId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("profileId", profileId));
        return (List<TrailRoom>) criteria.list();
    }

    public List<TrailRoom> findAllTrailsByItemId(int itemId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("itemId", itemId));
        return (List<TrailRoom>) criteria.list();
    }

    public List<TrailRoom> findAllTrailsByProfileAndItemId(int profileId,int itemId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("itemId", itemId));
        criteria.add(Restrictions.eq("profileId", profileId));
        return (List<TrailRoom>) criteria.list();
    }

    public void saveTrailRoom(TrailRoom item) {
        persist(item);
    }

    @Override
    public void deleteTrailById(int id) {
        Query query = getSession().createSQLQuery("delete from TrailRoom where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<TrailRoom> findAllTrails() {
        Criteria criteria = createEntityCriteria();
        return (List<TrailRoom>) criteria.list();
    }

}
