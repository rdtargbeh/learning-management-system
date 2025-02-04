package learning_management_system.backend.dto;

public class SubModuleDTO {
    private Long moduleId;
    private String moduleTitle;
    private Integer order;


    public SubModuleDTO(Long moduleId, String moduleTitle, Integer order) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.order = order;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}

