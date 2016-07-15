package com.epsilon.vtr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ITEM")
@Setter
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min=3, max=50)
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = true)
    private Double price;

    @Column(name = "PHOTO", nullable = true)
    private byte[] photo;

    @Column(name="PHOTO_CONTENT_TYPE",nullable = true)
    private String photoContentType;

}
