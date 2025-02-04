package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.AssignmentDto;
import learning_management_system.backend.entity.Assignment;
import learning_management_system.backend.entity.Attachment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AssignmentMapper {

    /**
     * Converts an `Assignment` entity to an `AssignmentDto`.
     * @param assignment The `Assignment` entity.
     * @return The corresponding `AssignmentDto`.
     */
    public AssignmentDto toDto(Assignment assignment) {
        if (assignment == null) {
            return null;
        }

        AssignmentDto dto = new AssignmentDto();
        dto.setAssignmentId(assignment.getAssignmentId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        dto.setAvailableFrom(assignment.getAvailableFrom());
        dto.setAvailableUntil(assignment.getAvailableUntil());
        dto.setPublished(assignment.getPublished());
        dto.setActive(assignment.getActive());
        dto.setEnablePeerReviews(assignment.getEnablePeerReviews());
        dto.setCompletionRate(assignment.getCompletionRate());
        dto.setAverageGrade(assignment.getAverageGrade());
        dto.setSubmissionType(assignment.getSubmissionType());
        dto.setAssignmentType(assignment.getAssignmentType());
        dto.setGradingId(assignment.getGrading() != null ? assignment.getGrading().getGradingId() : null);
        dto.setMaxAttempts(assignment.getMaxAttempts());
        dto.setGracePeriodMinutes(assignment.getGracePeriodMinutes());
        dto.setVisibility(assignment.getVisibility());
        dto.setCourseId(assignment.getCourse() != null ? assignment.getCourse().getCourseId() : null);
        dto.setModuleId(assignment.getModule() != null ? assignment.getModule().getModuleId() : null);
        dto.setStudentGroupId(assignment.getStudentGroup() != null ? assignment.getStudentGroup().getGroupId() : null);
        dto.setAssignedStudentIds(assignment.getAssignedStudents() != null ?
                assignment.getAssignedStudents().stream()
                        .map(student -> student.getUserId()) // Explicit lambda to avoid method reference issues
                        .collect(Collectors.toSet()) : null);

        dto.setInstructions(assignment.getInstructions());
        dto.setStatus(assignment.getStatus());
        dto.setGradingCompleted(assignment.getGradingCompleted());
        dto.setCreatedBy(assignment.getCreatedBy() != null ? assignment.getCreatedBy().getUserId() : null);
        dto.setMetadata(assignment.getMetadata());
        dto.setAttachmentIds(assignment.getAttachments() != null ?
                assignment.getAttachments().stream().map(Attachment::getAttachmentId).collect(Collectors.toList()) : null);

        return dto;
    }

    /**
     * Converts an `AssignmentDto` to an `Assignment` entity.
     * @param dto The `AssignmentDto`.
     * @return The corresponding `Assignment` entity.
     */
    public Assignment toEntity(AssignmentDto dto) {
        if (dto == null) {
            return null;
        }

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(dto.getAssignmentId());
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        assignment.setAvailableFrom(dto.getAvailableFrom());
        assignment.setAvailableUntil(dto.getAvailableUntil());
        assignment.setPublished(dto.getPublished());
        assignment.setActive(dto.getActive());
        assignment.setEnablePeerReviews(dto.getEnablePeerReviews());
        assignment.setCompletionRate(dto.getCompletionRate());
        assignment.setAverageGrade(dto.getAverageGrade());
        assignment.setSubmissionType(dto.getSubmissionType());
        assignment.setAssignmentType(dto.getAssignmentType());
        assignment.setMaxAttempts(dto.getMaxAttempts());
        assignment.setGracePeriodMinutes(dto.getGracePeriodMinutes());
        assignment.setVisibility(dto.getVisibility());
        assignment.setInstructions(dto.getInstructions());
        assignment.setStatus(dto.getStatus());
        assignment.setGradingCompleted(dto.getGradingCompleted());
        assignment.setMetadata(dto.getMetadata());

        return assignment;
    }
}

