package com.zen.cameltest.integrationroutes;

import com.zen.cameltest.users.UserDto;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class UserEmailSenderRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:sendEmail")
        .unmarshal().json(JsonLibrary.Jackson, UserDto.class)
        .process(exchange -> {
          UserDto user = exchange.getMessage().getBody(UserDto.class);

          String emailHtml = "<!DOCTYPE html>"
              + "<html>"
              + "  <body>"
              + "    <h3>Welcome " + user.getUsername() + " !</h3>"
              + "  </body>"
              + "</html>";

          exchange.getMessage().setBody(emailHtml);
        })
        .to("log:com.zen.Email?level=INFO");
  }

}
