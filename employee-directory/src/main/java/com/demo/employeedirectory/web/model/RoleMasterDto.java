package com.demo.employeedirectory.web.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMasterDto {
    private Integer id;
    private StatusType status;
    private String roleName;
    private List<EmployeeRoleMappingDto> employees;
}
