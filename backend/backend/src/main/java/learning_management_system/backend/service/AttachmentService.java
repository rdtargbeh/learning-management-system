package learning_management_system.backend.service;


import learning_management_system.backend.dto.AttachmentDto;

import java.util.List;

public interface AttachmentService {

    List<AttachmentDto> getAttachments(Long linkedEntityId, String linkedEntityType);

    AttachmentDto updateAttachment(Long attachmentId, AttachmentDto attachmentDto);

    void deleteAttachments(Long linkedEntityId, String linkedEntityType);

    void deleteAttachmentById(Long attachmentId);

    AttachmentDto getAttachmentById(Long attachmentId);

    AttachmentDto uploadAttachment(Long linkedEntityId, String linkedEntityType, AttachmentDto attachmentDto);

//    AttachmentDto createAttachment(AttachmentDto attachmentDto);

    List<AttachmentDto> getAttachmentsByEntity(Long linkedEntityId, String linkedEntityType);

    List<AttachmentDto> getAttachmentsByUploader(Long userId);


    long countAttachmentsByEntity(Long entityId);
}
