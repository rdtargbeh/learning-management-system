package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.ModuleContentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.CourseModule;
import learning_management_system.backend.entity.ModuleContent;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ContentAccessType;
import learning_management_system.backend.enums.ContentType;
import learning_management_system.backend.enums.InteractivityLevel;
import learning_management_system.backend.mapper.AttachmentMapper;
import learning_management_system.backend.mapper.ModuleContentMapper;
import learning_management_system.backend.repository.AttachmentRepository;
import learning_management_system.backend.repository.CourseModuleRepository;
import learning_management_system.backend.repository.ModuleContentRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.ModuleContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ModuleContentServiceImplementation implements ModuleContentService {

    @Autowired
    private ModuleContentRepository moduleContentRepository;
    @Autowired
    private CourseModuleRepository courseModuleRepository;
    private ModuleContentDto moduleContentDto;
    @Autowired
    private  ModuleContentMapper moduleContentMapper;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ModuleContentDto createContent(ModuleContentDto contentDto) {
        // Validate that the parent CourseModule exists
        CourseModule courseModule = courseModuleRepository.findById(contentDto.getModuleId())
                .orElseThrow(() -> new RuntimeException("Course module not found with ID: " + contentDto.getModuleId()));

        // Validate and fetch attachments
        List<Attachment> attachments = validateAttachments(contentDto.getAttachmentIds());

        // Map DTO to entity
        ModuleContent content = moduleContentMapper.toEntity(contentDto);
        content.setModule(courseModule);
        content.setAttachments(attachments);

        // Set default values
        content.setPublished(contentDto.getPublished() != null ? contentDto.getPublished() : false);
        content.setVersion(1); // Initialize version for new content

        // Save content
        ModuleContent savedContent = moduleContentRepository.save(content);

        // Return the saved content as DTO
        return moduleContentMapper.toDto(savedContent);
    }


    @Override
    public ModuleContentDto updateContent(Long contentId, ModuleContentDto contentDto) {
        ModuleContent existingContent = moduleContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content not found"));

        // Update fields
        existingContent.setContentTitle(contentDto.getContentTitle());
        existingContent.setContentDescription(contentDto.getContentDescription());
        existingContent.setContentType(ContentType.valueOf(contentDto.getContentType()));
        existingContent.setFileUrl(contentDto.getFileUrl());
        existingContent.setOrderContent(contentDto.getOrderContent());
        existingContent.setPublished(contentDto.getPublished());
        existingContent.setCompletionCriteria(contentDto.getCompletionCriteria());
        existingContent.setEngagementScore(contentDto.getEngagementScore());
        existingContent.setLanguage(contentDto.getLanguage());
        existingContent.setTags(contentDto.getTags());
        existingContent.setAnalytics(contentDto.getAnalytics());
        existingContent.setAccessType(ContentAccessType.valueOf(contentDto.getAccessType()));
        existingContent.setAvailabilityStart(contentDto.getAvailabilityStart());
        existingContent.setAvailabilityEnd(contentDto.getAvailabilityEnd());
        existingContent.setInteractivityLevel(InteractivityLevel.valueOf(contentDto.getInteractivityLevel()));
        existingContent.setPrerequisiteContentId(contentDto.getPrerequisiteContentId());
        existingContent.setReleaseDate(contentDto.getReleaseDate());
        existingContent.setMetadata(contentDto.getMetadata());

        // Validate and update attachments
        List<Attachment> attachments = validateAttachments(contentDto.getAttachmentIds());
        existingContent.setAttachments(attachments);

        // Increment version if critical fields change
        if (!existingContent.getFileUrl().equals(contentDto.getFileUrl()) ||
                !existingContent.getCompletionCriteria().equals(contentDto.getCompletionCriteria())) {
            existingContent.incrementVersion(existingContent.getContentId());
        }

        // Update version control
//        existingContent.incrementVersion(existingContent.getContentId());

        ModuleContent updatedContent = moduleContentRepository.save(existingContent);
        return moduleContentMapper.toDto(updatedContent);
    }

    // Get All Contents
    @Override
    public List<ModuleContentDto> getAllContents() {
        return moduleContentRepository.findAll().stream()
                .map(moduleContentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Content By Module ID
    @Override
    public List<ModuleContentDto> getContentsByModuleId(Long moduleId) {
        // Validate CourseModule existence
        CourseModule courseModule = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Course Module not found with ID: " + moduleId));

        // Fetch contents using the repository
        List<ModuleContent> contents = moduleContentRepository.findByCourseModule_ModuleId(moduleId);
        return moduleContentRepository.findByCourseModule_ModuleId(moduleId).stream()
                .map(moduleContentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Content by ID
    @Override
    public ModuleContentDto getContentById(Long contentId) {
        ModuleContent moduleContent = moduleContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content not found."));
        return moduleContentMapper.toDto(moduleContent);
    }

    // Delete Content
    @Override
    public void deleteContent(Long contentId) {
        if (!moduleContentRepository.existsById(contentId)) {
            throw new IllegalArgumentException("Content not found.");
        }
        moduleContentRepository.deleteById(contentId);
    }

    // Check if content is release
    @Override
    public boolean isContentReleased(Long contentId, Set<Long> completedContentIds) {
        ModuleContent content = moduleContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content not found."));
        return content.isReleased(completedContentIds, LocalDateTime.now());
    }

    // Check whether content is completed
    @Override
    public boolean isContentComplete(Long contentId, Set<Long> completedContentIds) {
        ModuleContent content = moduleContentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content not found."));
        return content.isComplete(completedContentIds);
    }

    // Get Release content
    @Override
    public List<ModuleContentDto> getReleasedContent() {
        return moduleContentRepository.findReleasedContent().stream()
                .map(moduleContentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Search content by Tags
    @Override
    public List<ModuleContentDto> searchByTag(String tag) {
        return moduleContentRepository.findByTagsContaining(tag).stream()
                .map(moduleContentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModuleContentDto> getAvailableContent(LocalDateTime currentTime) {
        return moduleContentRepository.findAvailableContent(currentTime).stream()
                .map(moduleContentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Attach file to assignment
    @Override
    public AttachmentDto addAttachmentToModuleContent(Long contentId, AttachmentDto attachmentDto) {
        // Find the Assignment
        ModuleContent content = moduleContentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + contentId));

        // Map DTO to Entity
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);

        // Associate Assignment and Attachment
        attachment.setLinkedEntityType("MODULE_CONTENT");
        attachment.setLinkedEntityId(contentId);

        // Set the uploadedBy User
        User currentUser = getCurrentUser();
        attachment.setUploadedBy(currentUser);

        // Save Attachment
        attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Current user not found!"));
    }

    // Get Attachment by Module content
    @Override
    public List<AttachmentDto> getAttachmentsByModuleContent(Long contentId) {
        return attachmentRepository.findAttachmentsByModuleContentId(contentId).stream()
                .map(attachmentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Validate Attachment exist before uploading
    private List<Attachment> validateAttachments(List<Long> attachmentIds) {
        // If no attachment IDs are provided, return an empty list.
        if (attachmentIds == null || attachmentIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Fetch attachments from the repository based on the provided IDs.
        List<Attachment> attachments = attachmentRepository.findAllById(attachmentIds);

        // Validate that all provided IDs correspond to existing attachments.
        if (attachments.size() != attachmentIds.size()) {
            throw new RuntimeException("One or more attachments not found.");
        }

        return attachments;
    }



//    @Override
//    public void addAttachmentToModuleContents(Long contentId, AttachmentDto attachmentDto) {
//        // Validate the existence of the ModuleContent
//        ModuleContent moduleContent = moduleContentRepository.findById(contentId)
//                .orElseThrow(() -> new IllegalArgumentException("ModuleContent not found"));
//
//        // Map the AttachmentDto to the Attachment entity
//        Attachment attachment = new Attachment();
//        attachment.setFileName(attachmentDto.getFileName());
//        attachment.setFileType(attachmentDto.getFileType());
//        attachment.setFileSize(attachmentDto.getFileSize());
//        attachment.setStorageUrl(attachmentDto.getStorageUrl());
//        attachment.setStorageType(attachmentDto.getStorageType());
//        attachment.setLinkedEntityType("MODULE_CONTENT"); // Link to ModuleContent
//        attachment.setLinkedEntityId(contentId); // Link to specific ModuleContent
//        attachment.setAccessLevel(attachmentDto.getAccessLevel());
//        attachment.setVisibility(attachmentDto.getVisibility());
//        attachment.setCategory(attachmentDto.getCategory());
//        attachment.setTags(attachmentDto.getTags());
//
//        attachment.setUploadedBy(userRepository.findById(attachmentDto.getUploadedBy())
//                .orElseThrow(() -> new IllegalArgumentException("User not found")));
//
//        // Save the attachment
//        attachmentRepository.save(attachment);
//    }



//    @Override
//    public ModuleContentDto updateContent(Long contentId, ModuleContentDto contentDto) {
//        ModuleContent existingContent = moduleContentRepository.findById(contentId)
//                .orElseThrow(() -> new IllegalArgumentException("Content not found"));
//
//        existingContent.setContentTitle(contentDto.getContentTitle());
//        existingContent.setContentDescription(contentDto.getContentDescription());
//        existingContent.setContentType(ContentType.valueOf(contentDto.getContentType()));
//        existingContent.setFileUrl(contentDto.getFileUrl());
//        existingContent.setOrderContent(contentDto.getOrderContent());
//        existingContent.setIsPublished(contentDto.getIsPublished());
//        existingContent.setCompletionCriteria(contentDto.getCompletionCriteria());
//        existingContent.setEngagementScore(contentDto.getEngagementScore());
//        existingContent.setVersion(contentDto.getVersion());
//        existingContent.setPreviousVersionId(contentDto.getPreviousVersionId());
//        existingContent.setLanguage(contentDto.getLanguage());
//        existingContent.setTags(contentDto.getTags());
//        existingContent.setAnalytics(contentDto.getAnalytics());
//        existingContent.setAccessType(ContentAccessType.valueOf(contentDto.getAccessType()));
//        existingContent.setAvailabilityStart(contentDto.getAvailabilityStart());
//        existingContent.setAvailabilityEnd(contentDto.getAvailabilityEnd());
//        existingContent.setInteractivityLevel(InteractivityLevel.valueOf(contentDto.getInteractivityLevel()));
//        existingContent.setPrerequisiteContentId(contentDto.getPrerequisiteContentId());
//        existingContent.setReleaseDate(contentDto.getReleaseDate());
//        existingContent.setMetadata(contentDto.getMetadata());
//
//        // Update attachments if provided
//        if (moduleContentDto.getAttachmentIds() != null) {
//            List<Attachment> attachments = attachmentRepository.findAllById(moduleContentDto.getAttachmentIds());
//            if (attachments.size() != moduleContentDto.getAttachmentIds().size()) {
//                throw new RuntimeException("One or more attachments not found.");
//            }
//            existingContent.setAttachments(attachments);
//        }
//
//
//        // save update
//        ModuleContent updatedContent = moduleContentRepository.save(existingContent);
//        return moduleContentMapper.toDto(updatedContent);
//    }


//    @Override
//    public ModuleContentDto updateContent(Long contentId, ModuleContentDto contentDto) {
//        // Validate that the content exists
//        ModuleContent existingContent = moduleContentRepository.findById(contentId)
//                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + contentId));
//
//        // Update basic fields
//        existingContent.setContentTitle(contentDto.getContentTitle());
//        existingContent.setContentDescription(contentDto.getContentDescription());
//        existingContent.setContentType(ContentType.valueOf(contentDto.getContentType()));
//        existingContent.setFileUrl(contentDto.getFileUrl());
//        existingContent.setOrderContent(contentDto.getOrderContent());
//        existingContent.setIsPublished(contentDto.getIsPublished() != null ? contentDto.getIsPublished() : existingContent.getIsPublished());
//        existingContent.setCompletionCriteria(contentDto.getCompletionCriteria());
//        existingContent.setEngagementScore(contentDto.getEngagementScore());
//        existingContent.setLanguage(contentDto.getLanguage());
//        existingContent.setTags(contentDto.getTags());
//        existingContent.setAnalytics(contentDto.getAnalytics());
//        existingContent.setAccessType(ContentAccessType.valueOf(contentDto.getAccessType()));
//        existingContent.setAvailabilityStart(contentDto.getAvailabilityStart());
//        existingContent.setAvailabilityEnd(contentDto.getAvailabilityEnd());
//        existingContent.setInteractivityLevel(InteractivityLevel.valueOf(contentDto.getInteractivityLevel()));
//        existingContent.setPrerequisiteContentId(contentDto.getPrerequisiteContentId());
//        existingContent.setReleaseDate(contentDto.getReleaseDate());
//        existingContent.setMetadata(contentDto.getMetadata());
//
//        // Handle and validate attachments
//        if (contentDto.getAttachmentIds() != null && !contentDto.getAttachmentIds().isEmpty()) {
//            List<Attachment> attachments = attachmentRepository.findAllById(contentDto.getAttachmentIds());
//            validateAttachments(attachments, contentDto.getAttachmentIds());
//            existingContent.setAttachments(attachments);
//        }
//
//        // Increment version if critical fields change
//        if (!existingContent.getFileUrl().equals(contentDto.getFileUrl()) ||
//                !existingContent.getCompletionCriteria().equals(contentDto.getCompletionCriteria())) {
//            existingContent.incrementVersion(existingContent.getContentId());
//        }
//
//        // Save updated content
//        ModuleContent updatedContent = moduleContentRepository.save(existingContent);
//
//        // Map and return updated DTO
//        return moduleContentMapper.toDto(updatedContent);
//    }


//    public List<ModuleContentDto> getVisibleContents(Boolean isVisible) {
//        return moduleContentRepository.findByIsVisible(isVisible).stream()
//                .map(ModuleContentMapper::toDto)
//                .collect(Collectors.toList());
//    }

}

