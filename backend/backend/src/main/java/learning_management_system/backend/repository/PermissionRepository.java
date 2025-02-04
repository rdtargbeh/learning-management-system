package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String permissionName);

    List<Permission> findByResourceAndIsDeletedFalse(String resource);

    List<Permission> findByIsActiveTrueAndIsDeletedFalse();

//    List<Permission> findByParentPermissionAndIsDeletedFalse(Long parentPermissionId);

    List<Permission> findByParentPermissionAndIsDeletedFalse(Permission parentPermission);


    @Query("SELECT p FROM Permission p WHERE p.role.roleId = :roleId AND p.isDeleted = false")
    List<Permission> findByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT p FROM Permission p WHERE p.isDeleted = false")
    List<Permission> findByIsDeletedFalse();

    @Query("SELECT p FROM Permission p WHERE p.permissionId = :permissionId AND p.isDeleted = false")
    Optional<Permission> findByIdAndIsDeletedFalse(@Param("permissionId") Long permissionId);

    @Query("SELECT p FROM Permission p WHERE p.expiresAt < CURRENT_TIMESTAMP")
    List<Permission> findExpiredPermissions();


}

