package learning_management_system.backend.mapper;


import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.stereotype.Component;


// Helper class for User to resolve issues in AssessmentMapper Interface
@Component
public class UserResolver {

    private final UserRepository userRepository;

    public UserResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}

