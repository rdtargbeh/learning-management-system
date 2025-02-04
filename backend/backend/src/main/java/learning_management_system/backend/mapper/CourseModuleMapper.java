package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.CourseModuleDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.CourseModule;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.CourseModuleRepository;
import learning_management_system.backend.repository.CourseRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseModuleMapper {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseModuleRepository courseModuleRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Converts a CourseModule entity to a CourseModuleDto.
     */
    public CourseModuleDto toDto(CourseModule module) {
        CourseModuleDto dto = new CourseModuleDto();
        dto.setModuleId(module.getModuleId());
        dto.setModuleTitle(module.getModuleTitle());
        dto.setModuleDescription(module.getModuleDescription());
        dto.setCourseId(module.getCourse() != null ? module.getCourse().getCourseId() : null);
        dto.setOrder(module.getOrder());
        dto.setPublished(module.getPublished());
        dto.setParentModuleId(module.getParentModule() != null ? module.getParentModule().getModuleId() : null);
        dto.setPrerequisiteModuleIds(module.getPrerequisites() != null
                ? module.getPrerequisites().stream().map(CourseModule::getModuleId).collect(Collectors.toSet())
                : null);
        dto.setReleaseDate(module.getReleaseDate());
        dto.setReleaseCondition(module.getReleaseCondition());
        dto.setObjectives(module.getObjectives());
        dto.setPoints(module.getPoints());
        dto.setBadgeUrl(module.getBadgeUrl());
        dto.setAverageCompletionTime(module.getAverageCompletionTime());
        dto.setEngagementScore(module.getEngagementScore());
        dto.setCreatedByUserId(module.getCreatedBy() != null ? module.getCreatedBy().getUserId() : null);
        dto.setSubModuleIds(module.getSubModules() != null
                ? module.getSubModules().stream().map(CourseModule::getModuleId).collect(Collectors.toSet())
                : null);
        dto.setMetadata(module.getMetadata());
        return dto;
    }

    /**
     * Converts a CourseModuleDto to a CourseModule entity.
     */
    public CourseModule toEntity(CourseModuleDto dto) {
        CourseModule module = new CourseModule();

        // Set course reference
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));
            module.setCourse(course);
        }

        // Set parent module reference
        if (dto.getParentModuleId() != null) {
            CourseModule parentModule = courseModuleRepository.findById(dto.getParentModuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent module not found with ID: " + dto.getParentModuleId()));
            module.setParentModule(parentModule);
        }

        // Set prerequisite modules
        if (dto.getPrerequisiteModuleIds() != null) {
            Set<CourseModule> prerequisites = courseModuleRepository.findAllById(dto.getPrerequisiteModuleIds())
                    .stream()
                    .collect(Collectors.toSet());
            module.setPrerequisites(prerequisites);
        }

        // Map other fields
        module.setModuleTitle(dto.getModuleTitle());
        module.setModuleDescription(dto.getModuleDescription());
        module.setOrder(dto.getOrder());
        module.setPublished(dto.getPublished());
        module.setReleaseDate(dto.getReleaseDate());
        module.setReleaseCondition(dto.getReleaseCondition());
        module.setObjectives(dto.getObjectives());
        module.setPoints(dto.getPoints());
        module.setBadgeUrl(dto.getBadgeUrl());
        module.setAverageCompletionTime(dto.getAverageCompletionTime());
        module.setEngagementScore(dto.getEngagementScore());

        // Set createdBy reference
        if (dto.getCreatedByUserId() != null) {
            User createdBy = userRepository.findById(dto.getCreatedByUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getCreatedByUserId()));
            module.setCreatedBy(createdBy);
        }

        module.setMetadata(dto.getMetadata());
        return module;
    }


//    // Map CourseModule entity to CourseModuleDto
//    @Mapping(source = "moduleId", target = "moduleId")
//    @Mapping(source = "moduleTitle", target = "moduleTitle")
//    @Mapping(source = "moduleDescription", target = "moduleDescription")
//    @Mapping(source = "course.courseId", target = "courseId")
//    @Mapping(source = "order", target = "order")
//    @Mapping(source = "published", target = "published")
//    @Mapping(source = "parentModule.moduleId", target = "parentModuleId")
//    @Mapping(source = "prerequisiteModules", target = "prerequisiteModuleIds", qualifiedByName = "mapModulesToIds")
//    @Mapping(source = "releaseDate", target = "releaseDate")
//    @Mapping(source = "releaseCondition", target = "releaseCondition")
//    @Mapping(source = "objectives", target = "objectives")
//    @Mapping(source = "points", target = "points")
//    @Mapping(source = "badgeUrl", target = "badgeUrl")
//    @Mapping(source = "averageCompletionTime", target = "averageCompletionTime")
//    @Mapping(source = "engagementScore", target = "engagementScore")
//    @Mapping(source = "createdBy.userId", target = "createdByUserId")
//    @Mapping(source = "subModules", target = "subModuleIds", qualifiedByName = "mapModulesToIds")
//    @Mapping(source = "metadata", target = "metadata")
//    CourseModuleDto toDto(CourseModule courseModule);
//
//    // Map CourseModuleDto to CourseModule entity
//    @Mapping(target = "moduleId", source = "moduleId")
//    @Mapping(target = "moduleTitle", source = "moduleTitle")
//    @Mapping(target = "moduleDescription", source = "moduleDescription")
//    @Mapping(target = "course.courseId", source = "courseId")
//    @Mapping(target = "order", source = "order")
//    @Mapping(target = "published", source = "published")
//    @Mapping(target = "parentModule.moduleId", source = "parentModuleId")
//    @Mapping(target = "prerequisiteModules", ignore = true) // Handled separately in service
//    @Mapping(target = "releaseDate", source = "releaseDate")
//    @Mapping(target = "releaseCondition", source = "releaseCondition")
//    @Mapping(target = "objectives", source = "objectives")
//    @Mapping(target = "points", source = "points")
//    @Mapping(target = "badgeUrl", source = "badgeUrl")
//    @Mapping(target = "averageCompletionTime", source = "averageCompletionTime")
//    @Mapping(target = "engagementScore", source = "engagementScore")
//    @Mapping(target = "createdBy.userId", source = "createdByUserId")
//    @Mapping(target = "subModules", ignore = true) // Handled separately in service
//    @Mapping(target = "metadata", source = "metadata")
//    CourseModule toEntity(CourseModuleDto courseModuleDto);

    // Utility method to map prerequisite or submodules to IDs
//    @Named("mapModulesToIds")
//    default Set<Long> mapModulesToIds(Set<CourseModule> modules) {
//        return modules != null ? modules.stream().map(CourseModule::getModuleId).collect(Collectors.toSet()) : null;
//    }


}
