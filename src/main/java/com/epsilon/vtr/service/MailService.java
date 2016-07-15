package com.epsilon.vtr.service;

import javax.mail.MessagingException;

import com.epsilon.vtr.vo.TrailRoomVO;

public interface MailService {

    public void sendEmail(final TrailRoomVO trailRoomVO) throws MessagingException;
}
