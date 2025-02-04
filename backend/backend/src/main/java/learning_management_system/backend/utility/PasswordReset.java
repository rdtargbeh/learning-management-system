package learning_management_system.backend.utility;

import learning_management_system.backend.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * Represents a password reset token for a user. This entity tracks the token
 * and its expiration date during the password reset process.
 */

@Data
public class PasswordReset {

    /** Unique identifier for the reset request. */
    private Long resetId;

    /** The user requesting the reset. */
    private User user;

    /** The secure reset token. */
    private String resetToken;

    /** The expiration timestamp of the reset token. */
    private Date resetTokenExpiry;
}
