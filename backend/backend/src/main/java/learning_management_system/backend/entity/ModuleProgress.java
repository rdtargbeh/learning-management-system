package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@Entity
@Table(name = "module_progress")
public class ModuleProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_progress_id", nullable = false, updatable = false)
    private Long moduleProgressId;

    public Long getModuleProgressId() {
        return moduleProgressId;
    }

    public void setModuleProgressId(Long moduleProgressId) {
        this.moduleProgressId = moduleProgressId;
    }
}
