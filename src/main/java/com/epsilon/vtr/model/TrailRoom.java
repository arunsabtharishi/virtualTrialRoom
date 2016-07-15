package com.epsilon.vtr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TRAIL_ROOM")
@Setter
@Getter
public class TrailRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "PROFILE_ID", unique = true, nullable = false)
    private int profileId;


    @Column(name = "ITEM_ID", unique = true, nullable = false)
    private int itemId;

    @Column(name = "EMAIL_SENT")
    private boolean emailSent;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    @Column(name = "EMAIL_SENT_DATE", nullable = true)
    private Date emailSentDate;

    @Column(name="TRAIL_PROFILE_PHOTO_NAME",nullable = true)
    private String trailProfilePhotoName;

    @Column(name = "TRAIL_PROFILE_PHOTO", nullable = true)
    private byte[] trailProfilePhoto;

    @Column(name="TRAIL_PROFILE_PHOTO_CONTENT_TYPE",nullable = true)
    private String trailProfilePhotoContentType;

}
