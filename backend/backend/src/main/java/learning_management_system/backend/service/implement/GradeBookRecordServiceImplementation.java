package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.dto.GradeBookRecordDto;
import learning_management_system.backend.entity.GradeBookItem;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.mapper.GradeBookRecordMapper;
import learning_management_system.backend.repository.GradeBookItemRepository;
import learning_management_system.backend.repository.GradeBookRecordRepository;
import learning_management_system.backend.repository.StudentRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.FeedbackService;
import learning_management_system.backend.service.GradeBookRecordService;
import learning_management_system.backend.service.GradeBookService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeBookRecordServiceImplementation implements GradeBookRecordService {

    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;
    @Autowired
    private GradeBookRecordMapper gradeBookRecordMapper;
    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private  GradeBookService gradeBookService;
    @Autowired
    private  UserRepository userRepository;


    @Override
    public GradeBookRecordDto createGradeBookRecord(GradeBookRecordDto dto) {
        GradeBookItem gradeBookItem = gradeBookItemRepository.findById(dto.getGradeBookItemId())
                .orElseThrow(() -> new RuntimeException("Grade book item not found."));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found."));

        GradeBookRecord record = gradeBookRecordMapper.toEntity(dto);
        record.setGradeBookItem(gradeBookItem);
        record.setStudent(student);

        return gradeBookRecordMapper.toDto(gradeBookRecordRepository.save(record));
    }

    @Override
    @Transactional
    public GradeBookRecordDto updateGradeBookRecord(Long recordId, GradeBookRecordDto dto) {
        // Fetch the existing GradeBookRecord
        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Grade book record not found."));

        // Update basic fields from the DTO
        if (dto.getScore() != null) {
            record.setScore(dto.getScore());
        }
        if (dto.getFeedback() != null) {
            record.setFeedback(dto.getFeedback());
        }
        if (dto.getFinalized() != null) {
            record.setFinalized(dto.getFinalized());
        }
        if (dto.getVerified() != null) {
            record.setVerified(dto.getVerified());
        }
        if (dto.getLocked() != null) {
            record.setLocked(dto.getLocked());
        }
        if (dto.getMetadata() != null) {
            record.setMetadata(dto.getMetadata());
        }

        // Handle relationships
        if (dto.getGradeBookItemId() != null) {
            GradeBookItem gradeBookItem = gradeBookItemRepository.findById(dto.getGradeBookItemId())
                    .orElseThrow(() -> new RuntimeException("GradeBookItem not found with ID: " + dto.getGradeBookItemId()));
            record.setGradeBookItem(gradeBookItem);
        }

        if (dto.getStudentId() != null) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + dto.getStudentId()));
            record.setStudent(student);
        }

        // Save the updated record
        GradeBookRecord updatedRecord = gradeBookRecordRepository.save(record);

        // Convert to DTO and return
        return gradeBookRecordMapper.toDto(updatedRecord);
    }


    /**
     * Finalizes the grade for a specific grade book record and updates grade book progress.
     *
     * @param recordId The ID of the grade book record to finalize.
     * @param score    The finalized score to set.
     */
    @Transactional
    public void finalizeGrade(Long recordId, Double score) {
        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found."));

        // Update record with score and finalized status
        record.setScore(score);
        record.setFinalized(true);

        gradeBookRecordRepository.save(record);

        // Update progress in the gradebook
        gradeBookService.updateProgressOnRecordChange(
                record.getGradeBookItem().getCategory().getGradeBook().getGradeBookId()
        );
    }


    @Override
    public List<GradeBookRecordDto> getRecordsByItem(Long itemId) {
        return gradeBookRecordRepository.findByGradeBookItem_ItemId(itemId).stream()
                .map(gradeBookRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeBookRecordDto> getRecordsByStudent(Long studentId) {
        return gradeBookRecordRepository.findByStudent_StudentId(studentId).stream()
                .map(gradeBookRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateAverageScoreByItem(Long itemId) {
        return gradeBookRecordRepository.calculateAverageScoreByItemId(itemId);
    }

    /**
     * Add feedback for a grade book record.
     *
     * @param recordId The ID of the grade book record.
     * @param feedbackDto The feedback details.
     * @return The created FeedbackDto.
     */
    @Override
    @Transactional
    public FeedbackDto addFeedbackForRecord(Long recordId, FeedbackDto feedbackDto) {
        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Grade book record not found."));

        // Set feedback details
        feedbackDto.setLinkedEntityId(recordId);
        feedbackDto.setLinkedEntityType("GRADEBOOK_RECORD");

        FeedbackDto createdFeedback = feedbackService.createFeedback(feedbackDto);

        // Notify the student
        notificationService.createAndSendNotification(
                record.getStudent().getStudentId(),
                "Feedback provided for " + record.getGradeBookItem().getName(),
                record.getGradeBookItem().getItemId(),
                RelatedEntityType.GRADEBOOK_ITEM,
                "Feedback added for your graded activity."
        );

        return createdFeedback;
    }

    /**
     * Retrieve feedback for a grade book record.
     *
     * @param recordId The ID of the grade book record.
     * @return List of FeedbackDto.
     */
    @Override
    @Transactional
    public List<FeedbackDto> getFeedbackForRecord(Long recordId) {
        return feedbackService.getFeedbackByEntity("GRADEBOOK_RECORD", recordId);
    }

    /**
     * Count finalized records for a specific grade book item.
     *
     * @param itemId the ID of the grade book item.
     * @return the count of finalized records.
     */
    @Override
    @Transactional
    public Long countFinalizedRecords(Long itemId) {
        return gradeBookRecordRepository.countByGradeBookItem_ItemIdAndIsFinalizedTrue(itemId);
    }

    /**
     * Fetch all finalized records for a specific grade book item.
     *
     * @param itemId the ID of the grade book item.
     * @return a list of finalized grade book records.
     */
    @Override
    @Transactional
    public Page<GradeBookRecordDto> getFinalizedRecords(Long itemId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GradeBookRecord> records = gradeBookRecordRepository.findByGradeBookItem_ItemIdAndIsFinalizedTrue(itemId, pageable);
        return records.map(gradeBookRecordMapper::toDto);
    }

    /**
     * Calculate total points scored by a student in a specific grade book.
     *
     * @param studentId   the ID of the student.
     * @param gradeBookId the ID of the grade book.
     * @return the total points scored.
     */
    @Override
    @Transactional
    public Double calculateTotalPointsByStudent(Long studentId, Long gradeBookId) {
        return gradeBookRecordRepository.calculateTotalPointsByStudentAndGradeBook(studentId, gradeBookId);
    }

    /**
     * Finalize all grade records for a specific grade book item.
     *
     * @param itemId the ID of the grade book item.
     */
    @Override
    @Transactional
    public void finalizeAllRecordsForItem(Long itemId) {
        gradeBookRecordRepository.finalizeAllRecordsForItem(itemId);
    }

    /**
     * Generate analytics for a specific grade book item.
     *
     * @param gradeBookId the ID of the grade book.
     * @return analytics data including average score and total items.
     */
    @Override
    @Transactional
    public Map<String, Object> generateItemAnalytics(Long gradeBookId) {
        Double averageScore = gradeBookRecordRepository.calculateAverageScoreByGradeBook(gradeBookId);
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("averageScore", averageScore);
        analytics.put("gradeBookId", gradeBookId);
        return analytics;
    }

    /**
     * Marks a grade as verified.
     *
     * @param recordId the ID of the grade book record
     * @param verifierId the ID of the user verifying the grade
     */
    @Override
    public void verifyGrade(Long recordId, Long verifierId) {
        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found with ID: " + recordId));

        User verifier = userRepository.findById(verifierId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + verifierId));

        if (record.getLocked()) {
            throw new RuntimeException("Grade record is locked and cannot be verified.");
        }

        record.setVerified(true);
        record.setVerifiedBy(verifier);
        gradeBookRecordRepository.save(record);
    }

    /**
     * Locks a grade record to prevent modifications.
     *
     * @param recordId the ID of the grade book record
     * @param lockStatus whether to lock or unlock the record
     */
    @Override
    public void setGradeLockStatus(Long recordId, Boolean lockStatus) {
        GradeBookRecord record = gradeBookRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found with ID: " + recordId));

        record.setLocked(lockStatus);
        gradeBookRecordRepository.save(record);
    }
}
