package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Assessment;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Submission entity.
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    /**
     * Count the number of submissions for a specific assessment and student.
     *
     * @param assessment The assessment entity.
     * @param student    The student entity.
     * @return The count of submissions.
     */
    long countByAssessmentAndStudent(Assessment assessment, Student student);

    /**
     * Find all submissions by student ID with pagination.
     *
     * @param userId The student's ID.
     * @param pageable  Pagination information.
     * @return Paginated list of submissions.
     */
    Page<Submission> findByStudent_UserId(Long userId, Pageable pageable);

    /**
     * Find all submissions for a specific assignment.
     *
     * @param assignmentId The assignment ID.
     * @return List of submissions.
     */
    List<Submission> findByAssignment_AssignmentId(Long assignmentId);

    /**
     * Find all submissions flagged for plagiarism.
     *
     * @return List of flagged submissions.
     */
    @Query("SELECT s FROM Submission s WHERE s.isFlaggedForPlagiarism = true")
    List<Submission> findFlaggedSubmissions();

    /**
     * Calculate the average marks obtained for a specific assessment.
     *
     * @param assessmentId The assessment ID.
     * @return Average marks.
     */
    @Query("SELECT AVG(s.marksObtained) FROM Submission s WHERE s.assessment.assessmentId = :assessmentId")
    Double findAverageMarksByAssessmentId(Long assessmentId);

    /**
     * Count all submissions for a specific assignment.
     *
     * @param assignmentId The assignment ID.
     * @return Total submission count.
     */
    long countByAssignment_AssignmentId(Long assignmentId);

    /**
     * Find the most recent submission by student for a specific assessment.
     * Fetch the latest submission for an assessment
     *
     * @param studentId    The student's ID.
     * @param assessmentId The assessment ID.
     * @return The most recent submission.
     */
    @Query("SELECT s FROM Submission s WHERE s.student.userId = :studentId AND s.assessment.assessmentId = :assessmentId " +
            "ORDER BY s.submissionDate DESC")
    Submission findLatestSubmissionByStudentAndAssessment(@Param("studentId") Long studentId,
                                                          @Param("assessmentId") Long assessmentId);

    boolean existsByStudent_UserIdAndAssessment_AssessmentId(Long userId, Long assessmentId);

    /**
     * Counts the number of submissions for a specific student and assessment.
     *
     * @param userId   The ID of the student.
     * @param assessmentId The ID of the assessment.
     * @return The number of submissions by the student for the assessment.
     */
    long countByStudent_UserIdAndAssessment_AssessmentId(Long userId, Long assessmentId);

    // Fetch the latest submission for an assignment
    @Query("SELECT s FROM Submission s WHERE s.student.userId = :studentId AND s.assignment.assignmentId = :assignmentId " +
            "ORDER BY s.submissionDate DESC")
    Submission findLatestSubmissionByStudentAndAssignment(@Param("studentId") Long studentId,
                                                          @Param("assignmentId") Long assignmentId);


}
