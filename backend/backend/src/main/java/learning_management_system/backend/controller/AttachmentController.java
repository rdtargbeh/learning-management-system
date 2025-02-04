package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * Retrieve all attachments linked to a specific entity.
     *
     * @param linkedEntityType The type of the linked entity.
     * @param linkedEntityId   The ID of the linked entity.
     * @return List of attachments.
     */
    @GetMapping("/entity")
    public ResponseEntity<List<AttachmentDto>> getAttachments(
            @RequestParam Long linkedEntityId,
            @RequestParam String linkedEntityType) {
        List<AttachmentDto> attachments = attachmentService.getAttachments(linkedEntityId, linkedEntityType);
        return ResponseEntity.ok(attachments);
    }

    /**
     * Upload an attachment and link it to a specific entity.
     *
     * @param attachmentDto The attachment to upload.
     * @return The saved attachment.
     */

    @PostMapping("/{linkedEntityId}/{linkedEntityType}")
    public ResponseEntity<AttachmentDto> uploadAttachment(
            @PathVariable Long linkedEntityId,
            @PathVariable String linkedEntityType,
            @RequestBody AttachmentDto attachmentDto) {
        return ResponseEntity.ok(attachmentService.uploadAttachment(linkedEntityId, linkedEntityType, attachmentDto));
    }

    /**
     * Update an existing attachment.
     *
     * @param attachmentId  The ID of the attachment to update.
     * @param attachmentDto The updated attachment details.
     * @return The updated attachment.
     */
    @PutMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDto> updateAttachment(
            @PathVariable Long attachmentId,
            @RequestBody AttachmentDto attachmentDto) {
        AttachmentDto updatedAttachment = attachmentService.updateAttachment(attachmentId, attachmentDto);
        return ResponseEntity.ok(updatedAttachment);
    }

    /**
     * Delete all attachments linked to a specific entity.
     *
     * @param linkedEntityType The type of the linked entity.
     * @param linkedEntityId   The ID of the linked entity.
     * @return A success message.
     */
    @DeleteMapping("/entity")
    public ResponseEntity<String> deleteAttachments(
            @RequestParam Long linkedEntityId,
            @RequestParam String linkedEntityType) {
        attachmentService.deleteAttachments(linkedEntityId, linkedEntityType);
        return ResponseEntity.ok("Attachments deleted successfully.");
    }

    /**
     * Delete a single attachment by its ID.
     *
     * @param attachmentId The ID of the attachment to delete.
     * @return A success message.
     */
    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<String> deleteAttachmentById(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachmentById(attachmentId);
        return ResponseEntity.ok("Attachment deleted successfully.");
    }

    /**
     * Retrieve a single attachment by its ID.
     *
     * @param attachmentId The ID of the attachment to retrieve.
     * @return The attachment.
     */
    @GetMapping("/{attachmentId}")
    public ResponseEntity<AttachmentDto> getAttachmentById(@PathVariable Long attachmentId) {
        AttachmentDto attachment = attachmentService.getAttachmentById(attachmentId);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/count/{entityId}")
    public ResponseEntity<Long> countAttachmentsByEntity(@PathVariable Long entityId) {
        return ResponseEntity.ok(attachmentService.countAttachmentsByEntity(entityId));
    }
    @GetMapping("/uploader/{userId}")
    public ResponseEntity<List<AttachmentDto>> getAttachmentsByUploader(@PathVariable Long userId) {
        return ResponseEntity.ok(attachmentService.getAttachmentsByUploader(userId));
    }

    @GetMapping("/{entityId}/{entityType}")
    public ResponseEntity<List<AttachmentDto>> getAttachmentsByEntity(@RequestParam Long linkedEntityId, @RequestParam String linkedEntityType) {
        return ResponseEntity.ok(attachmentService.getAttachmentsByEntity(linkedEntityId, linkedEntityType));
    }

}

