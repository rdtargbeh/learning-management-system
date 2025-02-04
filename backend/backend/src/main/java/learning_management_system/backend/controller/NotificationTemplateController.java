package learning_management_system.backend.controller;

import learning_management_system.backend.entity.NotificationTemplate;
import learning_management_system.backend.service.NotificationTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
public class NotificationTemplateController {

    @Autowired
    private NotificationTemplateService templateService;


    @PostMapping
    public ResponseEntity<NotificationTemplate> createTemplate(@RequestBody NotificationTemplate template) {
        return ResponseEntity.ok(templateService.createTemplate(template));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationTemplate> updateTemplate(@PathVariable Long id, @RequestBody NotificationTemplate template) {
        return ResponseEntity.ok(templateService.updateTemplate(id, template));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<NotificationTemplate> getTemplateByName(@PathVariable String name) {
        return ResponseEntity.ok(templateService.getTemplateByName(name));
    }
}
