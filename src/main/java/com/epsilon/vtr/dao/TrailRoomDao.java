package com.epsilon.vtr.dao;

import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.EmployeeTrailRoom;
import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;

public interface TrailRoomDao {

    public TrailRoom findById(int id);
    List<TrailRoom> findAllTrails();

    List<TrailRoom> findAllTrailsByProfileId(int profileId);

    List<TrailRoom> findAllTrailsByItemId(int itemId);

    List<TrailRoom> findAllTrailsByProfileAndItemId(int profileId,int itemId);

    void saveTrailRoom(TrailRoom trailRoom);

    void deleteTrailById(int id);

}
