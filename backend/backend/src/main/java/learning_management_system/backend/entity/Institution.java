package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Represents a business-specific institution in the LMS system.
 * The institution extends tenant-level configurations with business-related
 * details such as branding, contact information, and localization settings.
 */

@CrossOrigin("*")
@Entity
@Table(name = "institutions")
public class Institution {

    /** Unique identifier for the institution, mapped to tenant_id. */
    @Id
    @Column(name = "institution_id", nullable = false, updatable = false)
    private Long institutionId;

    /** Maps the institution to its tenant. */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "institution_id", nullable = false)
    private Tenant tenant;

    /** Billing address for the institution. */
    @Column(name = "billing_address", columnDefinition = "TEXT")
    private String billingAddress;

    /** Preferred payment method (e.g., Credit Card, Bank Transfer). */
    @Column(name = "payment_method", length = 100)
    private String paymentMethod;

    /** Date of the last payment. */
    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate;

    /** Date of the next payment due. */
    @Column(name = "next_payment_due")
    private LocalDate nextPaymentDue;

    /** Support contact information for the tenant. */
    @Column(name = "support_contact", length = 255)
    private String supportContact;

    /** General contact email for the institution. */
    @Column(name = "contact_email", nullable = false, length = 100)
    private String contactEmail;

    /** General contact phone number for the institution. */
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    /** Preferred theme for the institution (e.g., Light, Dark). */
    @Column(name = "theme", length = 50)
    private String theme;

    /** URL for the institution's logo. */
    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    /** URL for the institution's favicon. */
    @Column(name = "favicon_url", length = 255)
    private String faviconUrl;

    /** Background image URL for the login page. */
    @Column(name = "login_background_image_url", length = 255)
    private String loginBackgroundImageUrl;

    /** Default time zone for the institution. */
    @Column(name = "time_zone", nullable = false, length = 50)
    private String timeZone;

    /** Default language for the institution. */
    @Column(name = "language", nullable = false, length = 10)
    private String language = "en";

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the institution was created. */
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the institution was last updated. */
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated = LocalDateTime.now();


    public Institution() {
    }

    public Institution(Long institutionId, Tenant tenant, String billingAddress, String paymentMethod, LocalDate lastPaymentDate, LocalDate nextPaymentDue, String supportContact, String contactEmail, String contactPhone, String theme, String logoUrl, String faviconUrl, String loginBackgroundImageUrl, String timeZone, String language, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.institutionId = institutionId;
        this.tenant = tenant;
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


    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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


