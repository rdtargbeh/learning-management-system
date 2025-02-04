package learning_management_system.backend.utility;

import java.io.Serializable;


/** Data Transfer Object (DTO) for summarizing tenant details.*/
public class TenantSummaryDto implements Serializable {

    /** ID of the tenant */
    private Long tenantId;

    /** Name of the tenant */
    private String tenantName;

    /** Number of courses associated with the tenant */
    private Long totalCourses;

    /** Number of users associated with the tenant */
    private Long totalUsers;



    /**
     * Constructor for `TenantSummaryDto`
     *
     * @param tenantId    the ID of the tenant
     * @param tenantName  the name of the tenant
     * @param totalCourses the number of courses associated with the tenant
     * @param totalUsers   the number of users associated with the tenant
     */
    public TenantSummaryDto(Long tenantId, String tenantName, Long totalCourses, Long totalUsers) {
        this.tenantId = tenantId;
        this.tenantName = tenantName;
        this.totalCourses = totalCourses;
        this.totalUsers = totalUsers;
    }

    // Default constructor for frameworks or libraries that require it
    public TenantSummaryDto() {
    }

    /** Getters and Setters */

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    /** Override toString for logging or debugging purposes */
    @Override
    public String toString() {
        return "TenantSummaryDto{" +
                "tenantId=" + tenantId +
                ", tenantName='" + tenantName + '\'' +
                ", courseCount=" + totalCourses +
                ", userCount=" + totalUsers +
                '}';
    }
}