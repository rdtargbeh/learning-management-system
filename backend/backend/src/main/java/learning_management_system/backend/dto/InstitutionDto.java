package learning_management_system.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Institution entity.
 */
public class InstitutionDto {

    private Long institutionId;
    private Long tenantId; // Maps to Tenant
    private String billingAddress;
    private String paymentMethod;
    private LocalDate lastPaymentDate;
    private LocalDate nextPaymentDue;
    private String supportContact;
    private String contactEmail;
    private String contactPhone;
    private String theme;
    private String logoUrl;
    private String faviconUrl;
    private String loginBackgroundImageUrl;
    private String timeZone;
    private String language;
    private String metadata;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // Constructor

    public InstitutionDto() {}

    public InstitutionDto(Long institutionId, Long tenantId, String billingAddress, String paymentMethod, LocalDate lastPaymentDate, LocalDate nextPaymentDue, String supportContact, String contactEmail, String contactPhone, String theme, String logoUrl, String faviconUrl, String loginBackgroundImageUrl, String timeZone, String language, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.institutionId = institutionId;
        this.tenantId = tenantId;
        this.billingAddress = billingAddress;
        this.paymentMethod = paymentMethod;
        this.lastPaymentDate = lastPaymentDate;
        this.nextPaymentDue = nextPaymentDue;
        this.supportContact = supportContact;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.theme = theme;
        this.logoUrl = logoUrl;
        this.faviconUrl = faviconUrl;
        this.loginBackgroundImageUrl = loginBackgroundImageUrl;
        this.timeZone = timeZone;
        this.language = language;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

// Getter and Setter

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public LocalDate getNextPaymentDue() {
        return nextPaymentDue;
    }

    public void setNextPaymentDue(LocalDate nextPaymentDue) {
        this.nextPaymentDue = nextPaymentDue;
    }

    public String getSupportContact() {
        return supportContact;
    }

    public void setSupportContact(String supportContact) {
        this.supportContact = supportContact;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public String getLoginBackgroundImageUrl() {
        return loginBackgroundImageUrl;
    }

    public void setLoginBackgroundImageUrl(String loginBackgroundImageUrl) {
        this.loginBackgroundImageUrl = loginBackgroundImageUrl;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
