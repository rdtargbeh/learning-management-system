package learning_management_system.backend.dto;

import learning_management_system.backend.enums.EnrollmentStatus;

import java.time.LocalDate;

public class EnrollmentDto {
    private Long enrollmentId;
    private Long courseId;
    private Long studentId;
    private Double grade;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;
    private Double attendancePercentage;
    private LocalDate completionDate;
    private String metadata;

    // Constructors
    public EnrollmentDto(){}

    public EnrollmentDto(Long enrollmentId, Long courseId, Long studentId, Double grade, LocalDate enrollmentDate,
                         EnrollmentStatus status, Double attendancePercentage, LocalDate completionDate, String metadata) {
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.attendancePercentage = attendancePercentage;
        this.completionDate = completionDate;
        this.metadata = metadata;
    }

// Getters and Setters
    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
