package learning_management_system.backend.service;

import learning_management_system.backend.entity.NotificationTemplate;
import learning_management_system.backend.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateService {

    @Autowired
    private NotificationTemplateRepository templateRepository;


    public NotificationTemplate createTemplate(NotificationTemplate template) {
        return templateRepository.save(template);
    }

    public NotificationTemplate updateTemplate(Long id, NotificationTemplate updatedTemplate) {
        NotificationTemplate existingTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + id));
        existingTemplate.setName(updatedTemplate.getName());
        existingTemplate.setContent(updatedTemplate.getContent());
        existingTemplate.setDescription(updatedTemplate.getDescription());
        existingTemplate.setMetadata(updatedTemplate.getMetadata());
        return templateRepository.save(existingTemplate);
    }

    public void deleteTemplate(Long id) {
        if (!templateRepository.existsById(id)) {
            throw new RuntimeException("Template not found with ID: " + id);
        }
        templateRepository.deleteById(id);
    }

    public NotificationTemplate getTemplateByName(String name) {
        return templateRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Template not found with name: " + name));
    }
}
