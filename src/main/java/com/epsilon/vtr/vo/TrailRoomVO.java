package com.epsilon.vtr.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrailRoomVO {

    private int id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private boolean emailSent;

    private String base64EncodedForProfileTrailPhoto;

}
