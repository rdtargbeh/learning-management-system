package learning_management_system.backend.controller;

import learning_management_system.backend.dto.CourseModuleDto;
import learning_management_system.backend.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/modules")
public class CourseModuleController {

    @Autowired
    private CourseModuleService courseModuleService;


    @PostMapping
    public ResponseEntity<CourseModuleDto> createModule(@RequestBody CourseModuleDto moduleDto) {
        CourseModuleDto createdModule = courseModuleService.createModule(moduleDto);
        return new ResponseEntity<>(createdModule, HttpStatus.CREATED);
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<CourseModuleDto> updateModule(@PathVariable Long moduleId,
                                                        @RequestBody CourseModuleDto moduleDto) {
        CourseModuleDto updatedModule = courseModuleService.updateModule(moduleId, moduleDto);
        return ResponseEntity.ok(updatedModule);
    }

    @GetMapping
    public ResponseEntity<List<CourseModuleDto>> getAllModules() {
        List<CourseModuleDto> modules = courseModuleService.getAllModules();
        return ResponseEntity.ok(modules);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseModuleDto>> getModulesByCourseId(@PathVariable Long courseId) {
        List<CourseModuleDto> modules = courseModuleService.getModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<CourseModuleDto> getModuleById(@PathVariable Long moduleId) {
        CourseModuleDto module = courseModuleService.getModuleById(moduleId);
        return ResponseEntity.ok(module);
    }


    @DeleteMapping("/{moduleId}")
    public ResponseEntity<String> deleteModule(@PathVariable Long moduleId) {
        courseModuleService.deleteModule(moduleId);
        return ResponseEntity.ok("Module deleted successfully.");
    }

    @GetMapping("/course/{courseId}/published")
    public ResponseEntity<List<CourseModuleDto>> getPublishedModulesByCourseId(@PathVariable Long courseId) {
        List<CourseModuleDto> modules = courseModuleService.getPublishedModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/details/{moduleId}")
    public ResponseEntity<CourseModuleDto> getModuleDetails(@PathVariable Long moduleId) {
        CourseModuleDto moduleDetails = courseModuleService.getModuleDetails(moduleId);
        return ResponseEntity.ok(moduleDetails);
    }

    @GetMapping("/{moduleId}/prerequisites-met")
    public ResponseEntity<Boolean> checkPrerequisites(@PathVariable Long moduleId, @RequestBody Set<Long> completedModuleIds) {
        boolean result = courseModuleService.arePrerequisitesMet(moduleId, completedModuleIds);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{moduleId}/complete")
    public ResponseEntity<Boolean> checkCompletion(@PathVariable Long moduleId, @RequestBody Set<Long> completedContentIds) {
        boolean result = courseModuleService.isModuleComplete(moduleId, completedContentIds);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{moduleId}/increment-version")
    public ResponseEntity<Void> incrementVersion(@PathVariable Long moduleId) {
        courseModuleService.incrementVersion(moduleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Checks if a module is released based on prerequisites and other conditions.
     *
     * @param moduleId the ID of the module.
     * @param completedModuleIds IDs of completed modules by the user.
     * @return true if the module is released, false otherwise.
     */
    @PostMapping("/{moduleId}/is-released")
    public ResponseEntity<Boolean> isModuleReleased(
            @PathVariable Long moduleId, @RequestBody Set<Long> completedModuleIds) {
        boolean isReleased = courseModuleService.isModuleReleased(moduleId, completedModuleIds);
        return ResponseEntity.ok(isReleased);
    }

    /**
     * Updates the prerequisites and release conditions for a module.
     *
     * @param moduleId the ID of the module.
     * @param requiresPrerequisites true if prerequisites are required.
     * @param releaseCondition the release condition as a string.
     * @return the updated module.
     */
    @PutMapping("/{moduleId}/update-conditions")
    public ResponseEntity<CourseModuleDto> updatePrerequisitesAndConditions(
            @PathVariable Long moduleId,
            @RequestParam Boolean requiresPrerequisites,
            @RequestParam String releaseCondition) {
        CourseModuleDto updatedModule = courseModuleService.updatePrerequisitesAndConditions(
                moduleId, requiresPrerequisites, releaseCondition);
        return ResponseEntity.ok(updatedModule);
    }
}

