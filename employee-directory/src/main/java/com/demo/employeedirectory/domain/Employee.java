package com.demo.employeedirectory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(exclude = {"roles"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id",updatable = false, nullable = false)
    private Integer id;

    @Column(name = "FirstName", nullable = true)
    private String firstName;

    @Column(name = "MiddleName", nullable = false)
    private String middleName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "DateOfJoining", nullable = false)
    private Date dateOfJoining;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "DateOfExit", nullable = true)
    private Date dateOfExit;

    @Column(name = "Status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<EmployeeRoleMapping> roles = new ArrayList<>();

    public void addRoleMapping(EmployeeRoleMapping employeeRoleMapping) {
        if(roles == null) roles = new ArrayList<>();
        roles.add(employeeRoleMapping);
    }

}
