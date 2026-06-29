package com.gulsun.user_ms.services;

import com.gulsun.user_ms.dtos.role.CreateRole;
import com.gulsun.user_ms.dtos.role.RoleResponse;
import com.gulsun.user_ms.dtos.role.UpdateRole;
import com.gulsun.user_ms.entity.Role;
import com.gulsun.user_ms.mapper.RoleMapper;
import com.gulsun.user_ms.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleResponse createRole(CreateRole createRole) {
        Role role = roleMapper.toEntity(createRole);
        log.info("User created successfully with id: {}", role.getId());
        Role savedRole = roleRepository.save(role);
        log.info("Creating user with username: {}", createRole.getName());
        return roleMapper.toResponse(savedRole);
    }

    public List<RoleResponse> getAllRoles() {
        log.info("Getting all users");
        return roleRepository.findAll()
                .stream().map(roleMapper::toResponse).toList();
    }

    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("NO CUSTOMER PRESENT WITH ID = " + id));
        log.info("Getting user by id: {}", id);
        return roleMapper.toResponse(role);
    }


    public RoleResponse updateRole(Long id, UpdateRole updateRole) {
        Role role = roleRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("NO CUSTOMER PRESENT WITH ID = " + id));
        roleMapper.updateRoleFromDto(updateRole, role);
        Role saved = roleRepository.save(role);
        log.info("Updating user with id: {}", id);
        return roleMapper.toResponse(saved);
    }

    public void deleteRole(Long id) {
        log.info("Deleting user with id: {}", id);
        roleRepository.deleteById(id);
    }
}
