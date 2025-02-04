package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.TenantDto;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.TenantStatus;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public static TenantDto toDto(Tenant tenant) {
        if (tenant == null) {
            return null;
        }
        TenantDto dto = new TenantDto();
        dto.setTenantId(tenant.getTenantId());
        dto.setDomain(tenant.getDomain());
        dto.setTenantName(tenant.getTenantName());
        dto.setSchemaName(tenant.getSchemaName());
        dto.setSubscriptionPlan(tenant.getSubscriptionPlan());
        dto.setSubscriptionActive(tenant.getSubscriptionActive());
        dto.setSubscriptionStart(tenant.getSubscriptionStart());
        dto.setSubscriptionEnd(tenant.getSubscriptionEnd());
        dto.setMaxUsers(tenant.getMaxUsers());
        dto.setMaxCourses(tenant.getMaxCourses());
        dto.setStorageLimit(tenant.getStorageLimit());
        dto.setRegion(tenant.getRegion());
        dto.setStatus(String.valueOf(tenant.getStatus()));
//        dto.setStatus(tenant.getStatus() != null ? tenant.getStatus().name() : null); // Convert enum to string
        dto.setDateCreated(tenant.getDateCreated());
        dto.setDateUpdated(tenant.getDateUpdated());

        dto.setCreatedById(tenant.getCreatedBy() != null ? tenant.getCreatedBy().getUserId() : null);
        dto.setUpdatedById(tenant.getUpdatedBy() != null ? tenant.getUpdatedBy().getUserId() : null);

        // Derived or additional fields
        dto.setTotalCourses((long) tenant.getCourses().size());
        dto.setTotalUsers((long) tenant.getUsers().size());
        dto.setAdminEmail(tenant.getCreatedBy() != null ? tenant.getCreatedBy().getEmail() : null);

        return dto;
    }


    public static Tenant toEntity(TenantDto dto, User createdBy) {
        if (dto == null) {
            return null;
        }
        Tenant tenant = new Tenant();
        tenant.setTenantName(dto.getTenantName());
        tenant.setDomain(dto.getDomain());
        tenant.setSchemaName(dto.getSchemaName());
        tenant.setSubscriptionPlan(dto.getSubscriptionPlan());
        tenant.setSubscriptionActive(dto.getSubscriptionActive());
        tenant.setSubscriptionStart(dto.getSubscriptionStart());
        tenant.setSubscriptionEnd(dto.getSubscriptionEnd());
        tenant.setMaxUsers(dto.getMaxUsers());
        tenant.setMaxCourses(dto.getMaxCourses());
        tenant.setStorageLimit(dto.getStorageLimit());
        tenant.setRegion(dto.getRegion());
        tenant.setCreatedBy(createdBy);
        tenant.setUpdatedBy(createdBy);
//        tenant.setStatus(TenantStatus.valueOf(dto.getStatus()));
        tenant.setStatus(dto.getStatus() != null ? TenantStatus.valueOf(dto.getStatus().toUpperCase()) : null);

        return tenant;
    }

}

