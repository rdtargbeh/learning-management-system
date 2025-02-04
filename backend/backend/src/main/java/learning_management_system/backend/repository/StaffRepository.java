package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("SELECT s FROM Staff s WHERE s.department.departmentName = :departmentName")
    List<Staff> findByDepartment(@Param("departmentName") String departmentName); // Find staff by department

    List<Staff> findByUser_IsActive(Boolean isActive);    // Find active/inactive staff

}
