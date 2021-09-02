package com.demo.employeedirectory.repositories;

import com.demo.employeedirectory.domain.EmployeeRoleMapping;
import com.demo.employeedirectory.domain.EmployeeRoleMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRoleMappingRepository extends JpaRepository<EmployeeRoleMapping, EmployeeRoleMappingId> { }
