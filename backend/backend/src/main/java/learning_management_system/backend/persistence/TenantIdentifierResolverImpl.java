package learning_management_system.backend.persistence;

import learning_management_system.backend.utility.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    private static final Logger logger = LoggerFactory.getLogger(TenantIdentifierResolverImpl.class);
    private static final String DEFAULT_TENANT = "lms_schema"; // Define a fallback tenant

    @Autowired
    private TenantSchemaService tenantSchemaService; // calling TenantSchemaService class

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenant();

        if (tenantId == null) {
            logger.warn("No tenant identifier found in context. Falling back to default tenant: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT; // Return default tenant schema if none is set
        }

        logger.debug("Resolved tenant identifier: {}", tenantId);
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true; // Ensures consistency across sessions and existing sessions are tied to the correct tenant
    }


}

