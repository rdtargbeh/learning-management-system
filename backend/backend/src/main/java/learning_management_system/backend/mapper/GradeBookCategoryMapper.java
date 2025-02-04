package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradeBookCategoryDto;
import learning_management_system.backend.entity.GradeBook;
import learning_management_system.backend.entity.GradeBookCategory;
import learning_management_system.backend.repository.GradeBookRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", uses = {GradeBookItemMapper.class})

@Component
public class GradeBookCategoryMapper {

    private final GradeBookItemMapper gradeBookItemMapper;
    private final GradeBookRepository gradeBookRepository;

    public GradeBookCategoryMapper(GradeBookItemMapper gradeBookItemMapper, GradeBookRepository gradeBookRepository) {
        this.gradeBookItemMapper = gradeBookItemMapper;
        this.gradeBookRepository = gradeBookRepository;
    }

    /**
     * Converts a GradeBookCategory entity to a GradeBookCategoryDto.
     */
    public GradeBookCategoryDto toDto(GradeBookCategory category) {
        if (category == null) {
            return null;
        }

        GradeBookCategoryDto dto = new GradeBookCategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setGradeBookId(category.getGradeBook() != null ? category.getGradeBook().getGradeBookId() : null);
        dto.setName(category.getName());
        dto.setWeight(category.getWeight());
        dto.setTotalPoints(category.getTotalPoints());
        dto.setCurrentPointsAchieved(category.getCurrentPointsAchieved());
        dto.setTotalWeight(category.getTotalWeight());
        dto.setEnableLateDrop(category.getEnableLateDrop());
        dto.setMetadata(category.getMetadata());
        dto.setDateCreated(category.getDateCreated());
        dto.setDateUpdated(category.getDateUpdated());
        dto.setGradeItems(category.getGradeItems() != null
                ? category.getGradeItems().stream()
                .map(gradeBookItemMapper::toDto)
                .collect(Collectors.toList())
                : null);
        return dto;
    }

    /**
     * Converts a GradeBookCategoryDto to a GradeBookCategory entity.
     */
    public GradeBookCategory toEntity(GradeBookCategoryDto dto) {
        if (dto == null) {
            return null;
        }

        GradeBookCategory category = new GradeBookCategory();
        category.setCategoryId(dto.getCategoryId());
        category.setName(dto.getName());
        category.setWeight(dto.getWeight());
        category.setTotalPoints(dto.getTotalPoints());
        category.setCurrentPointsAchieved(dto.getCurrentPointsAchieved());
        category.setTotalWeight(dto.getTotalWeight());
        category.setEnableLateDrop(dto.getEnableLateDrop());
        category.setMetadata(dto.getMetadata());
        category.setDateCreated(dto.getDateCreated());
        category.setDateUpdated(dto.getDateUpdated());

        // Set grade book reference
        if (dto.getGradeBookId() != null) {
            GradeBook gradeBook = gradeBookRepository.findById(dto.getGradeBookId())
                    .orElseThrow(() -> new IllegalArgumentException("GradeBook not found with ID: " + dto.getGradeBookId()));
            category.setGradeBook(gradeBook);
        }

        return category;
    }

}

