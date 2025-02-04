package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradeBookRecordDto;
import learning_management_system.backend.entity.GradeBookItem;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.GradeBookItemRepository;
import learning_management_system.backend.repository.StudentRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GradeBookRecordMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private StudentRepository studentRepository;

    /** Converts a `GradeBookRecord` entity to a `GradeBookRecordDto`.*/
    public GradeBookRecordDto toDto(GradeBookRecord gradeBookRecord) {
        GradeBookRecordDto dto = new GradeBookRecordDto();
        dto.setRecordId(gradeBookRecord.getRecordId());
        dto.setGradeBookItemId(gradeBookRecord.getGradeBookItem() != null
                ? gradeBookRecord.getGradeBookItem().getItemId()
                : null);
        dto.setStudentId(gradeBookRecord.getStudent() != null
                ? gradeBookRecord.getStudent().getUser().getUserId()
                : null);
        dto.setStudentName(gradeBookRecord.getStudent() != null
                ? gradeBookRecord.getStudent().getUser().getFirstName() + " "
                + gradeBookRecord.getStudent().getUser().getLastName()
                : null);

        dto.setScore(gradeBookRecord.getScore());
        dto.setFeedback(gradeBookRecord.getFeedback());
        dto.setFinalized(gradeBookRecord.getFinalized());
        dto.setVerified(gradeBookRecord.getVerified());
        dto.setVerifiedBy(gradeBookRecord.getVerifiedBy());
        dto.setLocked(gradeBookRecord.getLocked());
        dto.setMetadata(gradeBookRecord.getMetadata());
        dto.setDateCreated(gradeBookRecord.getDateCreated());
        dto.setDateUpdated(gradeBookRecord.getDateUpdated());
        return dto;
    }

    /** Converts a `GradeBookRecordDto` to a `GradeBookRecord` entity.*/
    public GradeBookRecord toEntity(GradeBookRecordDto dto) {
        GradeBookRecord gradeBookRecord = new GradeBookRecord();
        gradeBookRecord.setRecordId(dto.getRecordId());

        // Fetch and set GradeBookItem
        GradeBookItem gradeBookItem = gradeBookItemRepository.findById(dto.getGradeBookItemId())
                .orElseThrow(() -> new IllegalArgumentException("GradeBookItem not found with ID: " + dto.getGradeBookItemId()));

        gradeBookRecord.setGradeBookItem(gradeBookItem);  // Make sure this is correct




        // Fetch and set Student
        if (dto.getStudentId() != null) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + dto.getStudentId()));
            gradeBookRecord.setStudent(student);
        }


        gradeBookRecord.setScore(dto.getScore());
        gradeBookRecord.setFeedback(dto.getFeedback());
        gradeBookRecord.setFinalized(dto.getFinalized());
        gradeBookRecord.setVerified(dto.getVerified());

        // Set VerifiedBy
        if (dto.getVerifiedBy() != null && dto.getVerifiedBy().getUserId() != null) {
            User verifiedBy = userRepository.findById(dto.getVerifiedBy().getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("VerifiedBy user not found with ID: " + dto.getVerifiedBy().getUserId()));
            gradeBookRecord.setVerifiedBy(verifiedBy);
        }

        gradeBookRecord.setLocked(dto.getLocked());
        gradeBookRecord.setMetadata(dto.getMetadata());
        gradeBookRecord.setDateCreated(dto.getDateCreated());
        gradeBookRecord.setDateUpdated(dto.getDateUpdated());
        return gradeBookRecord;
    }


}

