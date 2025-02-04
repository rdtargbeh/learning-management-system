package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradeBookHistoryDto;
import learning_management_system.backend.entity.GradeBookHistory;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.GradeBookRecordRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
public class GradeBookHistoryMapper {

    private final UserRepository userRepository;
    private final GradeBookRecordRepository gradeBookRecordRepository;

    public GradeBookHistoryMapper(UserRepository userRepository, GradeBookRecordRepository gradeBookRecordRepository) {
        this.userRepository = userRepository;
        this.gradeBookRecordRepository = gradeBookRecordRepository;
    }

    /** Converts a GradeBookHistory entity to a GradeBookHistoryDto.*/
    public GradeBookHistoryDto toDto(GradeBookHistory gradeBookHistory) {
        if (gradeBookHistory == null) {
            return null;
        }

        GradeBookHistoryDto dto = new GradeBookHistoryDto();
        dto.setHistoryId(gradeBookHistory.getHistoryId());
        dto.setRecordId(gradeBookHistory.getGradeBookRecord().getRecordId());
        dto.setPreviousScore(gradeBookHistory.getPreviousScore());
        dto.setNewScore(gradeBookHistory.getNewScore());
        dto.setChangedByUserId(gradeBookHistory.getChangedBy() != null ? gradeBookHistory.getChangedBy().getUserId() : null);
        dto.setChangeReason(gradeBookHistory.getChangeReason());
        dto.setMetadata(gradeBookHistory.getMetadata());
        dto.setDateChanged(gradeBookHistory.getDateChanged());
        return dto;
    }

    /**
     * Converts a GradeBookHistoryDto to a GradeBookHistory entity.
     */
    public GradeBookHistory toEntity(GradeBookHistoryDto dto) {
        if (dto == null) {
            return null;
        }

        GradeBookHistory gradeBookHistory = new GradeBookHistory();
        gradeBookHistory.setHistoryId(dto.getHistoryId());
        gradeBookHistory.setPreviousScore(dto.getPreviousScore());
        gradeBookHistory.setNewScore(dto.getNewScore());
        gradeBookHistory.setChangeReason(dto.getChangeReason());
        gradeBookHistory.setMetadata(dto.getMetadata());
        gradeBookHistory.setDateChanged(dto.getDateChanged());

        // Fetch and set the GradeBookRecord
        if (dto.getRecordId() != null) {
            GradeBookRecord gradeBookRecord = gradeBookRecordRepository.findById(dto.getRecordId())
                    .orElseThrow(() -> new IllegalArgumentException("GradeBookRecord not found with ID: " + dto.getRecordId()));
            gradeBookHistory.setGradeBookRecord(gradeBookRecord);
        }

        // Fetch and set the user who made the change
        if (dto.getChangedByUserId() != null) {
            User user = userRepository.findById(dto.getChangedByUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getChangedByUserId()));
            gradeBookHistory.setChangedBy(user);
        }

        return gradeBookHistory;
    }

}
