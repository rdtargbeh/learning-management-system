package learning_management_system.backend.mapper;


import learning_management_system.backend.dto.DepartmentDto;
import learning_management_system.backend.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto toDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setDepartmentId(department.getDepartmentId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        departmentDto.setDepartmentDescription(department.getDepartmentDescription());
        departmentDto.setDepartmentAddress(department.getDepartmentAddress());
        departmentDto.setDepartmentEmail(department.getDepartmentEmail());
        departmentDto.setDepartmentPhoneNumber(department.getDepartmentPhoneNumber());
        departmentDto.setDepartmentHead(department.getDepartmentHead());
        departmentDto.setDateUpdated(department.getDateUpdated());

        return departmentDto;
    }

    public static Department toEntity(DepartmentDto departmentDto){
        Department department  = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        department.setDepartmentAddress(departmentDto.getDepartmentAddress());
        department.setDepartmentEmail(departmentDto.getDepartmentEmail());
        department.setDepartmentPhoneNumber(departmentDto.getDepartmentPhoneNumber());
        department.setDepartmentHead(departmentDto.getDepartmentHead());
        department.setDateUpdated(departmentDto.getDateUpdated());

        return department;
    }
}
