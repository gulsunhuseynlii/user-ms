package com.gulsun.user_ms.mapper;

import com.gulsun.user_ms.dtos.user.CreateUser;
import com.gulsun.user_ms.dtos.user.UpdateUser;
import com.gulsun.user_ms.dtos.user.UserResponse;
import com.gulsun.user_ms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

   User toEntity(CreateUser createUser);

   @Mapping(target = "role", expression = "java(user.getRole().getName())")
   UserResponse toResponse(User user);

   void updateUserFromDto(
           UpdateUser dto,
           @MappingTarget User user
   );
}