package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AttachmentMapper {

    private final UserRepository userRepository; // Inject UserRepository to fetch User by ID.

    @Autowired
    public AttachmentMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Converts an Attachment entity to an AttachmentDto.
     *
     * @param attachment the Attachment entity
     * @return the corresponding AttachmentDto
     */
    public AttachmentDto toDto(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        AttachmentDto dto = new AttachmentDto();
        dto.setAttachmentId(attachment.getAttachmentId());
        dto.setFileType(attachment.getFileType());
        dto.setFileName(attachment.getFileName());
        dto.setStorageUrl(attachment.getStorageUrl());
        dto.setFileSize(attachment.getFileSize());
        dto.setStorageType(attachment.getStorageType());
        dto.setExternalResourceId(attachment.getExternalResourceId());
        dto.setLinkedEntityType(attachment.getLinkedEntityType());
        dto.setLinkedEntityId(attachment.getLinkedEntityId());
        dto.setAccessLevel(attachment.getAccessLevel());
        dto.setVisibility(attachment.getVisibility());
        dto.setCategory(attachment.getCategory());
        dto.setTags(attachment.getTags());
        dto.setUploadDate(attachment.getUploadDate());
        dto.setUploadedBy(attachment.getUploadedBy() != null ? attachment.getUploadedBy().getUserId() : null); // Extract User ID
        dto.setVersion(attachment.getVersion());
        dto.setPreviousVersionId(attachment.getPreviousVersionId());
        dto.setCompressed(attachment.getCompressed());
        dto.setCompressionType(attachment.getCompressionType());
        dto.setDownloadCount(attachment.getDownloadCount());
        dto.setLastAccessed(attachment.getLastAccessed());
        dto.setThumbnailUrl(attachment.getThumbnailUrl());
        dto.setMetadata(attachment.getMetadata());

        return dto;
    }

    /**
     * Converts an AttachmentDto to an Attachment entity.
     *
     * @param dto the AttachmentDto
     * @return the corresponding Attachment entity
     */
    public Attachment toEntity(AttachmentDto dto) {
        if (dto == null) {
            return null;
        }

        Attachment attachment = new Attachment();
        attachment.setAttachmentId(dto.getAttachmentId());
        attachment.setFileType(dto.getFileType());
        attachment.setFileName(dto.getFileName());
        attachment.setStorageUrl(dto.getStorageUrl());
        attachment.setFileSize(dto.getFileSize());
        attachment.setStorageType(dto.getStorageType());
        attachment.setExternalResourceId(dto.getExternalResourceId());
        attachment.setLinkedEntityType(dto.getLinkedEntityType());
        attachment.setLinkedEntityId(dto.getLinkedEntityId());
        attachment.setAccessLevel(dto.getAccessLevel());
        attachment.setVisibility(dto.getVisibility());
        attachment.setCategory(dto.getCategory());
        attachment.setTags(dto.getTags());
        attachment.setUploadDate(dto.getUploadDate());

        // Fetch the User entity using the uploadedBy ID from the DTO
        if (dto.getUploadedBy() != null) {
            User uploadedBy = userRepository.findById(dto.getUploadedBy())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getUploadedBy()));
            attachment.setUploadedBy(uploadedBy);
        }

        attachment.setVersion(dto.getVersion());
        attachment.setPreviousVersionId(dto.getPreviousVersionId());
        attachment.setCompressed(dto.getCompressed());
        attachment.setCompressionType(dto.getCompressionType());
        attachment.setDownloadCount(dto.getDownloadCount());
        attachment.setLastAccessed(dto.getLastAccessed());
        attachment.setThumbnailUrl(dto.getThumbnailUrl());
        attachment.setMetadata(dto.getMetadata());

        return attachment;
    }



    /**
     * Updates an existing attachment entity with values from the DTO.
     *
     * @param existingAttachment The existing attachment entity.
     * @param attachmentDto      The DTO with updated values.
     */
    public void updateAttachment(Attachment existingAttachment, AttachmentDto attachmentDto) {
        if (attachmentDto.getFileName() != null) {
            existingAttachment.setFileName(attachmentDto.getFileName());
        }
        if (attachmentDto.getStorageUrl() != null) {
            existingAttachment.setStorageUrl(attachmentDto.getStorageUrl());
        }
        if (attachmentDto.getFileSize() != null) {
            existingAttachment.setFileSize(attachmentDto.getFileSize());
        }
        if (attachmentDto.getTags() != null) {
            existingAttachment.setTags(new HashSet<>(attachmentDto.getTags()));
        }
        if (attachmentDto.getMetadata() != null) {
            existingAttachment.setMetadata(attachmentDto.getMetadata());
        }
        if (attachmentDto.getAccessLevel() != null) {
            existingAttachment.setAccessLevel(attachmentDto.getAccessLevel());
        }
        // Add additional fields if necessary...
    }
}

