package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Permission;
import learning_management_system.backend.entity.Role;
import learning_management_system.backend.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);

    Optional<Role> findByRoleName(String roleName);   // Custom query method

    List<Role> findByTenant_TenantId(Long tenantId);

    List<Role> findByRoleType(RoleType roleType);

    Optional<Role> findByRoleNameAndTenant_TenantId(String roleName, Long tenantId);

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions WHERE r.roleId = :roleId")
    Role findByIdWithPermissions(@Param("roleId") Long roleId);

    @Query("SELECT p FROM Permission p WHERE p.expiresAt < CURRENT_TIMESTAMP")
    List<Permission> findExpiredPermissions();


    @Query("SELECT r FROM Role r WHERE " +
            "(:criteria IS NULL OR r.roleName LIKE %:criteria%) AND " +
            "(:isActive IS NULL OR r.isActive = :isActive) AND " +
            "(:tenantId IS NULL OR r.tenant.tenantId = :tenantId)")
    Page<Role> findByCriteria(
            @Param("criteria") String criteria,
            @Param("isActive") Boolean isActive,
            @Param("tenantId") Long tenantId,
            Pageable pageable
    );


    Optional<Role> findByRoleNameAndTenant_TenantIdAndIsDeletedFalse(String roleName, Long tenantId);



//    @Query("SELECT r.users FROM Role r WHERE r.roleId = :roleId")
//    Set<User> findUsersByRoleId(@Param("roleId") Long roleId);

}
