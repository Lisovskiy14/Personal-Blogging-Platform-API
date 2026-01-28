package com.example.blogging.web;

import com.example.blogging.dto.permission.PermissionResponseDto;
import com.example.blogging.dto.permission.PermissionSetResponseDto;
import com.example.blogging.service.PermissionService;
import com.example.blogging.web.mapper.PermissionWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionWebMapper permissionWebMapper;

    @GetMapping
    public ResponseEntity<PermissionSetResponseDto> getAllPermissions() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new PermissionSetResponseDto(
                        permissionService.getAllPermissions().stream()
                                .map(permissionWebMapper::toResponseDto)
                                .collect(Collectors.toSet())
                ));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionResponseDto> getPermissionById(@PathVariable Long permissionId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(permissionWebMapper.toResponseDto(
                        permissionService.getPermissionById(permissionId)));
    }
}
