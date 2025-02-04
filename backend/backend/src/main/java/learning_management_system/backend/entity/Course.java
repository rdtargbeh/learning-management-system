package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.CourseStatus;
import learning_management_system.backend.enums.CourseVisibility;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
/**
 * Represents a course in the LMS system. A course includes content, assignments,
 * and relationships with staff, students, and tenants, along with its metadata
 * and lifecycle management attributes.
 */

@CrossOrigin("*")
@Entity
@Table (name = "courses")
public class Course {

    /** Unique identifier for the course. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    /** Unique code for the course (e.g., CS101). */
    @Column(name = "course_code", nullable = false, unique = true, length = 50)
    private String courseCode;

    /** Full title of the course. */
    @Column(name = "course_title", nullable = false, length = 255)
    private String courseTitle;

    /** Detailed description of the course. */
    @Column(name = "course_description", columnDefinition = "TEXT")
    private String courseDescription;

    /** Status of the course (e.g., ACTIVE, ARCHIVED, COMPLETED). */
    @Enumerated(EnumType.STRING)
    @Column(name = "course_status", nullable = false, length = 20)
    private CourseStatus courseStatus;

    /** Indicates whether the course is published and visible to students. */
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    /** The tenant this course belongs to, ensuring multi-tenancy. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /** The department associated with this course. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    /** The start date of the course. */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** The end date of the course. */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /** The enrollment start date for the course. */
    @Column(name = "enrollment_start", nullable = false)
    private LocalDate enrollmentStart;

    /** The enrollment end date for the course. */
    @Column(name = "enrollment_end", nullable = false)
    private LocalDate enrollmentEnd;

    /** Maximum number of enrollments allowed in the course. */
    @Column(name = "max_enrollments")
    private Integer maxEnrollments;

    /** Visibility settings for the course (e.g., PUBLIC, PRIVATE, TENANT_ONLY). */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private CourseVisibility visibility;

    /** Tags associated with the course for categorization or searching. */
    @ElementCollection
    @CollectionTable(name = "course_tags", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag", length = 50)
    private Set<String> tags;

    /** Staff (e.g., instructors) managing this course. */
    @ManyToMany
    @JoinTable(
            name = "course_staff",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> staff;

    /** Students enrolled in this course. */
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students;

    /** Assignments associated with this course. */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;

    /** Modules defining the structure and content of this course. */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseModule> modules;

    /** Announcements related to this course. */
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Announcement> announcements;

    /** Timestamp when the course was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp when the course was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** The user who created the course. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;


    // Constructor
    public Course(){}

    public Course(Long courseId, String courseCode, String courseTitle, String courseDescription, CourseStatus courseStatus,
                  Boolean isPublished, boolean isArchived, Tenant tenant, Department department, LocalDate startDate,
                  LocalDate endDate, LocalDate enrollmentStart, LocalDate enrollmentEnd, Integer maxEnrollments,
                  CourseVisibility visibility, Set<String> tags, Set<Staff> staff, Set<User> students, List<Assignment> assignments,
                  List<CourseModule> modules, List<Announcement> announcements, Date dateCreated, Date dateUpdated,
                  User createdBy, String metadata) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseStatus = courseStatus;
        this.isPublished = isPublished;
        this.isArchived = isArchived;
        this.tenant = tenant;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentStart = enrollmentStart;
        this.enrollmentEnd = enrollmentEnd;
        this.maxEnrollments = maxEnrollments;
        this.visibility = visibility;
        this.tags = tags;
        this.staff = staff;
        this.students = students;
        this.assignments = assignments;
        this.modules = modules;
        this.announcements = announcements;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.metadata = metadata;
    }

    // Getter and Setter
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEnrollmentStart() {
        return enrollmentStart;
    }

    public void setEnrollmentStart(LocalDate enrollmentStart) {
        this.enrollmentStart = enrollmentStart;
    }

    public LocalDate getEnrollmentEnd() {
        return enrollmentEnd;
    }

    public void setEnrollmentEnd(LocalDate enrollmentEnd) {
        this.enrollmentEnd = enrollmentEnd;
    }

    public Integer getMaxEnrollments() {
        return maxEnrollments;
    }

    public void setMaxEnrollments(Integer maxEnrollments) {
        this.maxEnrollments = maxEnrollments;
    }

    public CourseVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CourseVisibility visibility) {
        this.visibility = visibility;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<CourseModule> getModules() {
        return modules;
    }

    public void setModules(List<CourseModule> modules) {
        this.modules = modules;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
