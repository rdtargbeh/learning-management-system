package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradeBookDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.GradeBook;
import learning_management_system.backend.repository.CourseRepository;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
public class GradeBookMapper {

    private final CourseRepository courseRepository;

    public GradeBookMapper(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Converts a GradeBook entity to a GradeBookDto.
     */
    public GradeBookDto toDto(GradeBook gradeBook) {
        if (gradeBook == null) {
            return null;
        }

        GradeBookDto dto = new GradeBookDto();
        dto.setGradeBookId(gradeBook.getGradeBookId());
        dto.setCourseId(gradeBook.getCourse() != null ? gradeBook.getCourse().getCourseId() : null);
        dto.setCourseName(gradeBook.getCourse() != null ? gradeBook.getCourse().getCourseTitle() : null);
        dto.setGradingPolicy(gradeBook.getGradingPolicy());
        dto.setGradingScale(gradeBook.getGradingScale());
        dto.setTotalCoursePoints(gradeBook.getTotalCoursePoints());
        dto.setCurrentPointsAchieved(gradeBook.getCurrentPointsAchieved());
        dto.setTotalWeight(gradeBook.getTotalWeight());
        dto.setCompletionPercentage(gradeBook.getCompletionPercentage());
        dto.setCustomGradingScale(gradeBook.getCustomGradingScale());
        dto.setEnableNormalization(gradeBook.getEnableNormalization());
        dto.setMetadata(gradeBook.getMetadata());
        dto.setDateCreated(gradeBook.getDateCreated());
        dto.setDateUpdated(gradeBook.getDateUpdated());
        return dto;
    }

    /**
     * Converts a GradeBookDto to a GradeBook entity.
     */
    public GradeBook toEntity(GradeBookDto dto) {
        if (dto == null) {
            return null;
        }

        GradeBook gradeBook = new GradeBook();
        gradeBook.setGradeBookId(dto.getGradeBookId());
        gradeBook.setGradingPolicy(dto.getGradingPolicy());
        gradeBook.setGradingScale(dto.getGradingScale());
        gradeBook.setTotalCoursePoints(dto.getTotalCoursePoints());
        gradeBook.setCurrentPointsAchieved(dto.getCurrentPointsAchieved());
        gradeBook.setTotalWeight(dto.getTotalWeight());
        gradeBook.setCompletionPercentage(dto.getCompletionPercentage());
        gradeBook.setCustomGradingScale(dto.getCustomGradingScale());
        gradeBook.setEnableNormalization(dto.getEnableNormalization());
        gradeBook.setMetadata(dto.getMetadata());
        gradeBook.setDateCreated(dto.getDateCreated());
        gradeBook.setDateUpdated(dto.getDateUpdated());

        // Fetch and set course reference
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));
            gradeBook.setCourse(course);
        }

        return gradeBook;
    }

}

