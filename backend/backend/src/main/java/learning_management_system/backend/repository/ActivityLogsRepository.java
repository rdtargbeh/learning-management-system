package learning_management_system.backend.repository;

import learning_management_system.backend.entity.ActivityLogs;
import learning_management_system.backend.enums.ActionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Repository for managing ActivityLog entities.
 * Provides methods for querying the activity logs.
 */

@Repository
public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, Long> {

    /**
     * Finds logs for a specific user by their user ID.
     *
     * @param userId The ID of the user.
     * @return A list of ActivityLog entities.
     */
    List<ActivityLogs> findByUser_UserId(Long userId);

    // Find all activity logs by activity type
    List<ActivityLogs> findByActionType(String actionType);

    // Find all activity logs within a specific timestamp range
    @Query("SELECT al FROM ActivityLogs al WHERE al.timestamp BETWEEN :start AND :end")
    List<ActivityLogs> findByTimestampBetween(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT al FROM ActivityLogs al WHERE al.user.userId = :staffId")
    List<ActivityLogs> findActivityLogsByStaffId(@Param("staffId") Long staffId);

    // Get logs for a specific user
    @Query("SELECT al FROM ActivityLogs al WHERE al.user.userId = :userId")
    List<ActivityLogs> findLogsByUserId(@Param("userId") Long userId);

    // Get logs for students
    @Query("SELECT al FROM ActivityLogs al WHERE al.user.userId IN " +
            "(SELECT s.user.userId FROM Student s WHERE s.id = :studentId)")
    List<ActivityLogs> findLogsForStudent(@Param("studentId") Long studentId);

    // Find logs for a specific staff member
    @Query("SELECT al FROM ActivityLogs al WHERE al.user.userId IN " +
            "(SELECT st.user.userId FROM Staff st WHERE st.id = :staffId)")
    List<ActivityLogs> findLogsForStaff(@Param("staffId") Long staffId);

    // Find logs for a specific admin
    @Query("SELECT al FROM ActivityLogs al WHERE al.user.userId IN " +
            "(SELECT a.user.userId FROM Admin a WHERE a.id = :adminId)")
    List<ActivityLogs> findLogsForAdmin(@Param("adminId") Long adminId);

    @Query("SELECT al FROM ActivityLogs al WHERE al.user.id IN " +
            "(SELECT a.user.id FROM Admin a WHERE a.id = :adminId)")
    List<ActivityLogs> findActivityLogsForAdmin(@Param("adminId") Long adminId);


    /**
     * Finds logs by category and a date range.
     *
     * @param actionCategory The category of the action.
     * @param start          The start of the date range.
     * @param end            The end of the date range.
     * @return A list of ActivityLog entities.
     */
    List<ActivityLogs> findByActionCategoryAndTimestampBetween(ActionCategory actionCategory, Date start, Date end);

    /**
     * Finds logs related to a specific resource.
     *
     * @param resourceType The type of resource.
     * @param resourceId   The ID of the resource.
     * @return A list of ActivityLog entities.
     */
    @Query("SELECT a FROM ActivityLogs a WHERE a.resourceType = :resourceType AND a.resourceId = :resourceId")
    List<ActivityLogs> findByResourceTypeAndResourceId(String resourceType, Long resourceId);

    /**
     * Finds logs by IP address.
     *
     * @param ipAddress The IP address to search for.
     * @return A list of ActivityLog entities.
     */
    List<ActivityLogs> findByIpAddress(String ipAddress);



}