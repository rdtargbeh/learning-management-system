package learning_management_system.backend.dto;

import java.util.Date;

public class DepartmentDto {

    private Long departmentId;
    private String departmentName;
    private String departmentDescription;
    private  String departmentEmail;
    private  String departmentPhoneNumber;
    private String departmentAddress;
    private String departmentHead;
    private Date dateUpdated;

    // Constructor

    public DepartmentDto() {}

    public DepartmentDto(Long departmentId, String departmentName, String departmentDescription, String departmentEmail,
                         String departmentPhoneNumber, String departmentAddress, String departmentHead, Date dateUpdated) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.departmentEmail = departmentEmail;
        this.departmentPhoneNumber = departmentPhoneNumber;
        this.departmentAddress = departmentAddress;
        this.departmentHead = departmentHead;
        this.dateUpdated = dateUpdated;
    }

// Getter and Setter
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public String getDepartmentEmail() {
        return departmentEmail;
    }

    public void setDepartmentEmail(String departmentEmail) {
        this.departmentEmail = departmentEmail;
    }

    public String getDepartmentPhoneNumber() {
        return departmentPhoneNumber;
    }

    public void setDepartmentPhoneNumber(String departmentPhoneNumber) {
        this.departmentPhoneNumber = departmentPhoneNumber;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
