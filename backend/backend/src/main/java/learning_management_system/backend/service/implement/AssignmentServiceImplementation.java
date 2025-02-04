package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AssignmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.mapper.AssignmentMapper;
import learning_management_system.backend.mapper.AttachmentMapper;
import learning_management_system.backend.mapper.GradingMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.AssignmentService;
import learning_management_system.backend.service.GradingService;
import learning_management_system.backend.utility.GradingAnalyticsDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingSubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImplementation implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private GradingRepository gradingRepository;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private GradingMapper gradingMapper;
    @Autowired
    private GradingService gradingService;
    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;


    @Override
    @Transactional
    public AssignmentDto createAssignment(AssignmentDto assignmentDto) {
        // Fetch and validate the course
        Course course = courseRepository.findById(assignmentDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + assignmentDto.getCourseId()));

        // Fetch and validate the creator
        User creator = userRepository.findById(assignmentDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + assignmentDto.getCreatedBy()));

        // Fetch and validate the student group if provided
        StudentGroup studentGroup = null;
        if (assignmentDto.getStudentGroupId() != null) {
            studentGroup = studentGroupRepository.findById(assignmentDto.getStudentGroupId())
                    .orElseThrow(() -> new RuntimeException("Student group not found with ID: " + assignmentDto.getStudentGroupId()));

            if (!studentGroup.getCourse().equals(course)) {
                throw new RuntimeException("Student group does not belong to the specified course.");
            }
        }

        // Fetch and validate assigned students if provided
        Set<User> assignedStudents = new HashSet<>();
        if (assignmentDto.getAssignedStudentIds() != null) {
            assignedStudents = new HashSet<>(userRepository.findAllById(assignmentDto.getAssignedStudentIds()));

            // Use course.getStudents() to retrieve the list of enrolled students
            if (assignedStudents.stream().anyMatch(student -> !course.getStudents().contains(student))) {
                throw new RuntimeException("One or more assigned students are not enrolled in the specified course.");
            }
        }

        // Validate attachments if provided
        List<Attachment> attachments = new ArrayList<>();
        if (assignmentDto.getAttachmentIds() != null) {
            attachments = attachmentRepository.findAllById(assignmentDto.getAttachmentIds());
            if (attachments.size() != assignmentDto.getAttachmentIds().size()) {
                throw new RuntimeException("One or more attachments not found.");
            }
        }

        // Map the DTO to an entity
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        assignment.setCourse(course);
        assignment.setCreatedBy(creator);
        assignment.setStudentGroup(studentGroup);
        assignment.setAssignedStudents(assignedStudents);
        assignment.setAttachments(attachments);

        // Ensure date is greater than current  date
        if (assignmentDto.getDueDate() != null) {
            if (assignmentDto.getDueDate().isBefore(assignment.getAvailableFrom())) {
                throw new RuntimeException("Due date cannot be before the availability start date.");
            }
            assignment.setDueDate(assignmentDto.getDueDate());
        }

        // Save the assignment and return the mapped DTO
        return assignmentMapper.toDto(assignmentRepository.save(assignment));
    }


    /**  Update Assignment Method*/
    @Override
    @Transactional
    public AssignmentDto updateAssignment(Long assignmentId, AssignmentDto assignmentDto) {
        // Fetch the existing assignment
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        // Update core fields if provided
        if (assignmentDto.getTitle() != null) {
            existingAssignment.setTitle(assignmentDto.getTitle());
        }
        if (assignmentDto.getDescription() != null) {
            existingAssignment.setDescription(assignmentDto.getDescription());
        }
        if (assignmentDto.getDueDate() != null) {
            if (assignmentDto.getDueDate().isBefore(existingAssignment.getAvailableFrom())) {
                throw new RuntimeException("Due date cannot be before the availability start date.");
            }
            existingAssignment.setDueDate(assignmentDto.getDueDate());
        }


        if (assignmentDto.getAvailableFrom() != null) {
            existingAssignment.setAvailableFrom(assignmentDto.getAvailableFrom());
        }
        if (assignmentDto.getAvailableUntil() != null) {
            existingAssignment.setAvailableUntil(assignmentDto.getAvailableUntil());
        }

        // Update course if provided
        if (assignmentDto.getCourseId() != null) {
            Course course = courseRepository.findById(assignmentDto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + assignmentDto.getCourseId()));
            existingAssignment.setCourse(course);
        }

        // Update student group if provided
        if (assignmentDto.getStudentGroupId() != null) {
            StudentGroup studentGroup = studentGroupRepository.findById(assignmentDto.getStudentGroupId())
                    .orElseThrow(() -> new RuntimeException("Student group not found with ID: " + assignmentDto.getStudentGroupId()));
            existingAssignment.setStudentGroup(studentGroup);
        }

        // Update assigned students if provided
        if (assignmentDto.getAssignedStudentIds() != null) {
            Set<User> assignedStudents = new HashSet<>(userRepository.findAllById(assignmentDto.getAssignedStudentIds()));
            if (assignedStudents.stream().anyMatch(student -> !existingAssignment.getCourse().getStudents().contains(student))) {
                throw new RuntimeException("One or more assigned students are not enrolled in the specified course.");
            }
            existingAssignment.setAssignedStudents(assignedStudents);
        }

        // Update attachments if provided
        if (assignmentDto.getAttachmentIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(assignmentDto.getAttachmentIds());
            if (attachments.size() != assignmentDto.getAttachmentIds().size()) {
                throw new RuntimeException("One or more attachments not found.");
            }
            existingAssignment.setAttachments(attachments);
        }

        // Update grading configuration if provided
        if (assignmentDto.getGradingId() != null) {
            Grading grading = gradingRepository.findById(assignmentDto.getGradingId())
                    .orElseThrow(() -> new RuntimeException("Grading configuration not found with ID: " + assignmentDto.getGradingId()));
            existingAssignment.setGrading(grading);
        }

        // Save and return the updated assignment
        return assignmentMapper.toDto(assignmentRepository.save(existingAssignment));
    }

    @Override
    public List<AssignmentDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getAssignmentsByCourseId(Long courseId) {
        return assignmentRepository.findByCourse_CourseId(courseId).stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<AssignmentDto> getAssignmentsByStudentId(Long studentId) {
        return assignmentRepository.findAssignmentsByUserId(studentId).stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDto getAssignmentById(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + assignmentId));
        return assignmentMapper.toDto(assignment);
    }


    @Override
    public void deleteAssignment(Long assignmentId) {
        if (!assignmentRepository.existsById(assignmentId)) {
            throw new IllegalArgumentException("Assignment not found with ID: " + assignmentId);
        }
        assignmentRepository.deleteById(assignmentId);
    }


    // Attach file to assignment
    @Override
    public AttachmentDto addAttachmentToAssignment(Long assignmentId, AttachmentDto attachmentDto) {
        // Find the Assignment
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        // Map DTO to Entity
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);

        // Associate Assignment and Attachment
        attachment.setLinkedEntityType("ASSIGNMENT");
        attachment.setLinkedEntityId(assignmentId);

        // Set the uploadedBy User
        User currentUser = getCurrentUser();
        attachment.setUploadedBy(currentUser);

        // Save Attachment
        attachmentRepository.save(attachment);

        return attachmentMapper.toDto(attachment);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Current user not found!"));
    }


    @Override
    @Transactional(readOnly = true)
    public GradingDto getGradingConfiguration(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        if (assignment.getGrading() == null) {
            throw new RuntimeException("No grading configuration found for this assignment.");
        }

        return gradingMapper.toDto(assignment.getGrading());
    }

    @Override
    @Transactional
    public GradingDto configureGrading(Long assignmentId, GradingDto gradingDto) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        Grading grading = gradingMapper.toEntity(gradingDto);
        grading.setAssignment(assignment); // Link grading to assignment

        grading = gradingRepository.save(grading);
        assignment.setGrading(grading); // Update assignment entity

        assignmentRepository.save(assignment); // Persist the relationship
        return gradingMapper.toDto(grading);
    }

    @Transactional
    public GradingResultDto gradeAssignmentSubmission(Long assignmentId, GradingSubmissionDto submissionDto) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        Grading grading = assignment.getGrading();
        if (grading == null) {
            throw new RuntimeException("No grading configuration found for this assignment.");
        }

        // Delegate to GradingService
        Double score = gradingService.calculateRubricScore(grading.getGradingRubric(), submissionDto);
        return gradingService.saveGradingResult(grading, submissionDto.getStudentId(), score);
    }

    @Override
    @Transactional(readOnly = true)
    public GradingAnalyticsDto getAssignmentAnalytics(Long assignmentId) {
        List<GradeBookRecord> records = gradeBookRecordRepository.findByGradeBookItem_AssignmentId(assignmentId);

        Double averageScore = records.stream()
                .mapToDouble(record -> record.getScore() != null ? record.getScore() : 0.0)
                .average()
                .orElse(0.0);

        Long completedCount = records.stream()
                .filter(record -> record.getScore() != null)
                .count();

        return new GradingAnalyticsDto(averageScore, completedCount);
    }

    @Override
    @Transactional
    public GradingResultDto gradeAssignment(Long assignmentId, GradingSubmissionDto submissionDto) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        Grading grading = assignment.getGrading();
        if (grading == null) {
            throw new RuntimeException("No grading configuration found for this assignment.");
        }

        return gradingService.gradeByGradingId(grading.getGradingId(), submissionDto);
    }



}

