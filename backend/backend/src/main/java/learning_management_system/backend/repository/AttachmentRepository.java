package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
//    List<Attachment> findByLinkedEntityTypeAndLinkedEntityId(String linkedEntityType, Long linkedEntityId);

    List<Attachment> findByLinkedEntityIdAndLinkedEntityType(Long linkedEntityId, String linkedEntityType);

    @Query("SELECT a FROM Attachment a WHERE a.uploadedBy.userId = :userId")
    List<Attachment> findByUploader(@Param("userId") Long userId);

    @Query("SELECT a FROM Attachment a WHERE a.visibility = :visibility")
    List<Attachment> findByVisibility(@Param("visibility") VisibleTo visibility);

    @Query("SELECT COUNT(a) FROM Attachment a WHERE a.linkedEntityId = :linkedEntityId")
    long countByLinkedEntityId(@Param("linkedEntityId") Long linkedEntityId);

    @Query("SELECT a FROM Attachment a WHERE a.linkedEntityType = 'MODULE_CONTENT' AND a.linkedEntityId = :contentId")
    List<Attachment> findAttachmentsByModuleContentId(@Param("contentId") Long contentId);

    @Query("SELECT SUM(a.fileSize) FROM Attachment a WHERE a.linkedEntityType = 'TENANT' AND a.linkedEntityId = :tenantId")
    Long findTotalSizeByTenantId(@Param("tenantId") Long tenantId);


}

