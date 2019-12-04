package com.zen.cameltest.eip;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class UserFileRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:foo")
        .marshal().json(JsonLibrary.Jackson)
        .to("file://C:/Users/RedrU?fileName=user_2.json");
  }

}
