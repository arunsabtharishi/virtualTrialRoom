package com.epsilon.vtr.vo;

public class EmployeeTrailRoomVO {

    private int id;

    private String employeeName;

    private FileBucket employeePhoto;

    private FileBucket employeeTrailPhoto;

    private byte[] photoStream;

    private String base64EncodedForEmployeePhoto;

    private String base64EncodedForEmployeeTrailPhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public FileBucket getEmployeePhoto() {
        return this.employeePhoto;
    }

    public void setEmployeePhoto(FileBucket employeePhoto) {
        this.employeePhoto = employeePhoto;
    }

    public FileBucket getEmployeeTrailPhoto() {
        return this.employeeTrailPhoto;
    }

    public void setEmployeeTrailPhoto(FileBucket employeeTrailPhoto) {
        this.employeeTrailPhoto = employeeTrailPhoto;
    }

    public byte[] getPhotoStream() {
        return this.photoStream;
    }

    public void setPhotoStream(byte[] photoStream) {
        this.photoStream = photoStream;
    }

    public String getBase64EncodedForEmployeePhoto() {
        return this.base64EncodedForEmployeePhoto;
    }

    public void setBase64EncodedForEmployeePhoto(String base64EncodedForEmployeePhoto) {
        this.base64EncodedForEmployeePhoto = base64EncodedForEmployeePhoto;
    }

    public String getBase64EncodedForEmployeeTrailPhoto() {
        return this.base64EncodedForEmployeeTrailPhoto;
    }

    public void setBase64EncodedForEmployeeTrailPhoto(String base64EncodedForEmployeeTrailPhoto) {
        this.base64EncodedForEmployeeTrailPhoto = base64EncodedForEmployeeTrailPhoto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof EmployeeTrailRoomVO))
            return false;
        EmployeeTrailRoomVO other = (EmployeeTrailRoomVO) obj;
        if (id != other.id)
            return false;
        if (employeeName == null) {
            if (other.employeeName != null)
                return false;
        } else if (!employeeName.equals(other.employeeName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + employeeName + "]";
    }




}
