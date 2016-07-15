package com.epsilon.vtr.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemVO {

    private int id;

    private String name;

    private double price;

    private FileBucket photo;

    private MultipartFile file;

    private byte[] photoStream;

    private String base64Encoded;

}
