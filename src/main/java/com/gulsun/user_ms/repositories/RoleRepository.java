package com.gulsun.user_ms.repositories;

import com.gulsun.user_ms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
