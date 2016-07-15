package com.epsilon.vtr.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.EmployeeTrailRoom;
import com.epsilon.vtr.model.Item;
import com.epsilon.vtr.model.Profile;
import com.epsilon.vtr.model.TrailRoom;

public interface TrialRoomService {

    public TrailRoom findById(int id);

    public void trialRoom() throws IOException;

    public List<TrailRoom> findAllTrailsFor(Profile profile);

    public List<TrailRoom> findAllTrails();

    public void saveTrailRoom(TrailRoom trailRoom);

    public void deleteTrailById(int id);

    public void sendEmail(TrailRoom trailRoom) throws UnsupportedEncodingException, MessagingException;
}
