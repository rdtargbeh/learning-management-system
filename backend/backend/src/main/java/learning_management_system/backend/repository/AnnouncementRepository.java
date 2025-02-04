package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing Announcement entities.
 */

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByIsArchivedFalse();

    List<Announcement> findByPublishDateBeforeAndExpiryDateAfter(LocalDateTime now, LocalDateTime nowAgain);

    /**
     * Finds announcements by category.
     * @param category The category to filter by.
     * @return A list of Announcement entities.
     */
    List<Announcement> findByCategory(String category);

    /** Finds pinned announcements.
     * @return A list of pinned Announcement entities.
     */
    List<Announcement> findByIsPinnedTrue();

    @Query("SELECT a FROM Announcement a WHERE a.recurrencePattern IS NOT NULL AND a.isArchived = false")
    List<Announcement> findRecurringAnnouncements();

    // Fetch announcements for a course with pagination
    @Query("SELECT a FROM Announcement a WHERE a.targetEntityType = 'COURSE' AND a.targetEntityId = :courseId")
    Page<Announcement> findByTargetCourse_CourseId(@Param("courseId") Long courseId, Pageable pageable);

    // Search announcements by title or content
    @Query("SELECT a FROM Announcement a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Announcement> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(@Param("keyword") String keyword);

    Page<Announcement> findAllByOrderByViewCountDesc(Pageable pageable);





    /**
     * Finds announcements by visibility.
     * @param visibility The visibility level.
     * @return A list of Announcement entities.
     */
    List<Announcement> findByVisibility(String visibility);

//    List<Announcement> findAllByScheduledAtBeforeAndNotifiedFalse(Date date);
//    List<Announcement> findByCourse_CourseId(Long courseId);
//    List<Announcement> findByCourseIsNull(); // Fetch announcements without an associated course
//    List<Announcement> findByTargetCourse_CourseId(Long courseId);
}
