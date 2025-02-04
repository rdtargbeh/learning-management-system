package learning_management_system.backend.controller;

import learning_management_system.backend.dto.InstitutionDto;
import learning_management_system.backend.entity.Institution;
import learning_management_system.backend.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;


    @PostMapping
    public ResponseEntity<InstitutionDto> createInstitution(@RequestBody InstitutionDto institutionDto) {
        return ResponseEntity.ok(institutionService.createInstitution(institutionDto));
    }

    @PutMapping("/{institutionId}")
    public ResponseEntity<InstitutionDto> updateInstitution(@PathVariable Long institutionId, @RequestBody InstitutionDto institutionDto) {
        return ResponseEntity.ok(institutionService.updateInstitution(institutionId, institutionDto));
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<InstitutionDto> getInstitutionById(@PathVariable Long institutionId) {
        return ResponseEntity.ok(institutionService.getInstitutionById(institutionId));
    }

    @GetMapping
    public ResponseEntity<List<InstitutionDto>> getAllInstitutions() {
        return ResponseEntity.ok(institutionService.getAllInstitutions());
    }

    @DeleteMapping("/{institutionId}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long institutionId) {
        institutionService.deleteInstitution(institutionId);
        return ResponseEntity.noContent().build();
    }

    // Build A Get Tenants By Theme REST API
    @GetMapping("/theme")
    public ResponseEntity<List<InstitutionDto>> getInstitutionsByTheme(@RequestParam String theme) {
        return ResponseEntity.ok(institutionService.getInstitutionsByTheme(theme));
    }


    @GetMapping("/billing-address")
    public ResponseEntity<List<InstitutionDto>> getInstitutionsByBillingAddress(@RequestParam String billingAddress) {
        return ResponseEntity.ok(institutionService.getInstitutionsByBillingAddress(billingAddress));
    }

    @GetMapping("/active-region")
    public ResponseEntity<List<InstitutionDto>> getActiveInstitutionsByRegionAndMinUsers(
            @RequestParam String region, @RequestParam Integer minUsers) {
        return ResponseEntity.ok(institutionService.getActiveInstitutionsByRegionAndMinUsers(region, minUsers));
    }

    // Build A Get Tenants By Region REST API
    @GetMapping("/region/{region}")
    public Page<Institution> getInstitutionsByRegion(
            @PathVariable String region,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return institutionService.getInstitutionsByRegion(region, page, size);
    }

//    @GetMapping("/region/{region}")
//    public ResponseEntity<List<InstitutionDto>> getInstitutionsByRegion(@PathVariable String region) {
//        return ResponseEntity.ok(institutionService.getInstitutionsByRegion(region));
//    }


}
