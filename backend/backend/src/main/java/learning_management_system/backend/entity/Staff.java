package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.AvailabilityStatus;
import learning_management_system.backend.enums.EmploymentStatus;
import learning_management_system.backend.enums.EngagementStatus;
import learning_management_system.backend.enums.StaffType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@Entity
@Table(name = "staff")
public class Staff {

    /** Primary key for the Staff table, shared with the User table. */
    @Id
    @Column(name = "staff_id", nullable = false, updatable = false)
    private Long staffId;

    /** Links this staff entry to a user entry. */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "staff_id", nullable = false) // Maps to user_id in User
    private User user;

    /** Differentiates between Teacher and Support Staff. */
    @Enumerated(EnumType.STRING)
    @Column(name = "staff_type", nullable = false, length = 20)
    private StaffType staffType; // TEACHER or SUPPORT_STAFF

    /** Job title or designation for the staff member. */
    @Column(name = "title", length = 100)
    private String jobTitle;

    /** Area of expertise for teachers. */
    @Column(name = "subject_specialization", length = 255)
    private String subjectSpecialization;

    /** Employment details. */
    @Column(name = "date_hired", nullable = false)
    private LocalDate dateHired;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false, length = 20)
    private EmploymentStatus employmentStatus; // FULL_TIME, PART_TIME

    /** Physical or virtual office location. */
    @Column(name = "office_location", length = 255)
    private String officeLocation;

    /** Staff member’s accessibility preferences. */
    @Column(name = "accessibility_preferences", columnDefinition = "TEXT")
    private String accessibilityPreferences;

    /** Staff member’s work schedule or availability. */
    @Column(name = "work_schedule", columnDefinition = "TEXT")
    private String workSchedule;

    /** The department this staff member belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /** The tenant this staff belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
            name = "Staff_Tenant",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private Set<Tenant> linkedTenants;


    /** Courses assigned to the staff member. */
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    private List<Course> assignedCourses;

    /** Teachers linked to support staff (nullable for teachers). */
    @ManyToMany
    @JoinTable(
            name = "Staff_Teacher",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Staff> assignedTeachers;

    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills; // JSON list of skills

    @Column(name = "current_workload")
    private Integer currentWorkload;

    @Column(name = "max_workload")
    private Integer maxWorkload;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false, length = 20)
    private AvailabilityStatus availabilityStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "engagement_status", nullable = false, length = 20)
    private EngagementStatus engagementStatus;

    @Column(name = "external_reference_id", length = 255)
    private String externalReferenceId;

    /** Metadata for additional customizations or attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;

    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;


    // Constructors
    public Staff(){}

    public Staff(Long staffId, User user, StaffType staffType, String jobTitle, String subjectSpecialization, LocalDate dateHired, EmploymentStatus employmentStatus, String officeLocation, String accessibilityPreferences, String workSchedule, Department department, Tenant tenant, Set<Tenant> linkedTenants, List<Course> assignedCourses, Set<Staff> assignedTeachers, String skills, Integer currentWorkload, Integer maxWorkload, AvailabilityStatus availabilityStatus, EngagementStatus engagementStatus, String externalReferenceId, String metadata, Long lastUpdatedBy, LocalDateTime lastUpdatedAt) {
        this.staffId = staffId;
        this.user = user;
        this.staffType = staffType;
        this.jobTitle = jobTitle;
        this.subjectSpecialization = subjectSpecialization;
        this.dateHired = dateHired;
        this.employmentStatus = employmentStatus;
        this.officeLocation = officeLocation;
        this.accessibilityPreferences = accessibilityPreferences;
        this.workSchedule = workSchedule;
        this.department = department;
        this.tenant = tenant;
        this.linkedTenants = linkedTenants;
        this.assignedCourses = assignedCourses;
        this.assignedTeachers = assignedTeachers;
        this.skills = skills;
        this.currentWorkload = currentWorkload;
        this.maxWorkload = maxWorkload;
        this.availabilityStatus = availabilityStatus;
        this.engagementStatus = engagementStatus;
        this.externalReferenceId = externalReferenceId;
        this.metadata = metadata;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
    }

// Getters and Setters

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Tenant> getLinkedTenants() {
        return linkedTenants;
    }

    public void setLinkedTenants(Set<Tenant> linkedTenants) {
        this.linkedTenants = linkedTenants;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }

    public Set<Staff> getAssignedTeachers() {
        return assignedTeachers;
    }

    public void setAssignedTeachers(Set<Staff> assignedTeachers) {
        this.assignedTeachers = assignedTeachers;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getCurrentWorkload() {
        return currentWorkload;
    }

    public void setCurrentWorkload(Integer currentWorkload) {
        this.currentWorkload = currentWorkload;
    }

    public Integer getMaxWorkload() {
        return maxWorkload;
    }

    public void setMaxWorkload(Integer maxWorkload) {
        this.maxWorkload = maxWorkload;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public EngagementStatus getEngagementStatus() {
        return engagementStatus;
    }

    public void setEngagementStatus(EngagementStatus engagementStatus) {
        this.engagementStatus = engagementStatus;
    }

    public String getExternalReferenceId() {
        return externalReferenceId;
    }

    public void setExternalReferenceId(String externalReferenceId) {
        this.externalReferenceId = externalReferenceId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
