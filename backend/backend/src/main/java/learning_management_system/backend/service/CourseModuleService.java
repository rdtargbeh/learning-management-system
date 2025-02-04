package learning_management_system.backend.service;

import learning_management_system.backend.dto.CourseModuleDto;

import java.util.List;
import java.util.Set;

public interface CourseModuleService {
    List<CourseModuleDto> getAllModules();

    List<CourseModuleDto> getModulesByCourseId(Long courseId);

    CourseModuleDto getModuleById(Long moduleId);

    CourseModuleDto createModule(CourseModuleDto moduleDto);

    CourseModuleDto updateModule(Long moduleId, CourseModuleDto moduleDto);

    void deleteModule(Long moduleId);

    List<CourseModuleDto> getPublishedModulesByCourseId(Long courseId);

    CourseModuleDto getModuleDetails(Long moduleId);

    boolean arePrerequisitesMet(Long moduleId, Set<Long> completedModuleIds);

    boolean isModuleComplete(Long moduleId, Set<Long> completedContentIds);

    void incrementVersion(Long moduleId);

    /**
     * Checks if a module is released based on the current time.
     *
     * @param moduleId the ID of the module.
     * @return true if the module is released, false otherwise.
     */
    boolean isModuleReleased(Long moduleId, Set<Long> completedModuleIds);

    CourseModuleDto updatePrerequisitesAndConditions(Long moduleId, Boolean requiresPrerequisites, String releaseCondition);

}

