package com.zen.cameltest.users;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(path = "/",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<UserDto>> getUsers() {
    Collection<UserDto> users = userService.getAllUsers();
    users.forEach(user -> user.setPassword(null));

    return ResponseEntity.ok(users);
  }

  @PostMapping(path = "/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDto> postUser(@RequestBody UserDto user) {
    UserDto createdUser = userService.createUser(user);
    createdUser.setPassword(null);

    return ResponseEntity.ok(createdUser);
  }

}
