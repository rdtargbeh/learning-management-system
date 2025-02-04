package learning_management_system.backend.service;

import learning_management_system.backend.dto.GradeBookHistoryDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface GradeBookHistoryService {

    void recordGradeChange(Long recordId, Double previousScore, Double newScore,
                           Long changedByUserId, String reason, String metadata);

    List<GradeBookHistoryDto> getHistoryByRecordId(Long recordId);

    List<GradeBookHistoryDto> getHistoryByUserId(Long userId);

    Map<String, Object> getCustomReports(Long userId);

//    Page<GradeBookHistoryDto> getHistoryWithPagination(Long recordId, int page, int size);

    Page<GradeBookHistoryDto> getPaginatedGradeBookHistory(Long recordId, int page, int size);

    List<GradeBookHistoryDto> getFilteredHistory(Long userId, Long recordId,
                                                 LocalDateTime startDate, LocalDateTime endDate);

    void recordGradeChange(Long recordId, Double previousScore, Double newScore,
                          String reason, Long changedById);





}
