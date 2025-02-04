package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.entity.Feedback;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.FeedbackStatus;
import learning_management_system.backend.enums.FeedbackType;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.repository.FeedbackRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Mapper(componentModel = "spring")

@Component
public class  FeedbackMapper {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    public FeedbackMapper(UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Maps a Feedback entity to a FeedbackDto.
     */
    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();
        dto.setFeedbackId(feedback.getFeedbackId());
        dto.setLinkedEntityId(feedback.getLinkedEntityId());
        dto.setLinkedEntityType(feedback.getLinkedEntityType());
        dto.setFeedbackText(feedback.getFeedbackText());
        dto.setFeedbackType(feedback.getFeedbackType() != null ? feedback.getFeedbackType().name() : null);
        dto.setParentFeedbackId(feedback.getParentFeedback() != null ? feedback.getParentFeedback().getFeedbackId() : null);
        dto.setVisibility(feedback.getVisibility() != null ? feedback.getVisibility().name() : null);
        dto.setAnonymous(feedback.getAnonymous());
        dto.setPeerReview(feedback.getPeerReview());

        // Concatenate firstName and lastName for creator and recipient
        dto.setCreatorId(feedback.getCreator() != null ? feedback.getCreator().getUserId() : null);
        dto.setCreatorName(feedback.getCreator() != null
                ? feedback.getCreator().getFirstName() + " " + feedback.getCreator().getLastName()
                : null);

        dto.setRecipientId(feedback.getRecipient() != null ? feedback.getRecipient().getUserId() : null);
        dto.setRecipientName(feedback.getRecipient() != null
                ? feedback.getRecipient().getFirstName() + " " + feedback.getRecipient().getLastName()
                : null);

        dto.setRubricCriteria(feedback.getRubricCriteria());
        dto.setRating(feedback.getRating());
        dto.setMetadata(feedback.getMetadata());
        dto.setStatus(feedback.getStatus() != null ? feedback.getStatus().name() : null);
        dto.setDateRead(feedback.getDateRead());
        dto.setDateResponded(feedback.getDateResponded());
        dto.setDateCreated(feedback.getDateCreated());
        dto.setDateUpdated(feedback.getDateUpdated());
        dto.setTags(feedback.getTags() != null ? new ArrayList<>(feedback.getTags()) : null);
        return dto;
    }

    /**
     * Maps a FeedbackDto to a Feedback entity.
     */
    public Feedback toEntity(FeedbackDto dto) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(dto.getFeedbackId());
        feedback.setLinkedEntityId(dto.getLinkedEntityId());
        feedback.setLinkedEntityType(dto.getLinkedEntityType());
        feedback.setFeedbackText(dto.getFeedbackText());
        feedback.setFeedbackType(dto.getFeedbackType() != null ? FeedbackType.valueOf(dto.getFeedbackType()) : null);

        // Fetch and set parent feedback
        if (dto.getParentFeedbackId() != null) {
            Feedback parentFeedback = feedbackRepository.findById(dto.getParentFeedbackId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent feedback not found with ID: " + dto.getParentFeedbackId()));
            feedback.setParentFeedback(parentFeedback);
        }

        feedback.setVisibility(dto.getVisibility() != null ? VisibleTo.valueOf(dto.getVisibility()) : null);
        feedback.setAnonymous(dto.getAnonymous());
        feedback.setPeerReview(dto.getPeerReview());

        // Fetch and set creator
        if (dto.getCreatorId() != null) {
            User creator = userRepository.findById(dto.getCreatorId())
                    .orElseThrow(() -> new IllegalArgumentException("Creator not found with ID: " + dto.getCreatorId()));
            feedback.setCreator(creator);
        }

        // Fetch and set recipient
        if (dto.getRecipientId() != null) {
            User recipient = userRepository.findById(dto.getRecipientId())
                    .orElseThrow(() -> new IllegalArgumentException("Recipient not found with ID: " + dto.getRecipientId()));
            feedback.setRecipient(recipient);
        }

        feedback.setRubricCriteria(dto.getRubricCriteria());
        feedback.setRating(dto.getRating());
        feedback.setMetadata(dto.getMetadata());
        feedback.setStatus(dto.getStatus() != null ? FeedbackStatus.valueOf(dto.getStatus()) : null);
        feedback.setDateRead(dto.getDateRead());
        feedback.setDateResponded(dto.getDateResponded());
        feedback.setDateCreated(dto.getDateCreated());
        feedback.setDateUpdated(dto.getDateUpdated());
        feedback.setTags(dto.getTags() != null ? new ArrayList<>(dto.getTags()) : null);
        return feedback;
    }

}
