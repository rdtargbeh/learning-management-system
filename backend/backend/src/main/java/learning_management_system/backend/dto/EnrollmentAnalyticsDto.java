package learning_management_system.backend.dto;

public class EnrollmentAnalyticsDto {

    private Long activeEnrollments;
    private Long completedEnrollments;
    private Long droppedEnrollments;

    // Constructor
    public EnrollmentAnalyticsDto(Long activeEnrollments, Long completedEnrollments, Long droppedEnrollments) {
        this.activeEnrollments = activeEnrollments;
        this.completedEnrollments = completedEnrollments;
        this.droppedEnrollments = droppedEnrollments;
    }

    // Getters
    public Long getActiveEnrollments() {
        return activeEnrollments;
    }

    public Long getCompletedEnrollments() {
        return completedEnrollments;
    }

    public Long getDroppedEnrollments() {
        return droppedEnrollments;
    }

    // Setters
    public void setActiveEnrollments(Long activeEnrollments) {
        this.activeEnrollments = activeEnrollments;
    }

    public void setCompletedEnrollments(Long completedEnrollments) {
        this.completedEnrollments = completedEnrollments;
    }

    public void setDroppedEnrollments(Long droppedEnrollments) {
        this.droppedEnrollments = droppedEnrollments;
    }
}
