package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.ModuleContentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.CourseModule;
import learning_management_system.backend.entity.ModuleContent;
import learning_management_system.backend.enums.ContentAccessType;
import learning_management_system.backend.enums.ContentType;
import learning_management_system.backend.enums.InteractivityLevel;
import learning_management_system.backend.repository.AttachmentRepository;
import learning_management_system.backend.repository.CourseModuleRepository;
import learning_management_system.backend.repository.ModuleContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


//@Mapper(componentModel = "spring", uses = {AttachmentMapper1.class})

@Component
public class ModuleContentMapper {

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private ModuleContentRepository moduleContentRepository;
    @Autowired
    CourseModuleRepository courseModuleRepository;

    /**
     * Converts a ModuleContent entity to a ModuleContentDto.
     *
     * @param moduleContent the ModuleContent entity
     * @return the corresponding ModuleContentDto
     */
    public ModuleContentDto toDto(ModuleContent moduleContent) {
        if (moduleContent == null) {
            return null;
        }

        ModuleContentDto dto = new ModuleContentDto();
        dto.setContentId(moduleContent.getContentId());
        dto.setContentTitle(moduleContent.getContentTitle());
        dto.setContentDescription(moduleContent.getContentDescription());
        dto.setContentType(moduleContent.getContentType() != null ? moduleContent.getContentType().name() : null);
        dto.setFileUrl(moduleContent.getFileUrl());
        dto.setOrderContent(moduleContent.getOrderContent());
        dto.setPublished(moduleContent.getPublished());
        dto.setCompletionCriteria(moduleContent.getCompletionCriteria());
        dto.setEngagementScore(moduleContent.getEngagementScore());
        dto.setModuleId(moduleContent.getModule() != null ? moduleContent.getModule().getModuleId() : null);
        dto.setVersion(moduleContent.getVersion());
        dto.setPreviousVersionId(moduleContent.getPreviousVersionId());
        dto.setLanguage(moduleContent.getLanguage());
        dto.setTags(moduleContent.getTags());
        dto.setAnalytics(moduleContent.getAnalytics());
        dto.setReleaseDate(moduleContent.getReleaseDate());
        dto.setCreatedAt(moduleContent.getCreatedAt());
        dto.setUpdatedAt(moduleContent.getUpdatedAt());
        dto.setMetadata(moduleContent.getMetadata());

        // Convert enums to Strings
        dto.setAccessType(moduleContent.getAccessType() != null ? moduleContent.getAccessType().name() : null);
        dto.setInteractivityLevel(moduleContent.getInteractivityLevel() != null ? moduleContent.getInteractivityLevel().name() : null);

        // Map attachments to attachment IDs
        if (moduleContent.getAttachments() != null) {
            dto.setAttachmentIds(moduleContent.getAttachments().stream()
                    .map(Attachment::getAttachmentId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Converts a ModuleContentDto to a ModuleContent entity.
     *
     * @param dto the ModuleContentDto
     * @return the corresponding ModuleContent entity
     */
    public ModuleContent toEntity(ModuleContentDto dto) {
        if (dto == null) {
            return null;
        }

        ModuleContent moduleContent = new ModuleContent();
        moduleContent.setContentId(dto.getContentId());
        moduleContent.setContentTitle(dto.getContentTitle());
        moduleContent.setContentDescription(dto.getContentDescription());
        moduleContent.setContentType(ContentType.valueOf(dto.getContentType()));
        moduleContent.setFileUrl(dto.getFileUrl());
        moduleContent.setOrderContent(dto.getOrderContent());
        moduleContent.setPublished(dto.getPublished());
        moduleContent.setCompletionCriteria(dto.getCompletionCriteria());
        moduleContent.setEngagementScore(dto.getEngagementScore());
        moduleContent.setVersion(dto.getVersion());
        moduleContent.setPreviousVersionId(dto.getPreviousVersionId());
        moduleContent.setLanguage(dto.getLanguage());
        moduleContent.setTags(dto.getTags());
        moduleContent.setAnalytics(dto.getAnalytics());
        moduleContent.setReleaseDate(dto.getReleaseDate());
        moduleContent.setCreatedAt(dto.getCreatedAt());
        moduleContent.setUpdatedAt(dto.getUpdatedAt());
        moduleContent.setMetadata(dto.getMetadata());

        // Fetch and set module
        if (dto.getModuleId() != null) {
            CourseModule courseModule = courseModuleRepository.findById(dto.getModuleId())
                    .orElseThrow(() -> new IllegalArgumentException("Course module not found with ID: " + dto.getModuleId()));
            moduleContent.setModule(courseModule);
        }

        // Set access type
        if (dto.getAccessType() != null) {
            moduleContent.setAccessType(ContentAccessType.valueOf(dto.getAccessType().toUpperCase()));
        }

        // Set interactivity level
        if (dto.getInteractivityLevel() != null) {
            moduleContent.setInteractivityLevel(InteractivityLevel.valueOf(dto.getInteractivityLevel().toUpperCase()));
        }

        // Resolve and set attachments
        if (dto.getAttachmentIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(dto.getAttachmentIds());
            moduleContent.setAttachments(attachments); // Ensure it's a List
        }

        return moduleContent;
    }

}
