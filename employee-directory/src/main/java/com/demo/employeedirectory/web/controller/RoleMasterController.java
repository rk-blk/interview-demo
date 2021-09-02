package com.demo.employeedirectory.web.controller;

import com.demo.employeedirectory.domain.Employee;
import com.demo.employeedirectory.domain.RoleMaster;
import com.demo.employeedirectory.mappers.EntityDtoMapper;
import com.demo.employeedirectory.repositories.RoleRepository;
import com.demo.employeedirectory.web.model.RoleMasterDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.ws.Response;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(value = "http://localhost:4200/")
public class RoleMasterController {

    private final RoleRepository roleRepository;
    private final EntityDtoMapper entityDtoMapper;

    public RoleMasterController(RoleRepository roleRepository, EntityDtoMapper entityDtoMapper) {
        this.roleRepository = roleRepository;
        this.entityDtoMapper = entityDtoMapper;
    }

    @GetMapping
    public ResponseEntity< List<RoleMasterDto>> listRoles() {
        List<RoleMaster> entities = this.roleRepository.findAll();
        List<RoleMasterDto> roleDtoList = entities.stream()
                .map(entity -> entityDtoMapper.roleMasterToRoleMasterDto(entity))
                .collect(Collectors.toList());
        return new ResponseEntity(roleDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRole(@RequestBody RoleMasterDto roleMasterDto) {
        RoleMaster roleMaster = this.roleRepository.save(this.entityDtoMapper.roleMasterDtoToRoleMaster(roleMasterDto));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, ServletUriComponentsBuilder.fromCurrentRequestUri().build().toString()+roleMaster.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleid}")
    public ResponseEntity deleteRole(@PathVariable("roleid") Integer roleid) {
        this.roleRepository.deleteById(roleid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
