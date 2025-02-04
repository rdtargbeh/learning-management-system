package learning_management_system.backend.service;


import learning_management_system.backend.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {

    FeedbackDto createFeedback(FeedbackDto feedbackDto);

    FeedbackDto getFeedbackById(Long feedbackId);

    List<FeedbackDto> getFeedbackByRecipient(Long recipientId);

    List<FeedbackDto> getFeedbackByEntity(String entityType, Long entityId);

    void deleteFeedback(Long feedbackId);

    List<FeedbackDto> getFeedbackGivenByUser(Long userId);

    List<FeedbackDto> getFeedbackReceivedByUser(Long userId);

    List<FeedbackDto> getFeedbackByVisibility(String visibility);

    List<FeedbackDto> getReplies(Long parentFeedbackId);

    void markFeedbackAsRead(Long feedbackId);

    List<FeedbackDto> getPeerReviews();

}
