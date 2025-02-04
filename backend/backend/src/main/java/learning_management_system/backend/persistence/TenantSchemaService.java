package learning_management_system.backend.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TenantSchemaService {

    @Value("${tenant.schemas}")
    private String tenantSchemas; // Inject tenant schemas

    /**
     * Returns the list of configured tenant schemas.
     */
    public List<String> getAvailableSchemas() {
        return Arrays.asList(tenantSchemas.split(","));
    }
}

