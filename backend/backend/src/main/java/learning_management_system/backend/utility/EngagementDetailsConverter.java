package learning_management_system.backend.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter
public class EngagementDetailsConverter implements AttributeConverter<List<EngagementDetail>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<EngagementDetail> engagementDetails) {
        try {
            return objectMapper.writeValueAsString(engagementDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing engagement details", e);
        }
    }

    @Override
    public List<EngagementDetail> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(dbData, new TypeReference<List<EngagementDetail>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing engagement details", e);
        }
    }

}

