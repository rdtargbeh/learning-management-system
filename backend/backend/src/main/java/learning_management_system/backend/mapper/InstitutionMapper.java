package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.InstitutionDto;
import learning_management_system.backend.entity.Institution;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting Institution entity to DTO and vice versa.
 */
@Component
public class InstitutionMapper {

    public static InstitutionDto toDto(Institution institution) {
        InstitutionDto dto = new InstitutionDto();
        dto.setInstitutionId(institution.getInstitutionId());
        dto.setTenantId(institution.getTenant().getTenantId());
        dto.setBillingAddress(institution.getBillingAddress());
        dto.setPaymentMethod(institution.getPaymentMethod());
        dto.setLastPaymentDate(institution.getLastPaymentDate());
        dto.setNextPaymentDue(institution.getNextPaymentDue());
        dto.setSupportContact(institution.getSupportContact());
        dto.setContactEmail(institution.getContactEmail());
        dto.setContactPhone(institution.getContactPhone());
        dto.setTheme(institution.getTheme());
        dto.setLogoUrl(institution.getLogoUrl());
        dto.setFaviconUrl(institution.getFaviconUrl());
        dto.setLoginBackgroundImageUrl(institution.getLoginBackgroundImageUrl());
        dto.setTimeZone(institution.getTimeZone());
        dto.setLanguage(institution.getLanguage());
        dto.setMetadata(institution.getMetadata());
        dto.setDateCreated(institution.getDateCreated());
        dto.setDateUpdated(institution.getDateUpdated());
        return dto;
    }

    public static Institution toEntity(InstitutionDto dto) {
        Institution institution = new Institution();
        institution.setBillingAddress(dto.getBillingAddress());
        institution.setPaymentMethod(dto.getPaymentMethod());
        institution.setLastPaymentDate(dto.getLastPaymentDate());
        institution.setNextPaymentDue(dto.getNextPaymentDue());
        institution.setSupportContact(dto.getSupportContact());
        institution.setContactEmail(dto.getContactEmail());
        institution.setContactPhone(dto.getContactPhone());
        institution.setTheme(dto.getTheme());
        institution.setLogoUrl(dto.getLogoUrl());
        institution.setFaviconUrl(dto.getFaviconUrl());
        institution.setLoginBackgroundImageUrl(dto.getLoginBackgroundImageUrl());
        institution.setTimeZone(dto.getTimeZone());
        institution.setLanguage(dto.getLanguage());
        institution.setMetadata(dto.getMetadata());
        return institution;
    }
}

