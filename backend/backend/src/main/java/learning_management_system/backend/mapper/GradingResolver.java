package learning_management_system.backend.mapper;

import learning_management_system.backend.entity.Grading;
import learning_management_system.backend.repository.GradingRepository;
import org.springframework.stereotype.Component;

// Helper class for Grading to resolve issues in AssessmentMapper Interface
@Component
public class GradingResolver {

    private final GradingRepository gradingRepository;

    public GradingResolver(GradingRepository gradingRepository) {
        this.gradingRepository = gradingRepository;
    }

    public Grading findById(Long gradingId) {
        if (gradingId == null) return null;
        return gradingRepository.findById(gradingId)
                .orElseThrow(() -> new RuntimeException("Grading not found with ID: " + gradingId));
    }
}

