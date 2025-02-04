package learning_management_system.backend.mapper;

import learning_management_system.backend.entity.Course;
import learning_management_system.backend.repository.CourseRepository;
import org.springframework.stereotype.Component;

// Helper class for Course to resolve issues in AssessmentMapper Interface
@Component
public class CourseResolver {

    private final CourseRepository courseRepository;

    public CourseResolver(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course findById(Long courseId) {
        if (courseId == null) return null;
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }
}

