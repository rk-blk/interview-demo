package com.demo.employeedirectory.domain;

import com.demo.employeedirectory.web.model.EmployeeRoleMappingDto;
import com.demo.employeedirectory.web.model.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoleMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Integer id;
    @Column(name = "RoleName", nullable = true)
    private String roleName;
    @Column(name = "Status", nullable = true)
    private Integer status;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "role")
    private List<EmployeeRoleMapping> employees = new ArrayList<>();
    public void addRoleMapping(EmployeeRoleMapping employeeRoleMapping) {
        if(employees == null) employees = new ArrayList<>();
        employees.add(employeeRoleMapping);
    }

}
