package learning_management_system.backend.enums;

public enum RoleType {
    GLOBAL,  // Global roles like Super Admin
    TENANT,  // Tenant-specific roles
    SYSTEM   // System-defined roles that tenants can use but not modify
}

