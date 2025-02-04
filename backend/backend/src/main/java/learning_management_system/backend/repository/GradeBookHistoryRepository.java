package learning_management_system.backend.repository;

import learning_management_system.backend.entity.GradeBookHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GradeBookHistoryRepository extends JpaRepository<GradeBookHistory, Long> {

    // Fetch all history entries for a specific record
    List<GradeBookHistory> findByGradeBookRecord_RecordId(Long recordId);

    Page<GradeBookHistory> findByGradeBookRecord_RecordId(Long recordId, Pageable pageable);

    // Fetch all history entries by a specific user
    List<GradeBookHistory> findByChangedBy_UserId(Long userId);


    @Query("SELECT h FROM GradeBookHistory h WHERE " +
            "(:userId IS NULL OR h.changedBy.userId = :userId) AND " +
            "(:recordId IS NULL OR h.gradeBookRecord.recordId = :recordId) AND " +
            "(:startDate IS NULL OR h.dateChanged >= :startDate) AND " +
            "(:endDate IS NULL OR h.dateChanged <= :endDate) " +
            "ORDER BY h.dateChanged DESC")
    List<GradeBookHistory> findFilteredHistory(
            @Param("userId") Long userId,
            @Param("recordId") Long recordId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COUNT(h) FROM GradeBookHistory h WHERE h.changedBy.userId = :userId")
    Long countChangesByUser(@Param("userId") Long userId);

    @Query("SELECT h.changeReason, COUNT(h) FROM GradeBookHistory h GROUP BY h.changeReason")
    List<Object[]> countChangesByReason();

}

