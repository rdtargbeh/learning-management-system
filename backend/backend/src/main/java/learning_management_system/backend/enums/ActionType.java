package learning_management_system.backend.enums;

/**
 * Represents the specific type of an action performed by a user.
 */
public enum ActionType {
    // LOGIN Actions
    LOGGED_IN,
    LOGGED_OUT,
    LOGIN_FAILED,

    // COURSE Actions
    COURSE_CREATED,
    COURSE_UPDATED,
    COURSE_DELETED,

    // USER Actions
    USER_REGISTERED,
    USER_UPDATED,
    USER_DEACTIVATED,

    // REPORT Actions
    REPORT_VIEWED,
    REPORT_GENERATED,

    // CRUD
    CREATE,
    DELETE,
    UPDATE,
    VALIDATION_SUCCESS,
    VALIDATION_FAILURE,

    SUBMITTED,   // New
    GRADED,      // New
    FLAGGED,     // New
    REVIEWED,

    // Other Actions
    OTHER
}
