package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.CourseModuleDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.CourseModule;
import learning_management_system.backend.mapper.CourseModuleMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseModuleServiceImplementation implements CourseModuleService {

    @Autowired
    private CourseModuleRepository courseModuleRepository;
    @Autowired
    private CourseModuleMapper courseModuleMapper;
    @Autowired
    private ModuleContentRepository moduleContentRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CourseModuleDto createModule(CourseModuleDto moduleDTO) {
        // Ensure the course exists
        Course course = courseRepository.findById(moduleDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"+ moduleDTO.getCourseId()));

        // Validate the creator exists
        userRepository.findById(moduleDTO.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Creator not found with ID: " + moduleDTO.getCreatedByUserId()));

        // Validate module title is not empty
        if (moduleDTO.getModuleTitle() == null || moduleDTO.getModuleTitle().isEmpty()) {
            throw new IllegalArgumentException("Module title cannot be null or empty");
        }

        // Validate order is unique within the course
        boolean isOrderTaken = courseModuleRepository.findByCourseId(course.getCourseId()).stream()
                .anyMatch(module -> module.getOrder().equals(moduleDTO.getOrder()));
        if (isOrderTaken) {
            throw new IllegalArgumentException("Module order must be unique within the course");
        }

        CourseModule parentModule = null;
        if (moduleDTO.getParentModuleId() != null) {
            parentModule = courseModuleRepository.findById(moduleDTO.getParentModuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent module not found"));
        }

        Set<CourseModule> prerequisites = new HashSet<>();
        // Validate prerequisites exist
        if (moduleDTO.getPrerequisiteModuleIds() != null) {
            for (Long prerequisiteId : moduleDTO.getPrerequisiteModuleIds()) {
                if (!courseModuleRepository.existsById(prerequisiteId)) {
                    throw new RuntimeException("Prerequisite module not found with ID: " + prerequisiteId);
                }
            }
        }

        CourseModule module = courseModuleMapper.toEntity(moduleDTO);
        CourseModule savedModule = courseModuleRepository.save(module);
        return courseModuleMapper.toDto(savedModule);
    }

    @Override
    public CourseModuleDto updateModule(Long moduleId, CourseModuleDto moduleDto) {
        // Check if the module exists
        CourseModule existingModule = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));

        // Validate updates
        if (moduleDto.getCourseId() != null && !moduleDto.getCourseId().equals(existingModule.getCourse().getCourseId())) {
            throw new RuntimeException("Cannot change the course for an existing module.");
        }

        if (moduleDto.getPrerequisiteModuleIds() != null) {
            for (Long prerequisiteId : moduleDto.getPrerequisiteModuleIds()) {
                if (!courseModuleRepository.existsById(prerequisiteId)) {
                    throw new RuntimeException("Prerequisite module not found with ID: " + prerequisiteId);
                }
            }
        }

        // Validate module title is not empty
        if (moduleDto.getModuleTitle() == null || moduleDto.getModuleTitle().isEmpty()) {
            throw new IllegalArgumentException("Module title cannot be null or empty");
        }

        // Map and save the updated module
        CourseModule updatedModule = courseModuleMapper.toEntity(moduleDto);
        updatedModule.setModuleId(existingModule.getModuleId());
        updatedModule.setDateCreated(existingModule.getDateCreated());

        updatedModule = courseModuleRepository.save(updatedModule);

        return courseModuleMapper.toDto(updatedModule);
    }



    @Override
    public List<CourseModuleDto> getAllModules() {
        return courseModuleRepository.findAll().stream()
                .map(courseModuleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseModuleDto> getModulesByCourseId(Long courseId) {
        // Validate course exists
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }

        return courseModuleRepository.findByCourseCourseIdOrderByOrderAsc(courseId).stream()
                .map(courseModuleMapper::toDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<CourseModuleDto> getModulesByCourseId(Long courseId) {
//        // Ensure the course exists
//        if (!courseRepository.existsById(courseId)) {
//            throw new IllegalArgumentException("Course not found");
//        }
//        List<CourseModule> modules = courseModuleRepository.findByCourseId(courseId);
//        return modules.stream().map(courseModuleMapper::toDto).collect(Collectors.toList());
//    }


    @Override
    public CourseModuleDto getModuleById(Long moduleId) {
        CourseModule courseModule = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found."));
        return courseModuleMapper.toDto(courseModule);
    }

    @Override
    public void deleteModule(Long moduleId) {
        // Check if the module exists
        if (!courseModuleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found with ID: " + moduleId);
        }

        // Ensure module is not part of an active course
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found for deletion."));
        if (module.getCourse() != null && module.getCourse().getPublished()) {
            throw new RuntimeException("Cannot delete a module from a published course.");
        }

        courseModuleRepository.deleteById(moduleId);
    }

    @Override
    public List<CourseModuleDto> getPublishedModulesByCourseId(Long courseId) {
        // Ensure the course exists
        if (!courseRepository.existsById(courseId)) {
            throw new IllegalArgumentException("Course not found");
        }
        List<CourseModule> modules = courseModuleRepository.findPublishedModulesByCourseId(courseId);
        return modules.stream().map(courseModuleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CourseModuleDto getModuleDetails(Long moduleId) {
        // Ensure the module exists
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));
        return courseModuleMapper.toDto(module);
    }

    @Override
    public boolean arePrerequisitesMet(Long moduleId, Set<Long> completedModuleIds) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));
        return module.arePrerequisitesMet(completedModuleIds);
    }

    @Override
    public boolean isModuleComplete(Long moduleId, Set<Long> completedContentIds) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));
        return module.isComplete(completedContentIds);
    }

    @Override
    public void incrementVersion(Long moduleId) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));
        module.setVersionNumber(module.getVersionNumber() + 1);
        courseModuleRepository.save(module);
    }
