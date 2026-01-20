package com.example.blogging.web;

import com.example.blogging.dto.role.RoleRequestDto;
import com.example.blogging.dto.role.RoleResponseDto;
import com.example.blogging.dto.role.RoleSetResponseDto;
import com.example.blogging.service.RoleService;
import com.example.blogging.web.mapper.RoleWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    private final RoleWebMapper roleWebMapper;

    @GetMapping
    public ResponseEntity<RoleSetResponseDto> getAllRoles() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RoleSetResponseDto(
                        roleService.getAllRoles().stream()
                                .map(roleWebMapper::toResponseDto)
                                .collect(Collectors.toSet())
                ));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long roleId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(roleWebMapper.toResponseDto(roleService.getRoleById(roleId)));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(roleWebMapper.toResponseDto(roleService.createRole(roleRequestDto)));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
        return ResponseEntity.noContent().build();
    }
}
