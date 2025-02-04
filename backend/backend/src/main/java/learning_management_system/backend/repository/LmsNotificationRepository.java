package learning_management_system.backend.repository;

import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LmsNotificationRepository extends JpaRepository<LmsNotification, Long> {
    // Find all notifications for a specific user
    List<LmsNotification> findByRecipient_UserId(Long userId);

    // Find notifications linked to a specific entity (e.g., course, assignment)
    List<LmsNotification> findByRelatedEntityIdAndRelatedEntityType(Long relatedEntityId, String relatedEntityType);

    // Find unread notifications for a specific user
    List<LmsNotification> findByRecipient_UserIdAndIsRead(Long userId, Boolean isRead);

    List<LmsNotification> findByTenantTenantId(Long tenantId);

    // Query to find notifications for a specific staff member
    @Query("SELECT n FROM LmsNotification n WHERE n.recipient.id IN " +
            "(SELECT s.user.userId FROM Staff s WHERE s.id = :staffId)")
    List<LmsNotification> findNotificationsForStaff(@Param("staffId") Long staffId);

    @Query("SELECT n FROM LmsNotification n WHERE n.recipient.id IN " +
            "(SELECT st.user.id FROM Student st WHERE st.id = :studentId)")
    List<LmsNotification> findNotificationsForStudent(@Param("studentId") Long studentId);

    @Query("SELECT n FROM LmsNotification n WHERE n.recipient.id IN " +
            "(SELECT a.user.id FROM Admin a WHERE a.id = :adminId)")
    List<LmsNotification> findNotificationsForAdmin(@Param("adminId") Long adminId);

    List<LmsNotification> findByType(String type);

    List<LmsNotification> findByRecurrenceIsNotNull();

    List<LmsNotification> findByScheduledDateBefore(LocalDateTime now);

    List<LmsNotification> findByExpiresDateBefore(LocalDateTime now);


    /**
     * Finds notifications by type and status.
     *
     * @param type   the type of notification (e.g., "SYSTEM", "USER").
     * @param status the status of the notification (e.g., "SENT", "PENDING").
     * @return a list of notifications matching the criteria.
     */
    List<LmsNotification> findByTypeAndStatus(String type, NotificationStatus status);


//    Optional<LmsNot     ification> findById(Long id); // Ensure this is mapped to LmsNotification

    // Find notifications of a specific type (e.g., announcement, grading)

//







}
