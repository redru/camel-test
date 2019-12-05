package com.zen.cameltest.users;

import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserConverter userConverter;
  private final UserRepository userRepository;

  @Autowired
  public UserService(
      PasswordEncoder passwordEncoder,
      UserConverter userConverter,
      UserRepository userRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.userConverter = userConverter;
  }

  @Transactional
  public Collection<UserDto> getAllUsers() {
    return userConverter.toDto(Lists.newArrayList(userRepository.findAll()));
  }

  @Transactional
  public UserDto createUser(UserDto user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
    userEntity.setName(user.getName());
    userEntity.setSurname(user.getSurname());
    userEntity.setCreationDate(LocalDateTime.now());

    UserEntity createdUser = userRepository.save(userEntity);

    return userConverter.toDto(createdUser);
  }

}
