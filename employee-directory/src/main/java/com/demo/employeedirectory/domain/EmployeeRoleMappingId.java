package com.demo.employeedirectory.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmployeeRoleMappingId implements Serializable {
    private Integer employeeId;
    private Integer roleId;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "EffectiveDate")
    private Date effectiveDate;
}
