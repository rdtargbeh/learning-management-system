package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.ModuleContentDto;
import learning_management_system.backend.service.ModuleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * REST Controller for ModuleContent.
 */

@RestController
@RequestMapping("/api/module-contents")
public class ModuleContentController {

    @Autowired
    private ModuleContentService moduleContentService;

    @PostMapping
    public ResponseEntity<ModuleContentDto> createContent(@RequestBody ModuleContentDto contentDto) {
        ModuleContentDto createdContent = moduleContentService.createContent(contentDto);
        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<ModuleContentDto> updateContent(@PathVariable Long contentId, @RequestBody ModuleContentDto contentDto) {
        return ResponseEntity.ok(moduleContentService.updateContent(contentId, contentDto));
    }

    @GetMapping
    public ResponseEntity<List<ModuleContentDto>> getAllContents() {
        List<ModuleContentDto> contents = moduleContentService.getAllContents();
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<ModuleContentDto>> getContentsByModuleId(@PathVariable Long moduleId) {
        List<ModuleContentDto> contents = moduleContentService.getContentsByModuleId(moduleId);
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ModuleContentDto> getContentById(@PathVariable Long contentId) {
        return ResponseEntity.ok(moduleContentService.getContentById(contentId));
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<String> deleteContent(@PathVariable Long contentId) {
        moduleContentService.deleteContent(contentId);
        return ResponseEntity.ok("Content deleted successfully.");
    }

    @PostMapping("/{contentId}/attachments")
    public ResponseEntity<AttachmentDto> addAttachmentToAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AttachmentDto attachmentDto) {
        return ResponseEntity.ok(moduleContentService.addAttachmentToModuleContent(assignmentId, attachmentDto));
    }

    @GetMapping("/{contentId}/attachments")
    public ResponseEntity<List<AttachmentDto>> getAttachmentsByModuleContent(@PathVariable Long contentId) {
        List<AttachmentDto> attachments = moduleContentService.getAttachmentsByModuleContent(contentId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/released")
    public ResponseEntity<List<ModuleContentDto>> getReleasedContent() {
        return ResponseEntity.ok(moduleContentService.getReleasedContent());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ModuleContentDto>> getAvailableContent(@RequestParam LocalDateTime currentTime) {
        return ResponseEntity.ok(moduleContentService.getAvailableContent(currentTime));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ModuleContentDto>> searchByTag(@RequestParam String tag) {
        return ResponseEntity.ok(moduleContentService.searchByTag(tag));
    }

    /**
     * Check if a specific content is released based on completion status of prerequisites.
     *
     * @param contentId           the ID of the content to check.
     * @param completedContentIds a list of completed content IDs (prerequisites).
     * @return a response indicating if the content is released.
     */
    @GetMapping("/{contentId}/is-released")
    public ResponseEntity<Boolean> isContentReleased(
            @PathVariable Long contentId,
            @RequestParam Set<Long> completedContentIds) {
        boolean isReleased = moduleContentService.isContentReleased(contentId, completedContentIds);
        return ResponseEntity.ok(isReleased);
    }

    /**
     * Check if a specific content is marked as complete.
     *
     * @param contentId           the ID of the content to check.
     * @param completedContentIds a list of completed content IDs.
     * @return a response indicating if the content is complete.
     */
    @GetMapping("/{contentId}/is-complete")
    public ResponseEntity<Boolean> isContentComplete(
            @PathVariable Long contentId,
            @RequestParam Set<Long> completedContentIds) {
        boolean isComplete = moduleContentService.isContentComplete(contentId, completedContentIds);
        return ResponseEntity.ok(isComplete);
    }

    // Add attachment method
//    @PostMapping("/{contentId}/attachments")
//    public ResponseEntity<Void> addAttachmentToModuleContents(
//            @PathVariable Long contentId, @RequestBody AttachmentDto attachmentDto) {
//        moduleContentService.addAttachmentToModuleContent(contentId, attachmentDto);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }


//    @GetMapping("/visible")
//    public ResponseEntity<List<ModuleContentDto>> getVisibleContents(@RequestParam Boolean isVisible) {
//        return ResponseEntity.ok(moduleContentService.getVisibleContents(isVisible));
//    }
}
