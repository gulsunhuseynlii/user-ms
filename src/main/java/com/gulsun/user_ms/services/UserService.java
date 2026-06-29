package com.gulsun.user_ms.services;

import com.gulsun.user_ms.dtos.user.ChangePasswordRequest;
import com.gulsun.user_ms.dtos.user.CreateUser;
import com.gulsun.user_ms.dtos.user.UpdateUser;
import com.gulsun.user_ms.dtos.user.UserResponse;
import com.gulsun.user_ms.entity.Role;
import com.gulsun.user_ms.entity.User;
import com.gulsun.user_ms.entity.UserStatus;
import com.gulsun.user_ms.mapper.UserMapper;
import com.gulsun.user_ms.repositories.RoleRepository;
import com.gulsun.user_ms.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserResponse getByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        return userMapper.toResponse(user);
    }
    public UserResponse createUser(CreateUser createUser){
        User user = userMapper.toEntity(createUser);

        Role role = roleRepository.findById(
                createUser.getRoleId()
        ).orElseThrow();

        user.setRole(role);

        String tempPassword = UUID.randomUUID().toString().substring(0,8);
        user.setPassword(
                passwordEncoder.encode(tempPassword)
        );
       user.setStatus(UserStatus.Pending);
        User savedUser = userRepository.save(user);
     emailService.sendTempPassword(user.getEmail(), tempPassword);
        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> getAllUsers(){
        log.info("Getting all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
    public UserResponse getUserById(Long id){

        User user = userRepository.findById(id).
        orElseThrow(
                () -> new NoSuchElementException("NO CUSTOMER PRESENT WITH ID = " + id));
        log.info("Getting user by id: {}", id);
        return userMapper.toResponse(user);
    }
    public UserResponse updateUser(Long id, UpdateUser updateUser){
      User user = userRepository.findById(id)
              .orElseThrow(
                      () -> new NoSuchElementException("NO CUSTOMER PRESENT WITH ID = " + id));

        userMapper.updateUserFromDto(updateUser, user);
        log.info("Updating user with id: {}", id);
        user.setPassword(
                passwordEncoder.encode(updateUser.getPassword())
        );
      User saved = userRepository.save(user);
      return userMapper.toResponse(saved);
    }
    public void deleteUser(Long id){
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
    public void changePassword(
            Long userId,
            ChangePasswordRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean matches =
                passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword()
                );

        if (!matches) {
            throw new RuntimeException(
                    "Old password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        user.setStatus(UserStatus.Active);

        userRepository.save(user);
    }
}
// exception handler
// logstash
//checkstyle
