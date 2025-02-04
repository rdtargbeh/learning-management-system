package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Question;
import learning_management_system.backend.enums.QuestionDifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>,JpaSpecificationExecutor<Question> {

    List<Question> findByAssessment_AssessmentId(Long assessmentId);

    List<Question> findByQuestionBank_QuestionBankId(Long questionBankId);

    @Query("SELECT q FROM Question q WHERE q.tags LIKE %:tag%")
    List<Question> findByTag(@Param("tag") String tag);

    @Query("SELECT q FROM Question q WHERE q.difficulty = :difficulty")
    List<Question> findByDifficulty(@Param("difficulty") QuestionDifficultyLevel difficulty);


    /**
     * Count the number of questions linked to a specific question bank.
     *
     * @param questionBankId ID of the question bank.
     * @return Number of questions in the question bank.
     */
    long countByQuestionBank_QuestionBankId(Long questionBankId);




}
