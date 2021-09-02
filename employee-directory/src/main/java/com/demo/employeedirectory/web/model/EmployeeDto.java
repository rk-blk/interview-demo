package com.demo.employeedirectory.web.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
	private Integer id;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date dateOfJoining;
	private Date dateOfExit;
	private StatusType status;
	private RoleMasterDto currentRole;
	private List<EmployeeRoleMappingDto> roles;
}
