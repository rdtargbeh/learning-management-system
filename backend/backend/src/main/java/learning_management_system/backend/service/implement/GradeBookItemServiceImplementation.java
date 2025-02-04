package learning_management_system.backend.service.implement;


import learning_management_system.backend.dto.GradeBookItemDto;
import learning_management_system.backend.entity.GradeBookCategory;
import learning_management_system.backend.entity.GradeBookItem;
import learning_management_system.backend.entity.Grading;
import learning_management_system.backend.entity.StudentGroup;
import learning_management_system.backend.mapper.GradeBookItemMapper;
import learning_management_system.backend.mapper.GradingMapper;
import learning_management_system.backend.repository.GradeBookCategoryRepository;
import learning_management_system.backend.repository.GradeBookItemRepository;
import learning_management_system.backend.repository.StudentGroupRepository;
import learning_management_system.backend.service.GradeBookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeBookItemServiceImplementation implements GradeBookItemService {

    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private GradeBookCategoryRepository gradeBookCategoryRepository;
    @Autowired
    private GradeBookItemMapper gradeBookItemMapper;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private GradingMapper gradingMapper;


    @Override
    public GradeBookItemDto createGradeBookItem(GradeBookItemDto itemDto) {
        GradeBookCategory category = gradeBookCategoryRepository.findById(itemDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        // Validate the updated data
        validateGradeBookItem(itemDto);

        // Map DTO to entity
        GradeBookItem gradeBookItem  = gradeBookItemMapper.toEntity(itemDto);
        gradeBookItem .setCategory(category);

        return gradeBookItemMapper.toDto(gradeBookItemRepository.save(gradeBookItem ));
    }

    @Override
    public GradeBookItemDto updateGradeBookItem(Long itemId, GradeBookItemDto itemDto) {
        // Fetch the existing GradeBookItem
        GradeBookItem existingItem = gradeBookItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("GradeBookItem not found with ID: " + itemId));

        // Validate the updated data
        validateGradeBookItem(itemDto);

        // Manually update fields of the existing entity from the DTO
        existingItem.setName(itemDto.getName());
        existingItem.setMaxPoints(itemDto.getMaxPoints());
        existingItem.setDueDate(itemDto.getDueDate());
        existingItem.setGradeVerificationRequired(itemDto.getGradeVerificationRequired());
        existingItem.setGroupGrading(itemDto.getGroupGrading());
        existingItem.setMetadata(itemDto.getMetadata());
        existingItem.setDateUpdated(LocalDateTime.now());

        // Update linked entities like Group and Grading
        if (itemDto.getGroupId() != null) {
            StudentGroup group = studentGroupRepository.findById(itemDto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found with ID: " + itemDto.getGroupId()));
            existingItem.setStudentGroup(group);
        } else {
            existingItem.setStudentGroup(null);
        }

        if (itemDto.getGrading() != null) {
            Grading grading = gradingMapper.toEntity(itemDto.getGrading());
            existingItem.setGrading(grading);
        }

        // Save the updated entity
        GradeBookItem updatedItem = gradeBookItemRepository.save(existingItem);

        // Convert the updated entity back to a DTO
        return gradeBookItemMapper.toDto(updatedItem);
    }


    @Override
    @Transactional(readOnly = true)
    public GradeBookItemDto getGradeBookItem(Long itemId) {
        GradeBookItem item = gradeBookItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("GradeBookItem not found."));
        return gradeBookItemMapper.toDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeBookItemDto> getItemsByCategoryId(Long categoryId) {
        return gradeBookItemRepository.findByCategoryId(categoryId)
                .stream()
                .map(gradeBookItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeBookItemDto> getItemsByDueDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return gradeBookItemRepository.findByDueDateRange(startDate, endDate)
                .stream()
                .map(gradeBookItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGradeBookItem(Long itemId) {
        if (!gradeBookItemRepository.existsById(itemId)) {
            throw new RuntimeException("GradeBookItem not found.");
        }
        gradeBookItemRepository.deleteById(itemId);
    }


    private void validateGradeBookItem(GradeBookItemDto itemDto) {
        if (itemDto.getMaxPoints() <= 0) {
            throw new IllegalArgumentException("Max points must be greater than zero.");
        }
        if (itemDto.getDueDate() != null && itemDto.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date must be in the future.");
        }
        if (gradeBookItemRepository.existsByNameAndCategory_CategoryId(itemDto.getName(), itemDto.getCategoryId())) {
            throw new IllegalArgumentException("An item with this name already exists in the category.");
        }
    }

    @Override
    @Transactional
    public List<GradeBookItemDto> batchCreateGradeBookItems(List<GradeBookItemDto> itemDtos) {
        List<GradeBookItem> items = itemDtos.stream()
                .map(gradeBookItemMapper::toEntity)
                .collect(Collectors.toList());

        List<GradeBookItem> savedItems = gradeBookItemRepository.saveAll(items);
        return savedItems.stream()
                .map(gradeBookItemMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<GradeBookItemDto> getOverdueItems(Long gradeBookId) {
        return gradeBookItemRepository.findOverdueItems(LocalDateTime.now(), gradeBookId)
                .stream()
                .map(gradeBookItemMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Enables or disables group grading for a grade book item.
     *
     * @param itemId the ID of the grade book item
     * @param isGroupGrading whether group grading is enabled
     * @param groupId the group ID to link, if applicable
     */
    @Override
    public void updateGroupGrading(Long itemId, Boolean isGroupGrading, Long groupId) {
        GradeBookItem item = gradeBookItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("GradeBookItem not found with ID: " + itemId));

        if (isGroupGrading) {
            if (groupId == null || !studentGroupRepository.existsById(groupId)) {
                throw new IllegalArgumentException("Invalid or missing group ID for group grading.");
            }
            // Fetch and set the StudentGroup
            StudentGroup group = studentGroupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("StudentGroup not found with ID: " + groupId));
            item.setStudentGroup(group);
        } else {
            // Clear the StudentGroup association
            item.setStudentGroup(null);
        }

        item.setGroupGrading(isGroupGrading);
        gradeBookItemRepository.save(item);
    }


    /**
     * Marks a grade book item as requiring verification.
     *
     * @param itemId the ID of the grade book item
     * @param verificationRequired whether verification is required
     */
    @Override
    public void setGradeVerificationRequired(Long itemId, Boolean verificationRequired) {
        GradeBookItem item = gradeBookItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("GradeBookItem not found with ID: " + itemId));

        item.setGradeVerificationRequired(verificationRequired);
        gradeBookItemRepository.save(item);
    }
}
