package learning_management_system.backend.service;

import learning_management_system.backend.dto.GradeBookItemDto;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeBookItemService {
    GradeBookItemDto createGradeBookItem(GradeBookItemDto dto);

    GradeBookItemDto updateGradeBookItem(Long itemId, GradeBookItemDto dto);

    GradeBookItemDto getGradeBookItem(Long itemId);

    List<GradeBookItemDto> getItemsByCategoryId(Long categoryId);

    List<GradeBookItemDto> getItemsByDueDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void deleteGradeBookItem(Long itemId);

    List<GradeBookItemDto> batchCreateGradeBookItems(List<GradeBookItemDto> itemDtos);

    List<GradeBookItemDto> getOverdueItems(Long gradeBookId);

    void updateGroupGrading(Long itemId, Boolean isGroupGrading, Long groupId);

    void setGradeVerificationRequired(Long itemId, Boolean verificationRequired);

}
