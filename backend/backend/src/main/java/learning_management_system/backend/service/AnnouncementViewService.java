package learning_management_system.backend.service;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.entity.AnnouncementView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementViewService {

    void trackAnnouncementView(Long announcementId, Long userId);

//    List<AnnouncementView> getViewsForAnnouncement(Long announcementId);
    Page<AnnouncementView> getViewsForAnnouncement(Long announcementId, Pageable pageable);


    void processAnnouncementViewBatch();

    void logUserAnnouncementView(Long announcementId, Long userId);

    List<AnnouncementDto> getMostViewedAnnouncements(int topN);

    void deleteViewsForAnnouncement(Long announcementId);

    void processTenantAnnouncementViewBatch(String tenantId);

    void markAnnouncementAsViewed(Long announcementId, Long userId);

}
