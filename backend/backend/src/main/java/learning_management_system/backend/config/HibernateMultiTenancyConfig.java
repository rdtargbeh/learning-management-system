package learning_management_system.backend.config;

import learning_management_system.backend.persistence.SchemaRoutingProvider;
import learning_management_system.backend.persistence.TenantIdentifierResolverImpl;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class HibernateMultiTenancyConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * Configures the EntityManagerFactory with multi-tenancy properties.
     *
     * @return the configured LocalContainerEntityManagerFactoryBean.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        emf.setDataSource(dataSource);  // Set DataSource
        emf.setPackagesToScan("learning_management_system.backend.entity");    // Entity scanning
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());  // Set Hibernate JPA vendor adapter

        // Set Hibernate JPA vendor adapter
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        emf.setJpaVendorAdapter(vendorAdapter);

        // Multi-tenancy settings
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.multiTenancy", "SCHEMA"); // Use string-based key
        jpaProperties.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver());
        jpaProperties.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider());
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        // Add optional Hibernate properties for debugging or tuning
        jpaProperties.put("hibernate.format_sql", true);
        jpaProperties.put("hibernate.show_sql", true);

        emf.setJpaPropertyMap(jpaProperties);

        return emf;
    }

    /**
     * Configures the MultiTenantConnectionProvider, enabling schema-based tenant isolation.
     *
     * @return a SchemaRoutingProvider instance.
     */
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new SchemaRoutingProvider(dataSource);
    }

    /**
     * Resolves the current tenant identifier dynamically.
     *
     * @return the TenantIdentifierResolver implementation.
     */
    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new TenantIdentifierResolverImpl();
    }


}
