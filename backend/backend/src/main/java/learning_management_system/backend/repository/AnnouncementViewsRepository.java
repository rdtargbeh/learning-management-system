package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Announcement;
import learning_management_system.backend.entity.AnnouncementView;
import learning_management_system.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementViewsRepository extends JpaRepository<AnnouncementView, Long> {

    boolean existsByAnnouncementAndUser(Announcement announcement, User user);

    // Save a new announcement view
    @Modifying
    @Query(value = "INSERT INTO Announcement_Views (announcement_id, user_id, viewed_at) VALUES (:announcementId, :userId, CURRENT_TIMESTAMP)", nativeQuery = true)
    void saveAnnouncementView(@Param("announcementId") Long announcementId, @Param("userId") Long userId);


//    @Modifying
//    @Query("INSERT INTO AnnouncementView (announcementId, userId, viewedAt) VALUES (:announcementId, :userId, CURRENT_TIMESTAMP)")
//    void saveAnnouncementView(@Param("announcementId") Long announcementId, @Param("userId") Long userId);

    Page<AnnouncementView> findByAnnouncement(Announcement announcement, Pageable pageable);

    void deleteByAnnouncement(Announcement announcement);

    /**
     * Find all AnnouncementView records created since a specific timestamp.
     */
    @Query("SELECT v FROM AnnouncementView v WHERE v.viewedAt >= :since")
    List<AnnouncementView> findViewsSince(@Param("since") LocalDateTime since);

    /**
     * Check if a view record exists for a specific announcement and user.
     *
     * @param announcementId ID of the announcement.
     * @param userId ID of the user.
     * @return true if a view exists, false otherwise.
     */
    @Query("SELECT COUNT(v) > 0 FROM AnnouncementView v WHERE v.announcement.announcementId = :announcementId AND v.user.userId = :userId")
    boolean existsByAnnouncementIdAndUserId(@Param("announcementId") Long announcementId, @Param("userId") Long userId);
}
