package learning_management_system.backend.controller;

import learning_management_system.backend.dto.BadgeDto;
import learning_management_system.backend.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")

public class BadgeController {

    @Autowired
    private BadgeService badgeService;


    @PostMapping
    public ResponseEntity<BadgeDto> createBadge(@RequestBody BadgeDto badgeDto) {
        return ResponseEntity.ok(badgeService.createBadge(badgeDto));
    }

    @PutMapping("/{badgeId}")
    public ResponseEntity<BadgeDto> updateBadge(@PathVariable Long badgeId, @RequestBody BadgeDto badgeDto) {
        return ResponseEntity.ok(badgeService.updateBadge(badgeId, badgeDto));
    }

    @DeleteMapping("/{badgeId}")
    public ResponseEntity<Void> deleteBadge(@PathVariable Long badgeId) {
        badgeService.deleteBadge(badgeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BadgeDto>> getAllBadges() {
        return ResponseEntity.ok(badgeService.getAllBadges());
    }

    @GetMapping("/{badgeId}")
    public ResponseEntity<BadgeDto> getBadgeById(@PathVariable Long badgeId) {
        return ResponseEntity.ok(badgeService.getBadgeById(badgeId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<BadgeDto>> getBadgesByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(badgeService.getBadgesByTenantId(tenantId));
    }

    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<BadgeDto>> getBadgesByCreator(@PathVariable Long userId) {
        return ResponseEntity.ok(badgeService.getBadgesByCreator(userId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<BadgeDto>> getActiveBadges() {
        return ResponseEntity.ok(badgeService.getActiveBadges());
    }
}

