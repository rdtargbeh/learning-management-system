package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.InstitutionDto;
import learning_management_system.backend.entity.Institution;
import learning_management_system.backend.mapper.InstitutionMapper;
import learning_management_system.backend.repository.InstitutionRepository;
import learning_management_system.backend.service.InstitutionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionServiceImplementation implements InstitutionService {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public InstitutionServiceImplementation(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }


    @Override
    public InstitutionDto createInstitution(InstitutionDto institutionDto) {
        Institution institution = InstitutionMapper.toEntity(institutionDto);
        return InstitutionMapper.toDto(institutionRepository.save(institution));
    }

    @Override
    public InstitutionDto updateInstitution(Long institutionId, InstitutionDto institutionDto) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found"));
        institution.setBillingAddress(institutionDto.getBillingAddress());
        institution.setPaymentMethod(institutionDto.getPaymentMethod());
        institution.setLastPaymentDate(institutionDto.getLastPaymentDate());
        institution.setNextPaymentDue(institutionDto.getNextPaymentDue());
        institution.setSupportContact(institutionDto.getSupportContact());
        institution.setContactEmail(institutionDto.getContactEmail());
        institution.setContactPhone(institutionDto.getContactPhone());
        institution.setTheme(institutionDto.getTheme());
        institution.setLogoUrl(institutionDto.getLogoUrl());
        institution.setFaviconUrl(institutionDto.getFaviconUrl());
        institution.setLoginBackgroundImageUrl(institutionDto.getLoginBackgroundImageUrl());
        institution.setTimeZone(institutionDto.getTimeZone());
        institution.setLanguage(institutionDto.getLanguage());
        institution.setMetadata(institutionDto.getMetadata());

        return InstitutionMapper.toDto(institutionRepository.save(institution));
    }

    @Override
    public InstitutionDto getInstitutionById(Long institutionId) {
        return InstitutionMapper.toDto(institutionRepository.findById(institutionId)
                .orElseThrow(() -> new RuntimeException("Institution not found")));
    }

    @Override
    public List<InstitutionDto> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(InstitutionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInstitution(Long institutionId) {
        institutionRepository.deleteById(institutionId);
    }

    // Get Tenant By Theme
    @Override
    public List<InstitutionDto> getInstitutionsByTheme(String theme) {
        List<Institution> institutions = institutionRepository.findByTheme(theme);

        return institutions.stream()
                .map(InstitutionMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get Tenants By Billing Address
    @Override
    public List<InstitutionDto> getInstitutionsByBillingAddress(String billingAddress) {
        return institutionRepository.findByBillingAddress(billingAddress)
                .stream()
                .map(InstitutionMapper ::toDto)
                .collect(Collectors.toList());
    }

    // Get Active Tenants By Region And Min Users
    @Override
    public List<InstitutionDto> getActiveInstitutionsByRegionAndMinUsers(String region, Integer minUsers) {
        return institutionRepository.findActiveInstitutionsByRegionAndMinUsers(region, minUsers)
                .stream()
                .map(InstitutionMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get Tenants By Region
    @Override
    public Page<Institution> getInstitutionsByRegion(String region, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return institutionRepository.findByRegion(region, pageable);
    }


//    @Override
//    public List<InstitutionDto> getInstitutionsByRegion(String region) {
//        return institutionRepository.findByRegion(region)
//                .stream()
//                .map(InstitutionMapper::toDto)
//                .collect(Collectors.toList());
//    }


}
