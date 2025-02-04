package learning_management_system.backend.persistence;


import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;

public class CustomDatabaseConnectionInfo implements DatabaseConnectionInfo {
    private final String jdbcUrl;
    private final String userName;

    public CustomDatabaseConnectionInfo(String jdbcUrl, String userName) {
        this.jdbcUrl = jdbcUrl;
        this.userName = userName;
    }

    @Override
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @Override
    public @Nullable String getJdbcDriver() {
        return "";
    }

    @Override
    public @Nullable DatabaseVersion getDialectVersion() {
        return null;
    }

    @Override
    public @Nullable String getAutoCommitMode() {
        return "";
    }

    @Override
    public @Nullable String getIsolationLevel() {
        return "";
    }

    @Override
    public @Nullable Integer getPoolMinSize() {
        return 0;
    }

    @Override
    public @Nullable Integer getPoolMaxSize() {
        return 0;
    }


    public String getUser() {
        return userName;
    }


    public String getDatabaseName() {
        // Extracting the database name from the URL if needed
        if (jdbcUrl != null && jdbcUrl.contains("/")) {
            return jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1);
        }
        return null;
    }


    public String getSchemaName() {
        // Returning null or implementing schema-specific logic
        return null;
    }

    @Override
    public String toInfoString() {
        return "JDBC URL: " + jdbcUrl + ", User: " + userName;
    }
}

