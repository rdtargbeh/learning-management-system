package learning_management_system.backend.utility;

import learning_management_system.backend.entity.ProctoringSession;
import learning_management_system.backend.enums.ProctoringStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThirdPartyProctoringServiceImpl implements ThirdPartyProctoringService {

    private static final Logger logger = LoggerFactory.getLogger(ThirdPartyProctoringServiceImpl.class);
    // Optional: Base URL for the third-party service
    private static final String PROCTORING_API_BASE_URL = "https://third-party-proctoring-service.com/api";
    private final RestTemplate restTemplate;

    public ThirdPartyProctoringServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean validateSession(ProctoringSession session) {
        if (!session.isReadyForValidation()) {
            logger.error("Proctoring session is not ready for validation: {}", session.getProctoringSessionId());
            throw new IllegalArgumentException("Proctoring session is incomplete or invalid.");
        }

        logger.info("Validating proctoring session with ID: {}", session.getProctoringSessionId());

        // Call the third-party API
        boolean isValid = thirdPartyApiCall(session);

        // Update session status based on validation outcome
        session.updateStatus(isValid ? ProctoringStatus.APPROVED : ProctoringStatus.REJECTED);

        return isValid;
    }

    private boolean thirdPartyApiCall(ProctoringSession session) {
        // Build the endpoint URL
        String validationEndpoint = PROCTORING_API_BASE_URL + "/validate-session";

        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the request entity
        HttpEntity<ProctoringSession> requestEntity = new HttpEntity<>(session, headers);

        try {
            // Make the API call
            ResponseEntity<Boolean> response = restTemplate.postForEntity(validationEndpoint, requestEntity, Boolean.class);

            // Handle the response
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Proctoring session validation result: {}", response.getBody());
                return response.getBody();
            } else {
                logger.error("Proctoring session validation failed with status: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception ex) {
            logger.error("Error during proctoring session validation: {}", ex.getMessage());
            return false; // Default to invalid if an error occurs
        }
    }
}

