package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.enums.ProfileVisibility;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a student in the LMS system.
 * A student inherits core user details from the User entity
 * and is associated with a specific tenant through the User-Tenant relationship.
 */

@CrossOrigin("*")
@Entity
@Table(name = "students")
public class Student {


    /** Unique identifier for the student, mapped to user_id. */
    @Id
    @Column(name = "student_id", nullable = false, updatable = false)
    private Long studentId;

    /** Links this staff entry to a user entry.
     * One-to-One relationship with the User entity.
     * */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "student_id", nullable = false) // Maps to user_id in User
    private User user;

    /** Academic classification of the student (e.g., Freshman, Senior). */
    @Column(name = "grade_level", length = 50)
    private String gradeLevel;

    /** Field of study for the student (e.g., Computer Science). */
    @Column(name = "major", length = 100)
    private String major;

    /** Date of enrollment. */
    @Column(name = "date_enrolled", nullable = false)
    private LocalDate dateEnrolled;


    /** Enrollment status (e.g., ENROLLED, GRADUATED). */
    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_status", nullable = false, length = 20)
    private EnrollmentStatus enrollmentStatus;

    /** Student biography or profile information. */
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    /** Overall progress percentage in courses. */
    @Column(name = "overall_progress", nullable = false)
    private Double overallProgress = 0.0;

    /** Gamification points for leaderboard ranking. */
    @Column(name = "leaderboard_points", nullable = false)
    private Integer leaderboardPoints = 0;

    /** List of courses the student is enrolled in. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "enrollments",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> enrolledCourses = new HashSet<>();


    /** Groups or clubs the student is part of. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_groups",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<StudentGroup> studentGroups = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_badges",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private Set<Badge> badges = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_advisors",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> advisors = new HashSet<>();

    @Column(name = "attendance", columnDefinition = "TEXT")
    private String attendance;

    /** Expected or actual graduation date. */
    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_visibility", nullable = false)
    private ProfileVisibility profileVisibility = ProfileVisibility.TENANT_ONLY;

    @Column(name = "custom_attributes", columnDefinition = "TEXT")
    private String customAttributes;

    /** Metadata for tenant-specific or custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** The tenant this staff belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    // Constructor
    public Student(){}

    public Student(Long studentId, User user, String gradeLevel, String major, LocalDate dateEnrolled, EnrollmentStatus enrollmentStatus, String bio, Double overallProgress, Integer leaderboardPoints, Set<Course> enrolledCourses, Set<StudentGroup> studentGroups, Set<Badge> badges, Set<Staff> advisors, String attendance, LocalDate graduationDate, ProfileVisibility profileVisibility, String customAttributes, String metadata, Tenant tenant) {
        this.studentId = studentId;
        this.user = user;
        this.gradeLevel = gradeLevel;
        this.major = major;
        this.dateEnrolled = dateEnrolled;
        this.enrollmentStatus = enrollmentStatus;
        this.bio = bio;
        this.overallProgress = overallProgress;
        this.leaderboardPoints = leaderboardPoints;
        this.enrolledCourses = enrolledCourses;
        this.studentGroups = studentGroups;
        this.badges = badges;
        this.advisors = advisors;
        this.attendance = attendance;
        this.graduationDate = graduationDate;
        this.profileVisibility = profileVisibility;
        this.customAttributes = customAttributes;
        this.metadata = metadata;
        this.tenant = tenant;
    }

// Getter and Setter


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public LocalDate getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(LocalDate dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Double getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(Double overallProgress) {
        this.overallProgress = overallProgress;
    }

    public Integer getLeaderboardPoints() {
        return leaderboardPoints;
    }

    public void setLeaderboardPoints(Integer leaderboardPoints) {
        this.leaderboardPoints = leaderboardPoints;
    }

    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Set<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Set<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public Set<Staff> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(Set<Staff> advisors) {
        this.advisors = advisors;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public ProfileVisibility getProfileVisibility() {
        return profileVisibility;
    }

    public void setProfileVisibility(ProfileVisibility profileVisibility) {
        this.profileVisibility = profileVisibility;
    }

    public String getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(String customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}