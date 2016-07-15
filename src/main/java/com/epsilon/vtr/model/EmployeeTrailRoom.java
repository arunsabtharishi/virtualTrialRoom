package com.epsilon.vtr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_TRAIL_ROOM")
public class EmployeeTrailRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "EMP_ID", nullable = false)
    private int empId;

    @Column(name = "ITEM_ID", nullable = false)
    private int itemId;

    public String getEmpProfilePhotoName() {
        return this.empProfilePhotoName;
    }

    public void setEmpProfilePhotoName(String empProfilePhotoName) {
        this.empProfilePhotoName = empProfilePhotoName;
    }

    @Column(name="EMP_PROFILE_PHOTO_NAME",nullable = true)
    private String empProfilePhotoName;

    @Column(name = "EMP_PROFILE_PHOTO", nullable = true)
    private byte[] empProfilePhoto;

    @Column(name="EMP_PROFILE_PHOTO_CONTENT_TYPE",nullable = true)
    private String empProfilePhotoContentType;

    @Column(name="EMP_TRAIL_PROFILE_PHOTO_NAME",nullable = true)
    private String empTrailProfilePhotoName;

    @Column(name = "EMP_TRAIL_PROFILE_PHOTO", nullable = true)
    private byte[] empTrailProfilePhoto;

    @Column(name="EMP_TRAIL_PROFILE_PHOTO_CONTENT_TYPE",nullable = true)
    private String empTrailProfilePhotoContentType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getEmpId() {
        return this.empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public byte[] getEmpProfilePhoto() {
        return this.empProfilePhoto;
    }

    public void setEmpProfilePhoto(byte[] empProfilePhoto) {
        this.empProfilePhoto = empProfilePhoto;
    }

    public String getEmpProfilePhotoContentType() {
        return this.empProfilePhotoContentType;
    }

    public void setEmpProfilePhotoContentType(String empProfilePhotoContentType) {
        this.empProfilePhotoContentType = empProfilePhotoContentType;
    }

    public String getEmpTrailProfilePhotoName() {
        return this.empTrailProfilePhotoName;
    }

    public void setEmpTrailProfilePhotoName(String empTrailProfilePhotoName) {
        this.empTrailProfilePhotoName = empTrailProfilePhotoName;
    }

    public byte[] getEmpTrailProfilePhoto() {
        return this.empTrailProfilePhoto;
    }

    public void setEmpTrailProfilePhoto(byte[] empTrailProfilePhoto) {
        this.empTrailProfilePhoto = empTrailProfilePhoto;
    }

    public String getEmpTrailProfilePhotoContentType() {
        return this.empTrailProfilePhotoContentType;
    }

    public void setEmpTrailProfilePhotoContentType(String empTrailProfilePhotoContentType) {
        this.empTrailProfilePhotoContentType = empTrailProfilePhotoContentType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + (Long.valueOf(empId).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof EmployeeTrailRoom))
            return false;
        EmployeeTrailRoom other = (EmployeeTrailRoom) obj;
        if (id != other.id)
            return false;
        if (empId == 0) {
            if (other.empId != 0)
                return false;
        } else if (empId!= other.empId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", empId=" + empId + ", itemId="
                + itemId+ "]";
    }




}
