package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.dto.AnnouncementViewDto;
import learning_management_system.backend.entity.AnnouncementView;
import learning_management_system.backend.service.AnnouncementViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementViewController {

    @Autowired
    private AnnouncementViewService announcementViewService;


    @PostMapping("/{announcementId}/track-view")
    public ResponseEntity<Void> trackView(
            @PathVariable Long announcementId,
            @RequestParam Long userId) {
        announcementViewService.trackAnnouncementView(announcementId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get views for a specific announcement with pagination.
     */
    @GetMapping("/{announcementId}/views")
    public ResponseEntity<Page<AnnouncementViewDto>> getViewsForAnnouncement(
            @PathVariable Long announcementId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "viewedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<AnnouncementView> views = announcementViewService.getViewsForAnnouncement(announcementId, pageable);

        Page<AnnouncementViewDto> viewDtos = views.map(view -> new AnnouncementViewDto(
                view.getAnnouncement().getAnnouncementId(),
                view.getUser().getUserId(),
                view.getViewedAt()
        ));
        return ResponseEntity.ok(viewDtos);
    }

    /**
     * Process announcement view batch for all tenants (scheduled task).
     */
    @Scheduled(cron = "0 0 * * * *") // Runs hourly
    @PostMapping("/batch/process")
    public ResponseEntity<Void> processAnnouncementViewBatch() {
        announcementViewService.processAnnouncementViewBatch();
        return ResponseEntity.ok().build();
    }

    /**
     * Log a user's announcement view.
     */
    @PostMapping("/{announcementId}/log-view")
    public ResponseEntity<Void> logUserAnnouncementView(
            @PathVariable Long announcementId,
            @RequestParam Long userId) {
        announcementViewService.logUserAnnouncementView(announcementId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get the most viewed announcements.
     */
    @GetMapping("/most-viewed")
    public ResponseEntity<List<AnnouncementDto>> getMostViewedAnnouncements(
            @RequestParam(defaultValue = "10") int topN) {
        List<AnnouncementDto> mostViewedAnnouncements = announcementViewService.getMostViewedAnnouncements(topN);
        return ResponseEntity.ok(mostViewedAnnouncements);
    }

    /**
     * Delete views for a specific announcement.
     */
    @DeleteMapping("/{announcementId}/views")
    public ResponseEntity<Void> deleteViewsForAnnouncement(@PathVariable Long announcementId) {
        announcementViewService.deleteViewsForAnnouncement(announcementId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Process announcement view batch for a specific tenant.
     */
    @PostMapping("/batch/tenant/{tenantId}/process")
    public ResponseEntity<Void> processTenantAnnouncementViewBatch(@PathVariable String tenantId) {
        announcementViewService.processTenantAnnouncementViewBatch(tenantId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{announcementId}/view")
    public ResponseEntity<Void> markAsViewed(@PathVariable Long announcementId, @RequestParam Long userId) {
        announcementViewService.markAnnouncementAsViewed(announcementId, userId);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{announcementId}/views")
//    public ResponseEntity<List<AnnouncementViewDto>> getViews(@PathVariable Long announcementId) {
//        List<AnnouncementView> views = announcementViewService.getViewsForAnnouncement(announcementId);
//        List<AnnouncementViewDto> viewDtos = views.stream()
//                .map(view -> new AnnouncementViewDto(
//                        view.getAnnouncement().getAnnouncementId(), // Updated to use getAnnouncementId()
//                        view.getUser().getUserId(),                // Updated to use getUserId()
//                        view.getViewedAt())
//                )
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(viewDtos);
//    }

}

