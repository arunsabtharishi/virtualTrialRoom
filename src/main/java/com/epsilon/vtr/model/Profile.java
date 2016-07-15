package com.epsilon.vtr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PROFILE")
@Setter
@Getter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=3, max=50)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Size(min=3, max=50)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern="MM/dd/yyyy")
    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @NotEmpty
    @Column(name = "SEX", nullable = false)
    private String sex;

    @NotEmpty
    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;

    @Column(name="PROFILE_PHOTO_NAME",nullable = true)
    private String profilePhotoName;

    @Column(name = "PROFILE_PHOTO", nullable = true)
    private byte[] profilePhoto;

    @Column(name="PROFILE_PHOTO_CONTENT_TYPE",nullable = true)
    private String profilePhotoContentType;

}
