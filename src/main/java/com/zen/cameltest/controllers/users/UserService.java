package com.zen.cameltest.controllers.users;

import java.time.LocalDateTime;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final ProducerTemplate producerTemplate;

  private final ConsumerTemplate consumerTemplate;

  @Autowired
  public UserService(
      ProducerTemplate producerTemplate,
      ConsumerTemplate consumerTemplate
  ) {
    this.producerTemplate = producerTemplate;
    this.consumerTemplate = consumerTemplate;
  }

  public UserDto getUser(Long id) {
    UserDto user = new UserDto();
    user.setId(id);
    user.setName("Random name");
    user.setCreationDate(LocalDateTime.now());

    producerTemplate.sendBody("direct:foo", user);

    return user;

    /*producerTemplate.setDefaultEndpointUri("direct:readUserFile");
    producerTemplate.sendBody(null);*/

//    return consumerTemplate.receiveBody("direct:foo", UserDto.class);
  }

}
