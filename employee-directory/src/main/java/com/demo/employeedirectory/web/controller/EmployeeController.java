package com.demo.employeedirectory.web.controller;

import com.demo.employeedirectory.domain.Employee;
import com.demo.employeedirectory.domain.EmployeeRoleMapping;
import com.demo.employeedirectory.domain.EmployeeRoleMappingId;
import com.demo.employeedirectory.domain.RoleMaster;
import com.demo.employeedirectory.mappers.EntityDtoMapper;
import com.demo.employeedirectory.repositories.EmployeeRepository;
import com.demo.employeedirectory.repositories.EmployeeRoleMappingRepository;
import com.demo.employeedirectory.repositories.RoleRepository;
import com.demo.employeedirectory.web.model.EmployeeDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/employees")
@RestController
@CrossOrigin(value = "http://localhost:4200/")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final RoleRepository roleRepository;
    private final EmployeeRoleMappingRepository employeeRoleMappingRepository;

    public EmployeeController(EmployeeRepository employeeRepository, EntityDtoMapper entityDtoMapper, RoleRepository roleRepository, EmployeeRoleMappingRepository employeeRoleMappingRepository) {
        this.employeeRepository = employeeRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.roleRepository = roleRepository;
        this.employeeRoleMappingRepository = employeeRoleMappingRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam(required = false) String firstname, @RequestParam(required = false) String lastName){
        List<Employee> entities = new ArrayList<>();
        if(firstname != null && lastName != null) {
            entities = employeeRepository.findByFirstNameLikeAndLastNameLike(firstname, lastName);
        } else if(firstname != null) {
            entities = employeeRepository.findByFirstNameLike(firstname);
        } else if(lastName != null) {
            entities = employeeRepository.findByLastNameLike(lastName);
        } else {
            entities = employeeRepository.findAll();
        }
        List<EmployeeDto> employees = entities.stream()
                .map(employee -> entityDtoMapper.employeeToEmployeeDto(employee))
                .collect(Collectors.toList());

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("employeeId") Integer id) {
        EmployeeDto employee = entityDtoMapper.employeeToEmployeeDto(employeeRepository.getById(id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(entityDtoMapper.employeeDtoToEmployee(employeeDto));
        RoleMaster roleMaster = roleRepository.save(entityDtoMapper.roleMasterDtoToRoleMaster(employeeDto.getCurrentRole()));
        EmployeeRoleMapping employeeRoleMapping = EmployeeRoleMapping.builder()
                .id(EmployeeRoleMappingId.builder()
                        .roleId(roleMaster.getId())
                        .employeeId(savedEmployee.getId())
                        .effectiveDate(new Date()).build())
                .employee(savedEmployee)
                .role(roleMaster)
                .build();
         employeeRoleMappingRepository.save(employeeRoleMapping);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION,ServletUriComponentsBuilder.fromCurrentRequestUri().build().toString()+savedEmployee.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity updateEmployee(@PathVariable("employeeId") Integer id, @RequestBody EmployeeDto employeeDto) {
        Employee currentEmployee = employeeRepository.getById(id);
        Employee savedEmployee = employeeRepository.save(entityDtoMapper.employeeDtoToEmployee(employeeDto));
       if(getCurrentRole(currentEmployee.getRoles()).getId().getRoleId() != employeeDto.getCurrentRole().getId()) {
            EmployeeRoleMapping employeeRoleMapping = EmployeeRoleMapping.builder()
                    .id(EmployeeRoleMappingId.builder()
                            .roleId(employeeDto.getCurrentRole().getId())
                            .employeeId(savedEmployee.getId())
                            .effectiveDate(new Date()).build())
                    .employee(savedEmployee)
                    .role(entityDtoMapper.roleMasterDtoToRoleMaster(employeeDto.getCurrentRole()))
                    .build();
            employeeRoleMappingRepository.save(employeeRoleMapping);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity deleteEmployeeById(@PathVariable("employeeId") Integer id) {
         employeeRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private EmployeeRoleMapping getCurrentRole(List<EmployeeRoleMapping> roles) {
        Collections.sort(roles, (r1, r2) -> r2.getId().getEffectiveDate().compareTo(r1.getId().getEffectiveDate()));
        return roles.get(0);
    }



}
