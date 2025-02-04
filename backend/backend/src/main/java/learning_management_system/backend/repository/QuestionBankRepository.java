package learning_management_system.backend.repository;

import learning_management_system.backend.entity.QuestionBank;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.QuestionBankType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {

    List<QuestionBank> findByNameContainingIgnoreCase(String name);

    List<QuestionBank> findByTagsContainingIgnoreCase(String tag);

    List<QuestionBank> findByCreatedByUserId(Long userId);

    @Query("SELECT q FROM QuestionBank q WHERE q.type = :type")
    List<QuestionBank> findByType(@Param("type") QuestionBankType type);

    /**
     * Check if a question bank exists with the given name and creator.
     *
     * @param name The name of the question bank.
     * @param createdBy The user who created the question bank.
     * @return True if a question bank exists, otherwise false.
     */
    boolean existsByNameAndCreatedBy(String name, User createdBy);

    @Query("SELECT qb FROM QuestionBank qb WHERE qb.sharedWith LIKE %:userId%")
    Page<QuestionBank> findSharedWithUser(@Param("userId") String userId, Pageable pageable);

//    @Query("SELECT qb FROM QuestionBank qb WHERE qb.sharedWith LIKE %:userId%")
//    List<QuestionBank> findSharedWithUser(@Param("userId") String userId);
}
