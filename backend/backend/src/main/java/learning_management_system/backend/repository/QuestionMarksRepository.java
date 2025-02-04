package learning_management_system.backend.repository;

import learning_management_system.backend.entity.QuestionMarks;
import learning_management_system.backend.utility.QuestionMarksId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionMarksRepository extends JpaRepository<QuestionMarks, QuestionMarksId> {

    @Query("SELECT AVG(qm.marks) FROM QuestionMarks qm WHERE qm.grading.id = :gradingId")
    Double findAverageMarksByGradingId(Long gradingId);

    @Query("SELECT AVG(qm.partialMarks) FROM QuestionMarks qm WHERE qm.grading.id = :gradingId")
    Double findAveragePartialMarksByGradingId(Long gradingId);

    List<QuestionMarks> findById_GradingId(Long gradingId);

    List<QuestionMarks> findByQuestion_QuestionId(Long questionId);

    Page<QuestionMarks> findById_GradingIdAndMarksBetween(
            Long gradingId, int minMarks, int maxMarks, Pageable pageable
    );

//    @Query("SELECT q FROM QuestionMarks q WHERE q.id.gradingId = :gradingId AND q.score BETWEEN :minMarks AND :maxMarks")
//    Page<QuestionMarks> findByGradingIdAndMarksBetween(
//            @Param("gradingId") Long gradingId,
//            @Param("minMarks") int minMarks,
//            @Param("maxMarks") int maxMarks,
//            Pageable pageable
//    );

    /**
     * Finds the average marks for a given question ID.
     *
     * @param questionId The ID of the question.
     * @return The average marks for the question.
     */
    @Query("SELECT AVG(qm.marks) FROM QuestionMarks qm WHERE qm.question.questionId = :questionId")
    Double findAverageMarksByQuestionId(@Param("questionId") Long questionId);

    @Query("SELECT q.difficulty, AVG(m.marks) AS averageMarks FROM QuestionMarks m " +
            "JOIN m.question q WHERE m.grading.id = :gradingId GROUP BY q.difficulty")
    List<Object[]> getDifficultyWisePerformance(@Param("gradingId") Long gradingId);

    @Query("SELECT qm FROM QuestionMarks qm " +
            "WHERE qm.grading.id = :gradingId AND qm.id.questionId = :questionId")
    Optional<QuestionMarks> findByGrading_IdAndStudentId(@Param("gradingId") Long gradingId,
                                                         @Param("questionId") Long questionId);



}