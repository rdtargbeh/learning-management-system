package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.StudentDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.enums.ProfileVisibility;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentDto toDto(Student student) {

        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(student.getUser().getUserId());
//        studentDto.setUserId(student.getUser().getUserId());
        studentDto.setGradeLevel(student.getGradeLevel());
        studentDto.setMajor(student.getMajor());
        studentDto.setDateEnrolled(student.getDateEnrolled());
        studentDto.setEnrollmentStatus(student.getEnrollmentStatus().name());
        studentDto.setBio(student.getBio());
        studentDto.setOverallProgress(student.getOverallProgress());
        studentDto.setLeaderboardPoints(student.getLeaderboardPoints());

        // Populate enrolled courses
        studentDto.setEnrolledCourseIds(student.getEnrolledCourses().stream()
                .map(course -> course.getCourseId())
                .collect(Collectors.toSet()));
//        studentDto.setEnrolledCourseIds(student.getEnrolledCourses().stream()
//                .map(Course::getCourseId)
//                .collect(Collectors.toSet()));

        // Populate Student Groups
        studentDto.setStudentGroupIds(student.getStudentGroups().stream()
                .map(group -> group.getGroupId())
                .collect(Collectors.toSet()));

        // Populate Student Badges
        studentDto.setBadgeIds(student.getBadges().stream()
                .map(badge -> badge.getBadgeId())
                .collect(Collectors.toSet()));

        // Populate Student Advisors
        studentDto.setAdvisorIds(student.getAdvisors().stream()
                .map(advisor -> advisor.getStaffId())
                .collect(Collectors.toSet()));

        studentDto.setAttendance(student.getAttendance());
        studentDto.setGraduationDate(student.getGraduationDate());
        studentDto.setProfileVisibility(student.getProfileVisibility().name());
        studentDto.setCustomAttributes(student.getCustomAttributes());
        studentDto.setMetadata(student.getMetadata());
        studentDto.setTenantId(student.getTenant().getTenantId());

        // Populate user-related fields
        if (student.getUser() != null) {
            studentDto.setUserName(student.getUser().getUserName());
            studentDto.setEmail(student.getUser().getEmail());
            studentDto.setFirstName(student.getUser().getFirstName());
            studentDto.setLastName(student.getUser().getLastName());
        }

        return studentDto;
    }

    public static Student toEntity(StudentDto dto, User user, Set<Course> courses) {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setGradeLevel(dto.getGradeLevel());
        student.setMajor(dto.getMajor());
        student.setDateEnrolled(dto.getDateEnrolled());
        student.setBio(dto.getBio());
        student.setOverallProgress(dto.getOverallProgress());
        student.setLeaderboardPoints(dto.getLeaderboardPoints());
        student.setAttendance(dto.getAttendance());
        student.setGraduationDate(dto.getGraduationDate());
        student.setCustomAttributes(dto.getCustomAttributes());
        student.setMetadata(dto.getMetadata());
        student.setProfileVisibility(ProfileVisibility.valueOf(student.getProfileVisibility().name()));
        student.setEnrollmentStatus(EnrollmentStatus.valueOf(dto.getEnrollmentStatus()));

        student.setUser(user);
        student.setEnrolledCourses(courses);

        return student;
    }
}