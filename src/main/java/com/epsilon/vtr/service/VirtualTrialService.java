package com.epsilon.vtr.service;

import java.io.IOException;
import java.util.List;

import com.epsilon.vtr.model.Employee;
import com.epsilon.vtr.model.EmployeeTrailRoom;

public interface VirtualTrialService {

    public void virtualTrial(Employee employee) throws IOException;

    public List<EmployeeTrailRoom> findAllTrailsFor(Employee employee);
}
