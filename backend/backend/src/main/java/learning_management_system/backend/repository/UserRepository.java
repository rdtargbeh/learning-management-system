package learning_management_system.backend.repository;

import learning_management_system.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Find user by email

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleId = :roleId")
    List<User> findUsersByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.roleName = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

    Optional<User> findByUserName(String username);

    @Query("SELECT r.roleName FROM User u JOIN u.roles r WHERE u.userId = :userId")
    String findRoleNameByUserId(@Param("userId") Long userId);

    @Query("SELECT u.id FROM User u WHERE u.userName IN :usernames")
    List<Long> findUserIdsByUsernames(@Param("usernames") List<String> usernames);

    long countByTenant_TenantId(Long tenantId);

    @Query("SELECT COUNT(u) FROM User u WHERE u.tenant.tenantId = :tenantId")
    int countUsersByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT u.email FROM User u JOIN u.tenant t JOIN u.roles r WHERE t.tenantId = :tenantId AND r.roleName = 'ADMIN'")
    String findAdminEmailByTenantId(@Param("tenantId") Long tenantId);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleId = :roleId")
    Set<User> findAllByRolesContaining(@Param("roleId") Long roleId);

    @Query("SELECT u FROM User u JOIN u.assignments a WHERE a.assignmentId = :assignmentId")
    List<User> findUsersByAssignmentId(@Param("assignmentId") Long assignmentId);







}
