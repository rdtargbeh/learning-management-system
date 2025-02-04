package learning_management_system.backend.service;

import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.dto.GradeBookRecordDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface GradeBookRecordService {

    GradeBookRecordDto createGradeBookRecord(GradeBookRecordDto dto);

    GradeBookRecordDto updateGradeBookRecord(Long recordId, GradeBookRecordDto dto);

    List<GradeBookRecordDto> getRecordsByItem(Long itemId);

    List<GradeBookRecordDto> getRecordsByStudent(Long studentId);

    Double calculateAverageScoreByItem(Long itemId);

    FeedbackDto addFeedbackForRecord(Long recordId, FeedbackDto feedbackDto);

    List<FeedbackDto> getFeedbackForRecord(Long recordId);

    Long countFinalizedRecords(Long itemId);

//    List<GradeBookRecordDto> getFinalizedRecords(Long itemId);

    Page<GradeBookRecordDto> getFinalizedRecords(Long itemId, int page, int size);

    Double calculateTotalPointsByStudent(Long studentId, Long gradeBookId);

    void finalizeAllRecordsForItem(Long itemId);

    Map<String, Object> generateItemAnalytics(Long gradeBookId);

    void finalizeGrade(Long recordId, Double score);

    void verifyGrade(Long recordId, Long verifierId);

    void setGradeLockStatus(Long recordId, Boolean lockStatus);

}
