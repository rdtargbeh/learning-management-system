package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradeBookItemDto;
import learning_management_system.backend.entity.GradeBookCategory;
import learning_management_system.backend.entity.GradeBookItem;
import learning_management_system.backend.entity.StudentGroup;
import learning_management_system.backend.repository.GradeBookCategoryRepository;
import learning_management_system.backend.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring", uses = {GradingMapper.class})

@Component
public class GradeBookItemMapper {

    @Autowired
    private GradeBookCategoryRepository gradeBookCategoryRepository;
    @Autowired
    private GradingMapper gradingMapper;
    @Autowired
    private StudentGroupRepository groupRepository;

    /**
     * Converts a GradeBookItem entity to a GradeBookItemDto.
     */
    public GradeBookItemDto toDto(GradeBookItem gradeBookItem) {
        if (gradeBookItem == null) {
            return null;
        }

        GradeBookItemDto dto = new GradeBookItemDto();
        dto.setItemId(gradeBookItem.getItemId());
        dto.setCategoryId(gradeBookItem.getCategory() != null ? gradeBookItem.getCategory().getCategoryId() : null);
        dto.setName(gradeBookItem.getName());
        dto.setLinkedEntityType(gradeBookItem.getLinkedEntityType());
        dto.setLinkedEntityId(gradeBookItem.getLinkedEntityId());
        dto.setDueDate(gradeBookItem.getDueDate());
        dto.setMaxPoints(gradeBookItem.getMaxPoints());
        dto.setGrading(gradingMapper.toDto(gradeBookItem.getGrading()));
        dto.setGroupId(gradeBookItem.getStudentGroup() != null ? gradeBookItem.getStudentGroup().getGroupId() : null);
        dto.setGradeVerificationRequired(gradeBookItem.getGradeVerificationRequired());
        dto.setGroupGrading(gradeBookItem.getGroupGrading());
        dto.setMetadata(gradeBookItem.getMetadata());
        dto.setDateCreated(gradeBookItem.getDateCreated());
        dto.setDateUpdated(gradeBookItem.getDateUpdated());
        return dto;
    }

    /**
     * Converts a GradeBookItemDto to a GradeBookItem entity.
     */
    public GradeBookItem toEntity(GradeBookItemDto dto) {
        if (dto == null) {
            return null;
        }

        GradeBookItem gradeBookItem = new GradeBookItem();
        gradeBookItem.setItemId(dto.getItemId());
        gradeBookItem.setName(dto.getName());
        gradeBookItem.setLinkedEntityType(dto.getLinkedEntityType());
        gradeBookItem.setLinkedEntityId(dto.getLinkedEntityId());
        gradeBookItem.setDueDate(dto.getDueDate());
        gradeBookItem.setMaxPoints(dto.getMaxPoints());
        gradeBookItem.setGradeVerificationRequired(dto.getGradeVerificationRequired());
        gradeBookItem.setGroupGrading(dto.getGroupGrading());
        gradeBookItem.setMetadata(dto.getMetadata());
        gradeBookItem.setDateCreated(dto.getDateCreated());
        gradeBookItem.setDateUpdated(dto.getDateUpdated());

        // Fetch and set category
        if (dto.getCategoryId() != null) {
            GradeBookCategory category = gradeBookCategoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("GradeBookCategory not found with ID: " + dto.getCategoryId()));
            gradeBookItem.setCategory(category);
        }

        // Fetch and set grading
        if (dto.getGrading() != null) {
            gradeBookItem.setGrading(gradingMapper.toEntity(dto.getGrading()));
        }

        // Fetch and set group
        if (dto.getGroupId() != null) {
            StudentGroup group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + dto.getGroupId()));
            gradeBookItem.setStudentGroup(group);
        }

        return gradeBookItem;
    }

}
