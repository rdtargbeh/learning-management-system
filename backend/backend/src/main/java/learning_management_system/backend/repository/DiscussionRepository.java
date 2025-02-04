package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    @Query("SELECT d FROM Discussion d WHERE d.course.courseId = :courseId")
    List<Discussion> findDiscussionsByCourseId(@Param("courseId") Long courseId);



    @Query("SELECT d FROM Discussion d WHERE d.isPinned = true AND d.course.courseId = :courseId")
    List<Discussion> findPinnedDiscussions(@Param("courseId") Long courseId);

    @Query("SELECT d FROM Discussion d WHERE d.status = :status AND d.course.courseId = :courseId")
    List<Discussion> findByStatusAndCourseId(@Param("status") DiscussionStatus status, @Param("courseId") Long courseId);

    @Query("SELECT d FROM Discussion d WHERE d.visibility = :visibility")
    List<Discussion> findByVisibility(@Param("visibility") VisibleTo visibility);

    @Query("SELECT d FROM Discussion d WHERE d.author.userId = :authorId")
    List<Discussion> findByAuthor(@Param("authorId") Long authorId);

    @Query("SELECT d FROM Discussion d WHERE d.course.courseId = :courseId AND d.visibility = :visibility")
    Page<Discussion> findDiscussionsByCourseIdAndVisibility(@Param("courseId") Long courseId, @Param("visibility") VisibleTo visibility, Pageable pageable);


    @Query("SELECT d FROM Discussion d WHERE d.status = 'OPEN' AND d.course.courseId = :courseId")
    List<Discussion> findOpenDiscussionsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT d FROM Discussion d WHERE d.course.courseId = :courseId AND d.visibility = 'PUBLIC'")
    List<Discussion> findPublicDiscussionsByCourseId(@Param("courseId") Long courseId);
}


