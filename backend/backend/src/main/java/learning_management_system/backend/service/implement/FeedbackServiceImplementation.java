package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.entity.Feedback;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.mapper.FeedbackMapper;
import learning_management_system.backend.repository.FeedbackRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.FeedbackService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImplementation implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private LmsNotificationService notificationService;


    @Override
    @Transactional
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = feedbackMapper.toEntity(feedbackDto);

        // Validate creator
        User creator = userRepository.findById(feedbackDto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found."));
        feedback.setCreator(creator);

        // Validate recipient (optional)
        if (feedbackDto.getRecipientId() != null) {
            User recipient = userRepository.findById(feedbackDto.getRecipientId())
                    .orElseThrow(() -> new RuntimeException("Recipient not found."));
            feedback.setRecipient(recipient);
        }

        // Save feedback
        Feedback savedFeedback = feedbackRepository.save(feedback);

        // Notify recipient
        if (feedback.getRecipient() != null) {
            notificationService.createAndSendNotification(
                    feedback.getRecipient().getUserId(),
                    "You have received feedback.",
                    feedback.getFeedbackId(),
                    RelatedEntityType.FEEDBACK,
                    null
            );
        }

        return feedbackMapper.toDto(savedFeedback);
    }

    @Override
    public List<FeedbackDto> getReplies(Long parentFeedbackId) {
        List<Feedback> replies = feedbackRepository.findByParentFeedbackId(parentFeedbackId);
        return replies.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDto getFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found."));
        return feedbackMapper.toDto(feedback);
    }

    @Override
    public List<FeedbackDto> getFeedbackByRecipient(Long recipientId) {
        return feedbackRepository.findByRecipient_UserId(recipientId).stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getFeedbackByEntity(String entityType, Long entityId) {
        List<Feedback> feedbacks = feedbackRepository.findByLinkedEntityTypeAndLinkedEntityId(entityType, entityId);
        return feedbacks.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void markFeedbackAsRead(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found."));
        feedback.setDateRead(new Date());
        feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        if (!feedbackRepository.existsById(feedbackId)) {
            throw new IllegalArgumentException("Feedback not found.");
        }
        feedbackRepository.deleteById(feedbackId);
    }

    public List<FeedbackDto> getFeedbackGivenByUser(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByCreator_UserId(userId);
        return feedbacks.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FeedbackDto> getFeedbackReceivedByUser(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByRecipient_UserId(userId);
        return feedbacks.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    // Fetch feedback with specific visibility
    public List<FeedbackDto> getFeedbackByVisibility(String visibility) {
        List<Feedback> feedbacks = feedbackRepository.findByVisibility(visibility);
        return feedbacks.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getPeerReviews() {
        List<Feedback> peerReviews = feedbackRepository.findAllPeerReviews();
        return peerReviews.stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }
}
