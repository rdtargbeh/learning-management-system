package learning_management_system.backend.repository;

import learning_management_system.backend.entity.GradeBook;
import learning_management_system.backend.enums.GradingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeBookRepository extends JpaRepository<GradeBook, Long> {

    Optional<GradeBook> findByCourse_CourseId(Long courseId);

    List<GradeBook> findByGradingPolicy(GradingPolicy gradingPolicy);

    @Query("SELECT gb FROM GradeBook gb WHERE gb.completionPercentage > :threshold")
    List<GradeBook> findGradeBooksByCompletionPercentage(@Param("threshold") Double threshold);

    @Query("SELECT gb FROM GradeBook gb JOIN FETCH gb.categories WHERE gb.gradeBookId = :gradeBookId")
    Optional<GradeBook> findByIdWithCategories(@Param("gradeBookId") Long gradeBookId);

    /**
     * Calculates the total points of all grade book items in a given grade book.
     *
     * @param gradeBookId The ID of the grade book.
     * @return The total points for the grade book.
     */
    @Query("SELECT SUM(item.maxPoints) FROM GradeBookItem item WHERE item.category.gradeBook.gradeBookId = :gradeBookId")
    Double calculateTotalPoints(@Param("gradeBookId") Long gradeBookId);

    /**
     * Calculates the total achieved points for a given grade book.
     *
     * @param gradeBookId The ID of the grade book.
     * @return The total achieved points for the grade book.
     */
    @Query("SELECT SUM(record.score) FROM GradeBookRecord record " +
            "WHERE record.gradeBookItem.category.gradeBook.gradeBookId = :gradeBookId AND record.isFinalized = true")
    Double calculateAchievedPoints(@Param("gradeBookId") Long gradeBookId);


}
