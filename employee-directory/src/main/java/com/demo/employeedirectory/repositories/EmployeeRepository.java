package com.demo.employeedirectory.repositories;

import com.demo.employeedirectory.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    List<Employee> findByFirstNameLike(String firstName);

    List<Employee> findByLastNameLike(String lastName);

    List<Employee> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
