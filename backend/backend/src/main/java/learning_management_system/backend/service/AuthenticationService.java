package learning_management_system.backend.service;

import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the currently authenticated user from the SecurityContext.
     *
     * @return User object representing the authenticated user.
     * @throws RuntimeException if the user is not found or the context is invalid.
     */
    public User getCurrentUser() {
        // Retrieve the username of the currently authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            username = (String) principal; // For cases where username is directly stored
        } else {
            throw new RuntimeException("Invalid authentication principal");
        }

        // Fetch the user entity from the database
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found: " + username));
    }
}
