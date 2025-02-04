package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.GradeBookHistoryDto;
import learning_management_system.backend.entity.GradeBookHistory;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.mapper.GradeBookHistoryMapper;
import learning_management_system.backend.repository.GradeBookHistoryRepository;
import learning_management_system.backend.repository.GradeBookRecordRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.GradeBookHistoryService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GradeBookHistoryServiceImplementation implements GradeBookHistoryService {

    @Autowired
    private GradeBookHistoryRepository gradeBookHistoryRepository;
    @Autowired
    private GradeBookHistoryMapper gradeBookHistoryMapper;
    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;


    /**
     * Records a grade change in the history.
     *
     * @param recordId the grade book record ID
     * @param previousScore the previous score
     * @param newScore the new score
     * @param changedByUserId the user making the change
     * @param reason the reason for the grade change
     * @param metadata additional metadata
     */
    @Override
    @Transactional
    public void recordGradeChange(
            Long recordId,
            Double previousScore,
            Double newScore,
            Long changedByUserId,
            String reason,
            String metadata) {

        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found with ID: " + recordId));

        User changedBy = userRepository.findById(changedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + changedByUserId));

        GradeBookHistory history = new GradeBookHistory();
        history.setGradeBookRecord(record);
        history.setPreviousScore(previousScore);
        history.setNewScore(newScore);
        history.setChangedBy(changedBy);
        history.setChangeReason(reason);
        history.setMetadata(metadata);
        history.setDateChanged(LocalDateTime.now());

        gradeBookHistoryRepository.save(history);
    }

    /**
     * Retrieves the grade change history for a specific record.
     *
     * @param recordId the grade book record ID
     * @return a list of grade book history entries
     */
    @Override
    @Transactional(readOnly = true)
    public List<GradeBookHistoryDto> getHistoryByRecordId(Long recordId) {
        List<GradeBookHistory> histories = gradeBookHistoryRepository.findByGradeBookRecord_RecordId(recordId);
        return histories.stream()
                .map(gradeBookHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    // Fetch paginated history entries for a specific record
    @Override
    @Transactional(readOnly = true)
    public Page<GradeBookHistoryDto> getPaginatedGradeBookHistory(Long recordId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GradeBookHistory> histories = gradeBookHistoryRepository.findByGradeBookRecord_RecordId(recordId, pageable);
        return histories.map(gradeBookHistoryMapper::toDto);
    }


    /**
     * Retrieves the grade change history for a specific user.
     *
     * @param userId the user ID
     * @return a list of grade book history entries
     */
    @Override
    @Transactional(readOnly = true)
    public List<GradeBookHistoryDto> getHistoryByUserId(Long userId) {
        List<GradeBookHistory> histories = gradeBookHistoryRepository.findByChangedBy_UserId(userId);
        return histories.stream()
                .map(gradeBookHistoryMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<GradeBookHistoryDto> getFilteredHistory(Long userId, Long recordId, LocalDateTime startDate, LocalDateTime endDate) {
        List<GradeBookHistory> history = gradeBookHistoryRepository.findFilteredHistory(userId, recordId, startDate, endDate);
        return history.stream()
                .map(gradeBookHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getCustomReports(Long userId) {
        Long totalChangesByUser = gradeBookHistoryRepository.countChangesByUser(userId);
        List<Object[]> changesByReason = gradeBookHistoryRepository.countChangesByReason();

        Map<String, Object> report = new HashMap<>();
        report.put("totalChangesByUser", totalChangesByUser);
        report.put("changesByReason", changesByReason.stream()
                .collect(Collectors.toMap(data -> (String) data[0], data -> (Long) data[1])));

        return report;
    }


    // Notifications can alert students or instructors about grade changes.
    @Override
    @Transactional(readOnly = true)
    public void recordGradeChange(Long recordId, Double previousScore, Double newScore, String reason, Long changedById) {
        GradeBookRecord gradeBookRecord = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found."));

        // Record the grade change in history
        GradeBookHistory history = new GradeBookHistory();
        history.setGradeBookRecord(gradeBookRecord);
        history.setPreviousScore(previousScore);
        history.setNewScore(newScore);
        history.setChangeReason(reason);
        history.setChangedBy(userRepository.findById(changedById)
                .orElseThrow(() -> new RuntimeException("User not found.")));
        history.setDateChanged(LocalDateTime.now());

        gradeBookHistoryRepository.save(history);

        // Send a notification
        notificationService.createAndSendNotification(
                gradeBookRecord.getStudent().getUser().getUserId(),
                "Your grade has been updated.",
                gradeBookRecord.getGradeBookItem().getItemId(),
                RelatedEntityType.GRADEBOOK_ITEM,
                "Grade updated from " + previousScore + " to " + newScore
        );
    }
}
