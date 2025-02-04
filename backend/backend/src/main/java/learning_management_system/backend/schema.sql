
CREATE TABLE activity_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for the activity log
    user_id BIGINT NOT NULL,                 -- Foreign key referencing the user
    action_category VARCHAR(50) NOT NULL,    -- Enum for action category (e.g., LOGIN, COURSE)
    action_type VARCHAR(50) NOT NULL,        -- Enum for action type (e.g., LOGGED_IN, COURSE_CREATED)
    ip_address VARCHAR(45),                  -- IP address (IPv4/IPv6 compatible)
    status BOOLEAN NOT NULL,                 -- Indicates success or failure of the action
    resource_type VARCHAR(100),              -- Type of affected resource (e.g., COURSE, USER)
    resource_id BIGINT,                      -- ID of the affected resource
    metadata TEXT,                           -- Additional metadata or details
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Action timestamp

    -- Foreign key constraint
    CONSTRAINT fk_activity_log_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,

    -- Indexes
    INDEX idx_action_category (action_category),
    INDEX idx_action_type (action_type),
    INDEX idx_user_id (user_id),
    INDEX idx_resource_type (resource_type),
    INDEX idx_resource_id (resource_id),
    INDEX idx_timestamp (timestamp)
);


CREATE TABLE admins (
    admin_id BIGINT PRIMARY KEY, -- Shared with the Users table
    admin_level VARCHAR(20) NOT NULL, -- Enum for admin levels (e.g., SUPER_ADMIN, TENANT_ADMIN)
    tenant_id BIGINT, -- Foreign key to the Tenants table
    department_id BIGINT, -- Foreign key to the Departments table
    title VARCHAR(100), -- Admin's job title
    hire_date DATE, -- Date the admin joined
    office_location VARCHAR(255), -- Physical or virtual office location
    accessibility_preferences TEXT, -- Preferences for accessibility
    work_schedule TEXT, -- Work schedule or availability
    preferences TEXT, -- Metadata for customizations
    is_super_admin BOOLEAN NOT NULL DEFAULT FALSE, -- Indicates system-wide privileges
    last_login DATETIME, -- Timestamp of the last login
    last_login_ip VARCHAR(45), -- IP address of the last login

    -- Foreign key constraints
    CONSTRAINT fk_admins_users FOREIGN KEY (admin_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_admins_tenants FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id) ON DELETE SET NULL,
    CONSTRAINT fk_admins_departments FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE SET NULL,

    -- Indexes
    INDEX idx_admin_level (admin_level),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_department_id (department_id),
    INDEX idx_last_login (last_login)
);


-- Announcements Table
CREATE TABLE announcements (
    announcement_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    rich_text_content TEXT,
    category VARCHAR(50),
    target_roles TEXT,
    target_entity_type VARCHAR(50),
    target_entity_id BIGINT,
    view_count INT DEFAULT 0,
    reply_count INT DEFAULT 0,
    visibility VARCHAR(20) NOT NULL, -- Enum for visibility levels
    priority VARCHAR(20), -- Enum for priority levels
    action_link VARCHAR(255),
    is_pinned BOOLEAN NOT NULL DEFAULT FALSE,
    is_draft BOOLEAN NOT NULL DEFAULT FALSE,
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    recurrence_pattern VARCHAR(50),
    tags TEXT,
    scheduled_at DATETIME,
    created_by BIGINT NOT NULL, -- Foreign key to users
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_engaged_at DATETIME,
    content_language VARCHAR(50),

    -- Foreign Key Constraints
    CONSTRAINT fk_announcements_created_by FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE,

    -- Indexes for faster lookups
    INDEX idx_announcements_category (category),
    INDEX idx_announcements_target_entity (target_entity_type, target_entity_id),
    INDEX idx_announcements_visibility (visibility),
    INDEX idx_announcements_priority (priority),
    INDEX idx_announcements_date_created (date_created),
    INDEX idx_announcements_last_engaged (last_engaged_at)
);

-- Announcement Views Table
CREATE TABLE announcement_views (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    announcement_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    viewed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key Constraints
    CONSTRAINT fk_announcement_views_announcement FOREIGN KEY (announcement_id) REFERENCES announcements(announcement_id) ON DELETE CASCADE,
    CONSTRAINT fk_announcement_views_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,

    -- Indexes
    INDEX idx_announcement_views_announcement (announcement_id),
    INDEX idx_announcement_views_user (user_id),
    INDEX idx_announcement_views_viewed_at (viewed_at)
);

-- Attachments Table
CREATE TABLE attachments (
    attachment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_url VARCHAR(255) NOT NULL,
    linked_entity_id BIGINT, -- General linked entity field
    CONSTRAINT fk_attachments_announcements FOREIGN KEY (linked_entity_id) REFERENCES announcements(announcement_id) ON DELETE CASCADE
);

-- Comments Table
CREATE TABLE comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    announcement_id BIGINT, -- Foreign key to announcements
    created_by BIGINT NOT NULL, -- Foreign key to users
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign Key Constraints
    CONSTRAINT fk_comments_announcement FOREIGN KEY (announcement_id) REFERENCES announcements(announcement_id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_created_by FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE CASCADE
);





