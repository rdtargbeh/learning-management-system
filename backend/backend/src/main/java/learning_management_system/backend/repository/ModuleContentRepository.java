package learning_management_system.backend.repository;

import learning_management_system.backend.entity.ModuleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for ModuleContent entity.
 */
@Repository
public interface ModuleContentRepository extends JpaRepository<ModuleContent, Long> {

    List<ModuleContent> findByCourseModule_ModuleId(Long moduleId);

    @Query("SELECT c FROM ModuleContent c WHERE c.isPublished = true AND c.module.moduleId = :moduleId")
    List<ModuleContent> findPublishedContentsByModuleId(@Param("moduleId") Long moduleId);

    @Query("SELECT c FROM ModuleContent c WHERE c.isPublished = true AND c.releaseDate <= CURRENT_TIMESTAMP")
    List<ModuleContent> findReleasedContent();

    @Query("SELECT c FROM ModuleContent c WHERE c.availabilityStart <= :currentTime AND (c.availabilityEnd IS NULL OR c.availabilityEnd >= :currentTime)")
    List<ModuleContent> findAvailableContent(@Param("currentTime") LocalDateTime currentTime);

    List<ModuleContent> findByTagsContaining(String tag);



    // Update the property name to match the field in ModuleContent
//    List<ModuleContent> findByIsVisible(Boolean isVisible);


}