//

    @Override
    public boolean isModuleReleased(Long moduleId, Set<Long> completedModuleIds) {
        // Fetch the module from the repository
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));

        // Check if the module is released
        return module.isReleased(completedModuleIds, LocalDateTime.now());
    }

    @Override
    public CourseModuleDto updatePrerequisitesAndConditions(Long moduleId, Boolean requiresPrerequisites, String releaseCondition) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with ID: " + moduleId));
        module.setRequiresPrerequisites(requiresPrerequisites);
        module.setReleaseCondition(releaseCondition);
        courseModuleRepository.save(module);
        return courseModuleMapper.toDto(module);
    }

//    @Override
//    public CourseModuleDto updateModule(Long moduleId, CourseModuleDto moduleDto) {
//        // Ensure the module exists
//        CourseModule existingModule = courseModuleRepository.findById(moduleId)
//                .orElseThrow(() -> new IllegalArgumentException("Module not found"));
//
//        // Validate module title is not empty
//        if (moduleDto.getModuleTitle() == null || moduleDto.getModuleTitle().isEmpty()) {
//            throw new IllegalArgumentException("Module title cannot be null or empty");
//        }
//
//        // Validate order is unique within the course
//        boolean isOrderTaken = courseModuleRepository.findByCourseId(existingModule.getCourse().getCourseId()).stream()
//                .anyMatch(module -> !module.getModuleId().equals(moduleId) && module.getOrder().equals(moduleDto.getOrder()));
//        if (isOrderTaken) {
//            throw new IllegalArgumentException("Module order must be unique within the course");
//        }
//
//        existingModule.setModuleTitle(moduleDto.getModuleTitle());
//        existingModule.setModuleDescription(moduleDto.getModuleDescription());
//        existingModule.setOrder(moduleDto.getOrder());
//        existingModule.setIsPublished(moduleDto.getIsPublished());
//        existingModule.setReleaseDate(moduleDto.getReleaseDate());
//        existingModule.setReleaseCondition(moduleDto.getReleaseCondition());
//        existingModule.setObjectives(moduleDto.getObjectives());
//        existingModule.setPoints(moduleDto.getPoints());
//        existingModule.setBadgeUrl(moduleDto.getBadgeUrl());
//        existingModule.setAverageCompletionTime(moduleDto.getAverageCompletionTime());
//        existingModule.setEngagementScore(moduleDto.getEngagementScore());
//
//        CourseModule updatedModule = courseModuleRepository.save(existingModule);
//        return courseModuleMapper.toDto(updatedModule);
//    }
}

