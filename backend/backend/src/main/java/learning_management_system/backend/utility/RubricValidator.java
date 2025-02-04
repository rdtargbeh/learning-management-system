package learning_management_system.backend.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RubricValidator {
    public static boolean isValidRubric(String rubricJson) {
        try {
            // Validate against a predefined JSON schema
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rubricNode = objectMapper.readTree(rubricJson);

            // Validate structure
            if (!rubricNode.has("criteria") || !rubricNode.get("criteria").isArray()) {
                return false;
            }
            // Additional validation logic as per rubric schema
            return true;

            // Add additional validation logic here
//            return rubricNode.has("criteria") && rubricNode.has("weights");
        } catch (JsonProcessingException e) {
            return false;
        }
    }

}
