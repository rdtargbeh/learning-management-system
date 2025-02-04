package learning_management_system.backend.persistence;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides tenant-specific database connections for multi-tenancy using schema-based isolation.
 */
@Component
public class SchemaRoutingProvider implements MultiTenantConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(SchemaRoutingProvider.class);
    private final DataSource dataSource;

    public SchemaRoutingProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Retrieves a tenant-specific connection.
     *
     * @param tenantIdentifier the identifier of the tenant schema.
     * @return a connection set to the tenant's schema.
     * @throws SQLException if schema switching fails or the connection cannot be established.
     */
    private Connection getTenantConnection(String tenantIdentifier) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            logger.debug("Switching to schema: {}", tenantIdentifier);
            connection.setSchema(tenantIdentifier); // Switch to the tenant's schema
        } catch (SQLException e) {
            logger.error("Failed to set schema for tenant: {}", tenantIdentifier, e);
            throw new SQLException("Could not set schema for tenant: " + tenantIdentifier, e);
        }
        return connection;
    }

    /**
     * Provides a connection for a specific tenant (schema).
     */
    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        if (tenantIdentifier == null) {
            throw new IllegalArgumentException("Tenant identifier cannot be null.");
        }
        return getTenantConnection(tenantIdentifier.toString());
    }

    /**
     * Provides a generic connection, not tied to any tenant.
     */
    @Override
    public Connection getAnyConnection() throws SQLException {
        logger.debug("Providing a generic (non-tenant-specific) connection.");
        return dataSource.getConnection();
    }

    /**
     * Releases a generic connection.
     */
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            logger.debug("Releasing a generic (non-tenant-specific) connection.");
            connection.close();
        }
    }

    /**
     * Releases a tenant-specific connection.
     */
    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        logger.debug("Releasing connection for tenant: {}", tenantIdentifier);
        releaseAnyConnection(connection);
    }

    /**
     * Indicates whether this provider supports aggressive connection release.
     *
     * @return false as connections are kept stable during transaction execution.
     */
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    /**
     * Provides database connection information for the given dialect.
     */
    @Override
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String userName = connection.getMetaData().getUserName();
            return new CustomDatabaseConnectionInfo(url, userName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve database connection info", e);
        }
    }


    /**
     * Checks if this provider can be unwrapped to a specific interface.
     */
    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    /**
     * Unwraps this provider to the specified interface.
     */
    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return isUnwrappableAs(unwrapType) ? unwrapType.cast(this) : null;
    }

//    @Override
//    public <T> T unwrap(Class<T> unwrapType) {
//        if (isUnwrappableAs(unwrapType)) {
//            return unwrapType.cast(this);
//        }
//        return null;
//    }


}
