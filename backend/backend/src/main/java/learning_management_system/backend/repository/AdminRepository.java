package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds all admins by user ID.
     * @param userId The ID of the User.
     * @return A Optional of Admin entities.
     */
    Optional<Admin> findByUser_UserId(Long userId);

    /**
     * Finds all admins by tenant ID.
     * @param tenantId The ID of the tenant.
     * @return A list of Admin entities.
     */
    List<Admin> findByTenant_TenantId(Long tenantId);

    /**
     * Finds all admins by tenant ID.
     * @param adminLevel The role level of the tenant.
     * @return A list of Admin entities.
     */
    List<Admin> findByAdminLevel(String adminLevel);

    /**
     * Finds all admins by department ID.
     * @param departmentId The ID of the department.
     * @return A list of Admin entities.
     */
    List<Admin> findByDepartmentDepartmentId(Long departmentId);
}
