package com.gulsun.user_ms.mapper;

import com.gulsun.user_ms.dtos.role.CreateRole;
import com.gulsun.user_ms.dtos.role.RoleResponse;
import com.gulsun.user_ms.dtos.role.UpdateRole;
import com.gulsun.user_ms.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

//    @Mapping(target = "name", source = "name")
    Role toEntity(CreateRole createRole);

//    @Mapping(target = "name", source = "name")
    RoleResponse toResponse(Role role);

    void updateRoleFromDto(UpdateRole dto, @MappingTarget Role role);
}
