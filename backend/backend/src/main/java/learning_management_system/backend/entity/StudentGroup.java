package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a group of students within a course, enabling collaboration and teamwork.
 */


@CrossOrigin("*")
@Entity
@Table(name = "student_groups")
public class StudentGroup {

    /** Unique identifier for the group. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    /** Name of the group. */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Description of the group’s purpose or focus. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Many-to-One relationship with the Course entity. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** Many-to-Many relationship with the Student entity for group members. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> members = new HashSet<>();

    /** Many-to-Many relationship with the Student entity for group leaders. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_leaders",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> leaders = new HashSet<>();

    /** One-to-Many relationship with the Discussion entity for group discussions. */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discussion> discussions = new ArrayList<>();

    /** One-to-Many relationship with the Notification entity for group notifications. */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LmsNotification> notifications = new ArrayList<>();

    /** Timestamp for when the group was created. */
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    /** Timestamp for when the group was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /** Indicates whether the group is active or archived. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Metadata for extensibility, such as custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;


    public StudentGroup() {
    }

    public StudentGroup(Long groupId, String name, String description, Course course, Set<Student> members, Set<Student> leaders, List<Discussion> discussions, List<LmsNotification> notifications, LocalDateTime creationDate, LocalDateTime dateUpdated, Boolean isActive, String metadata) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.course = course;
        this.members = members;
        this.leaders = leaders;
        this.discussions = discussions;
        this.notifications = notifications;
        this.creationDate = creationDate;
        this.dateUpdated = dateUpdated;
        this.isActive = isActive;
        this.metadata = metadata;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Student> getMembers() {
        return members;
    }

    public void setMembers(Set<Student> members) {
        this.members = members;
    }

    public Set<Student> getLeaders() {
        return leaders;
    }

    public void setLeaders(Set<Student> leaders) {
        this.leaders = leaders;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public List<LmsNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<LmsNotification> notifications) {
        this.notifications = notifications;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
