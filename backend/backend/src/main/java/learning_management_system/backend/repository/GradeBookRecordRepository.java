package learning_management_system.backend.repository;

import jakarta.transaction.Transactional;
import learning_management_system.backend.entity.GradeBookItem;
import learning_management_system.backend.entity.GradeBookRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GradeBookRecordRepository extends JpaRepository<GradeBookRecord, Long> {

    @Query("SELECT AVG(r.score) FROM GradeBookRecord r WHERE r.gradeBookItem.category.categoryId = :categoryId")
    Double calculateAverageScoreByCategoryId(@Param("categoryId") Long categoryId);


    // Find all records for a specific grade book item
    List<GradeBookRecord> findByGradeBookItem_ItemId(Long itemId);

    // Find all records for a specific student
    List<GradeBookRecord> findByStudent_StudentId(Long studentId);

    // Calculate the average score for a specific grade book item
    @Query("SELECT AVG(r.score) FROM GradeBookRecord r WHERE r.gradeBookItem.itemId = :itemId")
    Double calculateAverageScoreByItemId(@Param("itemId") Long itemId);

    // Count the number of finalized records for a specific grade book item
    Long countByGradeBookItem_ItemIdAndIsFinalizedTrue(Long itemId);

    @Query("SELECT SUM(r.score) FROM GradeBookRecord r WHERE r.student.studentId = :studentId AND r.gradeBookItem.category.gradeBook.gradeBookId = :gradeBookId")
    Double calculateTotalPointsByStudentAndGradeBook(@Param("studentId") Long studentId, @Param("gradeBookId") Long gradeBookId);

    Page<GradeBookRecord> findByGradeBookItem_ItemIdAndIsFinalizedTrue(Long itemId, Pageable pageable);

    @Query("SELECT AVG(r.score) FROM GradeBookRecord r WHERE r.gradeBookItem.category.gradeBook.gradeBookId = :gradeBookId")
    Double calculateAverageScoreByGradeBook(@Param("gradeBookId") Long gradeBookId);

    @Transactional
    @Modifying
    @Query("UPDATE GradeBookRecord r SET r.isFinalized = true WHERE r.gradeBookItem.itemId = :itemId")
    void finalizeAllRecordsForItem(@Param("itemId") Long itemId);

    /**
     * Calculate the total points achieved by students in a grade book.
     *
     * @param gradeBookId ID of the grade book
     * @return Total achieved points
     */
    @Query("""
           SELECT SUM(record.score)
           FROM GradeBookRecord record
           WHERE record.gradeBookItem.category.gradeBook.gradeBookId = :gradeBookId
           AND record.isFinalized = true
           """)
    Double calculateAchievedPointsByGradeBook(@Param("gradeBookId") Long gradeBookId);


    // Calculate Achieved Grade points by Category
    @Query("SELECT SUM(record.score) FROM GradeBookRecord record WHERE record.gradeBookItem.category.categoryId = :categoryId")
    Double calculateAchievedPointsByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT i.itemId FROM GradeBookItem i WHERE i.category.gradeBook.gradeBookId = :gradeBookId")
    List<Long> findItemIdsByGradeBookId(@Param("gradeBookId") Long gradeBookId);

    @Query("SELECT r FROM GradeBookRecord r WHERE r.gradeBookItem.itemId IN :itemIds")
    List<GradeBookRecord> findByGradeBookItem_ItemIdIn(@Param("itemIds") List<Long> itemIds);

    @Query("SELECT r FROM GradeBookRecord r WHERE r.gradeBookItem.category.categoryId = :categoryId")
    List<GradeBookRecord> findByCategoryId(@Param("categoryId") Long categoryId);


    /**
     * Finds a grade book record by the associated grade book item.
     *
     * @param gradeBookItem The grade book item associated with the record.
     * @return The grade book record, if found.
     */
    Optional<GradeBookRecord> findByGradeBookItem(GradeBookItem gradeBookItem);

    /**
     * Finds grade book records associated with a specific assignment ID.
     *
     * @param assignmentId the ID of the assignment
     * @return a list of grade book records
     */
    @Query("SELECT r FROM GradeBookRecord r WHERE r.gradeBookItem.linkedEntityType = 'ASSIGNMENT' AND r.gradeBookItem.linkedEntityId = :assignmentId")
    List<GradeBookRecord> findByGradeBookItem_AssignmentId(@Param("assignmentId") Long assignmentId);

    /**
     * Finds all grade book records for a specific assessment ID.
     *
     * @param assessmentId The ID of the assessment.
     * @return A list of GradeBookRecord entities.
     */
    @Query("SELECT r FROM GradeBookRecord r WHERE r.gradeBookItem.linkedEntityId = :assessmentId AND r.gradeBookItem.linkedEntityType = 'ASSESSMENT'")
    List<GradeBookRecord> findByGradeBookItem_AssessmentId(@Param("assessmentId") Long assessmentId);


    // Find all verified records for a grade book item
    List<GradeBookRecord> findByGradeBookItem_ItemIdAndIsVerifiedTrue(Long itemId);

    // Find all locked records for a grade book item
    List<GradeBookRecord> findByGradeBookItem_ItemIdAndIsLockedTrue(Long itemId);

}
