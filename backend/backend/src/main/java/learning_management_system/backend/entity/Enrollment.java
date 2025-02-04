package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.utility.EnrollmentId;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

/**
 * Represents a student's enrollment in a course within the LMS system.
 */

@CrossOrigin("*")
@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id"})
)
public class Enrollment {

    /** Unique identifier for the enrollment. */
    @EmbeddedId
    private EnrollmentId enrollmentId;

    /** Many-to-One relationship with the Course entity. */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** Many-to-One relationship with the Student entity. */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /** Date the student enrolled in the course. */
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate = LocalDate.now();

    /** Status of the enrollment (e.g., ACTIVE, COMPLETED, DROPPED). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    /** Grade the student achieved in the course. */
    @Column(name = "grade")
    private Double grade;

    /** Percentage of attendance for the course. */
    @Column(name = "attendance_percentage")
    private Double attendancePercentage;

    /** Date the course was completed by the student. */
    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;


    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;



    // Constructor
    public Enrollment() {}

    public Enrollment(EnrollmentId enrollmentId, Course course, Student student, LocalDate enrollmentDate, EnrollmentStatus status, Double grade, Double attendancePercentage, LocalDate completionDate, Boolean isDeleted, String metadata) {
        this.enrollmentId = enrollmentId;
        this.course = course;
        this.student = student;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.grade = grade;
        this.attendancePercentage = attendancePercentage;
        this.completionDate = completionDate;
        this.isDeleted = isDeleted;
        this.metadata = metadata;
    }

    // Getters and Setters
    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(Double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
