package com.zen.cameltest.users;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {

  public UserDto toDto(UserEntity entity) {
    UserDto dto = new UserDto();
    dto.setId(entity.getId());
    dto.setUsername(entity.getUsername());
    dto.setPassword(entity.getPassword());
    dto.setName(entity.getName());
    dto.setSurname(entity.getSurname());
    dto.setVersion(entity.getVersion());

    return dto;
  }

  public Collection<UserDto> toDto(Collection<UserEntity> entities) {
    return entities.stream().map(this::toDto).collect(Collectors.toList());
  }

}
