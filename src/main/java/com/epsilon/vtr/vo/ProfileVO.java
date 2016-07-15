package com.epsilon.vtr.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileVO {

    private int id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String sex;

    private String emailAddress;

    private FileBucket photo = new FileBucket();

    private byte[] photoStream;

    private String base64Encoded;

    private MultipartFile file;

}
