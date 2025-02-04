package learning_management_system.backend.service;


import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.ModuleContentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


/**
 * Service interface for ModuleContent operations.
 */
public interface ModuleContentService {
    List<ModuleContentDto> getAllContents();

    List<ModuleContentDto> getContentsByModuleId(Long moduleId);

    ModuleContentDto getContentById(Long contentId);

    ModuleContentDto createContent(ModuleContentDto contentDto);

    ModuleContentDto updateContent(Long contentId, ModuleContentDto contentDto);

    void deleteContent(Long contentId);

    AttachmentDto addAttachmentToModuleContent(Long contentId, AttachmentDto attachmentDto);

    List<AttachmentDto> getAttachmentsByModuleContent(Long contentId);

    boolean isContentReleased(Long contentId, Set<Long> completedContentIds);

    boolean isContentComplete(Long contentId, Set<Long> completedContentIds);

    List<ModuleContentDto> getReleasedContent();

    List<ModuleContentDto> getAvailableContent(LocalDateTime currentTime);

    List<ModuleContentDto> searchByTag(String tag);
//    void addAttachmentToModuleContents(Long contentId, AttachmentDto attachmentDto);

//    List<ModuleContentDto> getVisibleContents(Boolean isVisible);


}

