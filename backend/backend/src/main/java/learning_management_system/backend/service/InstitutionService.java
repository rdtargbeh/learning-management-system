package learning_management_system.backend.service;

import learning_management_system.backend.dto.InstitutionDto;
import learning_management_system.backend.entity.Institution;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface for Institution operations.
 */
public interface InstitutionService {

    InstitutionDto createInstitution(InstitutionDto institutionDto);

    InstitutionDto updateInstitution(Long institutionId, InstitutionDto institutionDto);

    InstitutionDto getInstitutionById(Long institutionId);

    List<InstitutionDto> getAllInstitutions();

    void deleteInstitution(Long institutionId);

    List<InstitutionDto> getInstitutionsByTheme(String theme);

    List<InstitutionDto> getInstitutionsByBillingAddress(String billingAddress);

    List<InstitutionDto> getActiveInstitutionsByRegionAndMinUsers(String region, Integer minUsers);

    Page<Institution> getInstitutionsByRegion(String region, int page, int size);

//    List<InstitutionDto> getInstitutionsByRegion(String region);

}
