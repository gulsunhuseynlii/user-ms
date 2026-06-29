package com.gulsun.user_ms.controllers;

import com.gulsun.user_ms.dtos.user.ChangePasswordRequest;
import com.gulsun.user_ms.dtos.user.CreateUser;
import com.gulsun.user_ms.dtos.user.UpdateUser;
import com.gulsun.user_ms.dtos.user.UserResponse;
import com.gulsun.user_ms.exceptions.ErrorResponse;
import com.gulsun.user_ms.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                userService.getByEmail(email)
        );
    }
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUser createUser)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(createUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>>  getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser( @PathVariable Long id,@Valid @RequestBody UpdateUser updateUser) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, updateUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {

        userService.changePassword(
                id,
                request
        );

        return ResponseEntity.ok(
                "Password changed successfully"
        );
    }


    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
