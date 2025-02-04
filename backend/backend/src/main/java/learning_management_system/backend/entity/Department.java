package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@CrossOrigin("*")
@Entity
@Table (name = "departments")
public class Department {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "department_id", nullable = false, length = 50)
    private Long departmentId;

    @Column (name = "department_name", nullable = false, length = 50)
    private String departmentName;

    @Column (name = "department_description", nullable = false, length = 200)
    private String departmentDescription;

    @Column (name = "department_email", nullable = false, length = 50)
    private String departmentEmail;

    @Column (name = "phone_number", nullable = false, length = 50)
    private String departmentPhoneNumber;

    @Column (name = "department_address", nullable = true, length = 200)
    private String departmentAddress;

    @Column (name = "department_head", nullable = false, length = 50)
    private  String departmentHead;

    /** Timestamp when the course was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp when the course was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();


    // Constructor
    public Department(){}
    public Department(Long departmentId, String departmentName, String departmentDescription, String departmentEmail, String departmentPhoneNumber, String departmentAddress, String departmentHead, Date dateCreated, Date dateUpdated) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.departmentEmail = departmentEmail;
        this.departmentPhoneNumber = departmentPhoneNumber;
        this.departmentAddress = departmentAddress;
        this.departmentHead = departmentHead;
        this.dateCreated = dateCreated;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
