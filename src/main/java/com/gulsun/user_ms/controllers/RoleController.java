package com.gulsun.user_ms.controllers;

import com.gulsun.user_ms.dtos.role.CreateRole;
import com.gulsun.user_ms.dtos.role.RoleResponse;
import com.gulsun.user_ms.dtos.role.UpdateRole;
import com.gulsun.user_ms.exceptions.ErrorResponse;
import com.gulsun.user_ms.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse> > getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRole createRole) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.createRole(createRole));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse>  updateRole(@PathVariable Long id,@Valid  @RequestBody UpdateRole updateRole) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.updateRole(id, updateRole));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
