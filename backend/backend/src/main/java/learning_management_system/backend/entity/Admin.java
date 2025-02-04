package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.AdminLevel;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an admin user in the LMS system.
 * Admins have various levels and manage tenants, departments, or system-wide functionalities.
 */

@CrossOrigin("*")
@Entity
@Table(name = "admins")
public class Admin {

    /** Primary key for the Admin table, shared with the User table. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false, updatable = false)
    private Long adminId;

    /** Links this admin entry to a user entry. */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "admin_id", nullable = false) // Maps to user_id in User
    private User user;

    /** The level of the admin (e.g., SUPER_ADMIN, TENANT_ADMIN, DEPARTMENT_ADMIN). */
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level", nullable = false, length = 20)
    private AdminLevel adminLevel;

    /** The tenant this admin belongs to, if applicable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /** The department this admin manages, if applicable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /** Job title or designation for the admin. */
    @Column(name = "title", length = 100)
    private String title;

    /** The date the admin was hired or joined the organization. */
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /** Physical or virtual office location of the admin. */
    @Column(name = "office_location", length = 255)
    private String officeLocation;

    /** Admin’s accessibility preferences for inclusivity. */
    @Column(name = "accessibility_preferences", columnDefinition = "TEXT")
    private String accessibilityPreferences;

    /** Admin’s work schedule or availability. */
    @Column(name = "work_schedule", columnDefinition = "TEXT")
    private String workSchedule;

    /** Metadata for additional customizations or attributes. */
    @Column(name = "preferences", columnDefinition = "TEXT")
    private String preferences;

    /** Indicates if this admin has system-wide privileges. */
    @Column(name = "is_super_admin", nullable = false)
    private Boolean isSuperAdmin = false;

    /** Last login timestamp for this admin. */
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /** Last login IP address for this admin. */
    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;



    // Constructors
    public Admin(){}

    public Admin(Long adminId, User user, AdminLevel adminLevel, Tenant tenant, Department department, String title, LocalDate hireDate, String officeLocation, String accessibilityPreferences, String workSchedule, String preferences, Boolean isSuperAdmin, LocalDateTime lastLogin, String lastLoginIp) {
        this.adminId = adminId;
        this.user = user;
        this.adminLevel = adminLevel;
        this.tenant = tenant;
        this.department = department;
        this.title = title;
        this.hireDate = hireDate;
        this.officeLocation = officeLocation;
        this.accessibilityPreferences = accessibilityPreferences;
        this.workSchedule = workSchedule;
        this.preferences = preferences;
        this.isSuperAdmin = isSuperAdmin;
        this.lastLogin = lastLogin;
        this.lastLoginIp = lastLoginIp;
    }

    // Getters and Setter
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AdminLevel getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(AdminLevel adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getAccessibilityPreferences() {
        return accessibilityPreferences;
    }

    public void setAccessibilityPreferences(String accessibilityPreferences) {
        this.accessibilityPreferences = accessibilityPreferences;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
