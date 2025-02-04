package learning_management_system.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Admin entity.
 * Used to transfer admin data between layers.
 */

public class AdminDto {

    /** Unique identifier for the admin. */
    private Long adminId;

    /** User details mapped to the admin. */
    private Long userId;
    private String userName;
    private String email;

    /** The level of the admin (e.g., SUPER_ADMIN, TENANT_ADMIN, DEPARTMENT_ADMIN). */
    private String adminLevel;

    /** Tenant ID, if applicable. */
    private Long tenantId;

    /** Department ID, if applicable. */
    private Long departmentId;

    /** Job title or designation for the admin. */
    private String title;

    /** The date the admin was hired or joined the organization. */
    private LocalDate hireDate;

    /** Physical or virtual office location of the admin. */
    private String officeLocation;

    /** Admin’s accessibility preferences for inclusivity. */
    private String accessibilityPreferences;

    /** Admin’s work schedule or availability. */
    private String workSchedule;

    /** Metadata for additional customizations or attributes. */
    private String preferences;

    /** Indicates if this admin has system-wide privileges. */
    private Boolean isSuperAdmin;

    /** Last login timestamp for this admin. */
    private LocalDateTime lastLogin;

    /** Last login IP address for this admin. */
    private String lastLoginIp;



    // Constructors
    public AdminDto(){}

    public AdminDto(Long adminId, Long userId, String userName, String email, String adminLevel, Long tenantId, Long departmentId, String title, LocalDate hireDate, String officeLocation, String accessibilityPreferences, String workSchedule, String preferences, Boolean isSuperAdmin, LocalDateTime lastLogin, String lastLoginIp) {
        this.adminId = adminId;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.adminLevel = adminLevel;
        this.tenantId = tenantId;
        this.departmentId = departmentId;
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

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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

