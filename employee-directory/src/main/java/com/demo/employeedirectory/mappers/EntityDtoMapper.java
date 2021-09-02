package com.demo.employeedirectory.mappers;

import com.demo.employeedirectory.domain.Employee;
import com.demo.employeedirectory.domain.EmployeeRoleMapping;
import com.demo.employeedirectory.domain.EmployeeRoleMappingId;
import com.demo.employeedirectory.domain.RoleMaster;
import com.demo.employeedirectory.web.model.EmployeeDto;
import com.demo.employeedirectory.web.model.EmployeeRoleMappingDto;
import com.demo.employeedirectory.web.model.RoleMasterDto;
import com.demo.employeedirectory.web.model.StatusType;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface EntityDtoMapper {

    @BeforeMapping
    default void avoidRecursive(Employee employee) {
        if(employee != null && employee.getRoles() != null) {
            if(employee.getRoles().size() > 0) {
                List<EmployeeRoleMapping> roles = employee.getRoles();
                roles.forEach(roleMapping -> {
                    roleMapping.setEmployee(null);
                   if(roleMapping.getRole()!=null) roleMapping.getRole().setEmployees(null);
                });
            }
        }
    }
    @BeforeMapping
    default void addEmployeeRoleMappings(EmployeeDto employeeDto) {
        if(employeeDto != null && employeeDto.getCurrentRole() != null) {
            List<EmployeeRoleMappingDto> roles = new ArrayList<>();
            roles.add(EmployeeRoleMappingDto.builder()
                                  .employee(employeeDto)
                                  .role(employeeDto.getCurrentRole())
                                  .build());
            roles.forEach(roleMapping -> {
                roleMapping.getEmployee().setRoles(null);
            });
            employeeDto.setRoles(roles);
        }
    }

    @BeforeMapping
    default void avoidRoleMappingRecursive(RoleMaster roleMaster) {
        if(roleMaster != null && roleMaster.getEmployees() != null) {
            roleMaster.setEmployees(null);
        }
    }



    @Named(value = "toCurrentRoleMapperDto")
    default RoleMasterDto mapRoleMapToCurrentRoleDto(List<EmployeeRoleMapping> roles) {
        if (roles.size() > 0) {
            Collections.sort(roles, (r1, r2) -> r2.getId().getEffectiveDate().compareTo(r1.getId().getEffectiveDate()));
            EmployeeRoleMapping currentRoleMapping = roles.get(0);
            return RoleMasterDto.builder()
                    .id(currentRoleMapping.getRole().getId())
                    .roleName(currentRoleMapping.getRole().getRoleName())
                    .build();
        }
        return null;
    }

    default Integer toInteger(StatusType status) {
        return status.getStatus();
    }

    default StatusType toStatusType(Integer status) {
        return StatusType.getStatusType(status);
    }

    default Employee employeeDtoToEmployee(EmployeeDto employeeDto){
        Employee employee = Employee.builder()
                .id(employeeDto.getId())
                .firstName(employeeDto.getFirstName())
                .middleName(employeeDto.getMiddleName())
                .lastName(employeeDto.getLastName())
                .status(employeeDto.getStatus().getStatus())
                .dateOfJoining(employeeDto.getDateOfJoining())
                .build();

       /* RoleMaster roleMaster = roleMasterDtoToRoleMaster(employeeDto.getCurrentRole());
        EmployeeRoleMapping employeeRoleMapping = EmployeeRoleMapping.builder()
                .id(EmployeeRoleMappingId.builder().roleId(employeeDto.getCurrentRole().getId()).effectiveDate(new Date()).build())
                .role(roleMaster)
                .employee(employee)
                .build();
        roleMaster.addRoleMapping(employeeRoleMapping);
        employee.addRoleMapping(employeeRoleMapping);*/
        return employee;
    }

    @Mapping(source = "roles", target = "currentRole", qualifiedByName = "toCurrentRoleMapperDto")
    public EmployeeDto employeeToEmployeeDto(Employee employee) ;
    public RoleMaster roleMasterDtoToRoleMaster(RoleMasterDto roleMasterDto);
    public EmployeeRoleMapping employeeRoleMappingDtoToEmployeeRoleMapping(EmployeeRoleMappingDto employeeRoleMappingDto);
    public RoleMasterDto roleMasterToRoleMasterDto(RoleMaster roleMaster);
    public EmployeeRoleMappingDto employeeRoleMappingToEmployeeRoleMappingDto(EmployeeRoleMapping employeeRoleMapping);

}
