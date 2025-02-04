package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

/**
 * Represents a tenant-specific configuration setting in the LMS.
 */
@CrossOrigin("*")
@Entity
@Table(name = "tenant_settings")
public class TenantSettings {

    /** Unique identifier for the tenant-specific setting. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "setting_id", nullable = false, updatable = false)
    private Long tenantSettingId;

    /** The tenant this setting belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /** Key of the setting (e.g., "max_upload_size"). */
    @Column(name = "key", nullable = false, length = 100)
    private String key;

    /** Value of the setting (e.g., "50MB"). */
    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;

    /** Description of the setting. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Group for categorizing the setting (e.g., "Performance"). */
    @Column(name = "group", length = 50)
    private String group;

    /** Metadata for extensibility (e.g., conditions, notes). */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Indicates if the setting is currently active. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Timestamp for the last update to the setting. */
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();


    public TenantSettings() {
    }

    public TenantSettings(Long tenantSettingId, Tenant tenant, String key, String value, String description, String group, String metadata, Boolean isActive, LocalDateTime lastUpdated) {
        this.tenantSettingId = tenantSettingId;
        this.tenant = tenant;
        this.key = key;
        this.value = value;
        this.description = description;
        this.group = group;
        this.metadata = metadata;
        this.isActive = isActive;
        this.lastUpdated = lastUpdated;
    }

    public Long getTenantSettingId() {
        return tenantSettingId;
    }

    public void setTenantSettingId(Long tenantSettingId) {
        this.tenantSettingId = tenantSettingId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

