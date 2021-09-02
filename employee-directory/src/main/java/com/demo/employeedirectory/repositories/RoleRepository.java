package com.demo.employeedirectory.repositories;

import com.demo.employeedirectory.domain.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleMaster, Integer> {
}
