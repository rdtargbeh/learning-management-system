package learning_management_system.backend.repository;

import learning_management_system.backend.entity.GradeBookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GradeBookItemRepository extends JpaRepository<GradeBookItem, Long> {

    @Query("SELECT i FROM GradeBookItem i WHERE i.category.categoryId = :categoryId")
    List<GradeBookItem> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT i FROM GradeBookItem i WHERE i.dueDate BETWEEN :startDate AND :endDate")
    List<GradeBookItem> findByDueDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(i.maxPoints) FROM GradeBookItem i WHERE i.category.gradeBook.gradeBookId = :gradeBookId")
    Double calculateTotalPointsByGradeBook(@Param("gradeBookId") Long gradeBookId);

    @Query("SELECT COUNT(i) FROM GradeBookItem i WHERE i.category.categoryId = :categoryId")
    Long countByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT i FROM GradeBookItem i WHERE i.dueDate < :currentDate AND i.category.gradeBook.gradeBookId = :gradeBookId")
    List<GradeBookItem> findOverdueItems(@Param("currentDate") LocalDateTime currentDate, @Param("gradeBookId") Long gradeBookId);

    @Query("SELECT COUNT(i) > 0 FROM GradeBookItem i WHERE i.name = :name AND i.category.categoryId = :categoryId")
    boolean existsByNameAndCategory_CategoryId(@Param("name") String name, @Param("categoryId") Long categoryId);

    // Find all grade book items with group grading enabled
    List<GradeBookItem> findByIsGroupGradingTrue();

    // Find all grade book items requiring verification
    List<GradeBookItem> findByGradeVerificationRequiredTrue();
}

