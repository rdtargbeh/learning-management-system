package learning_management_system.backend.repository;

import learning_management_system.backend.entity.GradeBookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeBookCategoryRepository extends JpaRepository<GradeBookCategory, Long> {

    List<GradeBookCategory> findByGradeBook_GradeBookId(Long gradeBookId);

    @Query("SELECT c FROM GradeBookCategory c WHERE c.gradeBook.course.courseId = :courseId")
    List<GradeBookCategory> findByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT c FROM GradeBookCategory c WHERE c.weight IS NOT NULL AND c.gradeBook.gradeBookId = :gradeBookId")
    List<GradeBookCategory> findWeightedCategoriesByGradeBookId(@Param("gradeBookId") Long gradeBookId);

    @Query("SELECT SUM(c.weight) FROM GradeBookCategory c WHERE c.gradeBook.gradeBookId = :gradeBookId")
    Double sumWeightsByGradeBookId(@Param("gradeBookId") Long gradeBookId);

    @Query("SELECT AVG(r.score) FROM GradeBookRecord r WHERE r.gradeBookItem.category.categoryId = :categoryId")
    Double calculateAverageScoreByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(i) FROM GradeBookItem i WHERE i.category.categoryId = :categoryId")
    Long countByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * Calculate the total weight of all categories in a grade book.
     *
     * @param gradeBookId ID of the grade book
     * @return Total weight
     */
    @Query("SELECT SUM(category.weight) FROM GradeBookCategory category WHERE category.gradeBook.gradeBookId = :gradeBookId")
    Double calculateTotalWeightByGradeBook(@Param("gradeBookId") Long gradeBookId);

    @Query("SELECT SUM(item.maxPoints) FROM GradeBookItem item WHERE item.category.categoryId = :categoryId")
    Double calculateTotalPointsByCategory(@Param("categoryId") Long categoryId);



//    @Query("SELECT c FROM GradeBookCategory c WHERE c.gradeBook.gradeBookId = :gradeBookId")
//    List<GradeBookCategory> findByGradeBook_GradeBookId(@Param("gradeBookId") Long gradeBookId);



}
