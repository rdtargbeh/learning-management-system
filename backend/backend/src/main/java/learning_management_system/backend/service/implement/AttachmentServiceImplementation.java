package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.mapper.AttachmentMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImplementation implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;


    /**
     * Upload an attachment and link it to a specific entity.
     *
     * @param attachmentDto The attachment to upload.
     * @return The saved attachment.
     */
    @Override
    public AttachmentDto uploadAttachment(Long linkedEntityId, String linkedEntityType, AttachmentDto attachmentDto) {
        // Validate that the linked entity exists
        validateLinkedEntity(linkedEntityId, linkedEntityType);


        // Map DTO to entity and set additional properties
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachment.setLinkedEntityId(linkedEntityId);
        attachment.setLinkedEntityType(linkedEntityType);
        attachment.setUploadDate(new Date());

        // Save attachment and return DTO
        return attachmentMapper.toDto(attachmentRepository.save(attachment));
    }

    /**
     * Update an existing attachment.
     *
     * @param attachmentId  The ID of the attachment to update.
     * @param attachmentDto The updated attachment details.
     * @return The updated attachment.
     */

    @Override
    public AttachmentDto updateAttachment(Long attachmentId, AttachmentDto attachmentDto) {
        // Fetch the existing attachment
        Attachment existingAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found with ID: " + attachmentId));

        // Ensure linked entity information remains immutable
        attachmentMapper.updateAttachment(existingAttachment, attachmentDto);
        existingAttachment.setLastAccessed(new Date()); // Optionally update last accessed
        existingAttachment.setFileName(attachmentDto.getFileName());
        existingAttachment.setStorageUrl(attachmentDto.getStorageUrl());
        existingAttachment.setFileSize(attachmentDto.getFileSize());

        // Save updated attachment and return DTO
        return attachmentMapper.toDto(attachmentRepository.save(existingAttachment));
    }

    /**
     * Validates that the linked entity exists and is of the correct type.
     * @param linkedEntityId the ID of the linked entity
     * @param linkedEntityType the type of the linked entity
     */
    private void validateLinkedEntity(Long linkedEntityId, String linkedEntityType) {
        switch (linkedEntityType.toUpperCase()) {
            case "ANNOUNCEMENT":
                if (!announcementRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Linked Announcement not found with ID: " + linkedEntityId);
                }
                break;
            case "ASSIGNMENT":
                if (!assignmentRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Linked Assignment not found with ID: " + linkedEntityId);
                }
                break;
            case "ASSESSMENT":
                if (!assessmentRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Linked Assessment not found with ID: " + linkedEntityId);
                }
                break;
            default:
                throw new RuntimeException("Invalid linked entity type: " + linkedEntityType);
        }
    }

    /**
     * Retrieve all attachments linked to a specific entity.
     *
     * @param linkedEntityType The type of the linked entity (e.g., Announcement, Assignment).
     * @param linkedEntityId   The ID of the linked entity.
     * @return List of attachments.
     */
    @Override
    public List<AttachmentDto> getAttachments(Long linkedEntityId, String linkedEntityType) {
        return attachmentRepository.findByLinkedEntityIdAndLinkedEntityType(linkedEntityId, linkedEntityType).stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete all attachments linked to a specific entity.
     *
     * @param linkedEntityType The type of the linked entity.
     * @param linkedEntityId   The ID of the linked entity.
     */
    @Override
    @Transactional
    public void deleteAttachments(Long linkedEntityId, String linkedEntityType) {
        List<Attachment> attachments = attachmentRepository.findByLinkedEntityIdAndLinkedEntityType(linkedEntityId, linkedEntityType);
        attachmentRepository.deleteAll(attachments);
    }

    /**
     * Delete a single attachment by its ID.
     *
     * @param attachmentId The ID of the attachment to delete.
     */
    @Override
    public void deleteAttachmentById(Long attachmentId) {
        if (!attachmentRepository.existsById(attachmentId)) {
            throw new IllegalArgumentException("Attachment not found with ID: " + attachmentId);
        }
        attachmentRepository.deleteById(attachmentId);
    }

    /**
     * Retrieve a single attachment by its ID.
     *
     * @param attachmentId The ID of the attachment to retrieve.
     * @return The attachment.
     */

    @Override
    public AttachmentDto getAttachmentById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found with ID: " + attachmentId));
        return attachmentMapper.toDto(attachment);
    }


    @Override
    public List<AttachmentDto> getAttachmentsByEntity(Long linkedEntityId, String linkedEntityType) {
        return attachmentRepository.findByLinkedEntityIdAndLinkedEntityType(linkedEntityId, linkedEntityType)
                .stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttachmentDto> getAttachmentsByUploader(Long userId) {
        return attachmentRepository.findByUploader(userId)
                .stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countAttachmentsByEntity(Long entityId) {
        return attachmentRepository.countByLinkedEntityId(entityId);
    }
}
