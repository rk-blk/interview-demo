package com.demo.employeedirectory.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeRoleMapping {
    @EmbeddedId
    private EmployeeRoleMappingId id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("roleId")
    private RoleMaster role;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("employeeId")
    private Employee employee;

}
