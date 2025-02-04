package learning_management_system.backend.repository;

import learning_management_system.backend.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for the CourseModule entity.
 */

@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
//    List<CourseModule> findByCourseId(Long courseId);

    /** Find modules by course ID. */
    @Query("SELECT m FROM CourseModule m WHERE m.course.courseId = :courseId")
    List<CourseModule> findByCourseId(@Param("courseId") Long courseId);

    /** Find published modules for a course. */
    @Query("SELECT m FROM CourseModule m WHERE m.course.courseId = :courseId AND m.isPublished = true")
    List<CourseModule> findPublishedModulesByCourseId(@Param("courseId") Long courseId);

//    List<CourseModule> findByVisibleToGroups_GroupId(Long groupId);

    /**
     * Find all modules for a specific course, ordered by the module order.
     *
     * @param courseId the ID of the course.
     * @return a list of course modules.
     */
    List<CourseModule> findByCourseCourseIdOrderByOrderAsc(Long courseId);

    /**
     * Find all submodules for a specific parent module.
     *
     * @param parentModuleId the ID of the parent module.
     * @return a list of submodules.
     */
    List<CourseModule> findByParentModuleModuleId(Long parentModuleId);

    /**
     * Find all published modules in a course.
     *
     * @param courseId the ID of the course.
     * @return a list of published modules.
     */
    List<CourseModule> findByCourseCourseIdAndIsPublishedTrue(Long courseId);

    /**
     * Find all modules released before a specific date.
     *
     * @param releaseDate the release date.
     * @return a list of released modules.
     */
    List<CourseModule> findByReleaseDateBefore(LocalDateTime releaseDate);


}

