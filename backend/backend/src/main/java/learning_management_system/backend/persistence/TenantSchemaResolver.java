package learning_management_system.backend.persistence;

import learning_management_system.backend.utility.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolves the current tenant identifier for Hibernate's multi-tenancy mechanism.
 * Provides a fallback schema when the tenant ID is unavailable.
 */
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {

    private static final Logger logger = LoggerFactory.getLogger(TenantSchemaResolver.class);

    private static final String DEFAULT_TENANT = "default_schema"; // Define your fallback schema

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getTenant();

        if (tenant == null) {
            logger.warn("No tenant identifier found. Falling back to default schema: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT;
        }

        logger.debug("Resolving tenant identifier: {}", tenant);
        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true; // Ensures session consistency across tenant contexts
    }


}
