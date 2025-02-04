package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.entity.Announcement;
import learning_management_system.backend.entity.AnnouncementView;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.mapper.AnnouncementMapper;
import learning_management_system.backend.repository.AnnouncementRepository;
import learning_management_system.backend.repository.AnnouncementViewsRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.AnnouncementViewService;
import learning_management_system.backend.service.LmsNotificationService;
import learning_management_system.backend.utility.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnnouncementViewServiceImplementation implements AnnouncementViewService {

    @Autowired
    private AnnouncementViewsRepository announcementViewsRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private AnnouncementMapper announcementMapper;


    private static final Logger log = LoggerFactory.getLogger(AnnouncementViewService.class);

    @Override
    @Transactional
    public void trackAnnouncementView(Long announcementId, Long userId) {
        // Fetch the announcement and user
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Check if the view already exists
        if (!announcementViewsRepository.existsByAnnouncementAndUser(announcement, user)) {
            AnnouncementView view = new AnnouncementView();
            view.setAnnouncement(announcement);
            view.setUser(user);
            announcementViewsRepository.save(view);
        }
    }

//    @Override
//    public List<AnnouncementView> getViewsForAnnouncement(Long announcementId) {
//        Announcement announcement = announcementRepository.findById(announcementId)
//                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
//        return announcementViewsRepository.findByAnnouncement(announcement);
//    }

    // Pagination for Views
    @Override
    public Page<AnnouncementView> getViewsForAnnouncement(Long announcementId, Pageable pageable) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        return announcementViewsRepository.findByAnnouncement(announcement, pageable);
    }

    @Override
    @Transactional
    public void logUserAnnouncementView(Long announcementId, Long userId) {
        if (!announcementViewsRepository.existsByAnnouncementIdAndUserId(announcementId, userId)) {
            Announcement announcement = announcementRepository.findById(announcementId)
                    .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            AnnouncementView view = new AnnouncementView();
            view.setAnnouncement(announcement);
            view.setUser(user);
            announcementViewsRepository.save(view);
        }
    }

    /**
     * Process Announcement View Batch (Scheduled Task)
     * This method is scheduled to run hourly to process batch views.
     */
    @Override
    @Scheduled(cron = "0 0 * * * *") // Runs hourly
    @Transactional
    public void processAnnouncementViewBatch() {
        // Fetch all views created in the last hour
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<AnnouncementView> recentViews = announcementViewsRepository.findViewsSince(oneHourAgo);

        // Aggregate view counts by announcement
        Map<Long, Long> viewCountMap = recentViews.stream()
                .collect(Collectors.groupingBy(
                        view -> view.getAnnouncement().getAnnouncementId(),
                        Collectors.counting()
                ));

        // Update view counts for announcements
        viewCountMap.forEach((announcementId, count) -> {
            Announcement announcement = announcementRepository.findById(announcementId)
                    .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
            announcement.setViewCount(announcement.getViewCount() + count.intValue());
            announcementRepository.save(announcement);
        });

        // Logging the number of processed views
        log.info("Processed {} announcement views.", viewCountMap.size());

        // Optional: Notify administrators
        notificationService.notifyAdmins("Processed announcement views for the past hour.");
    }

    // Fetch Most Viewed Announcements
    @Override
    public List<AnnouncementDto> getMostViewedAnnouncements(int topN) {
        return announcementRepository.findAllByOrderByViewCountDesc(PageRequest.of(0, topN))
                .stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }


    // Delete Views for an Announcement
    @Override
    @Transactional
    public void deleteViewsForAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        announcementViewsRepository.deleteByAnnouncement(announcement);
    }

    @Override
    @Transactional
    public void processTenantAnnouncementViewBatch(String tenantId) {
        TenantContext.setTenant(tenantId); // Set the tenant context dynamically
        processAnnouncementViewBatch();   // Reuse existing batch logic
    }

    @Transactional
    public void markAnnouncementAsViewed(Long announcementId, Long userId) {
        announcementViewsRepository.saveAnnouncementView(announcementId, userId);
    }

}
