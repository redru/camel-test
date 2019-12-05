package com.zen.cameltest.integrationroutes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserImporterRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file-watch://C:/Users/RedrU/IdeaProjects/camel-test/filein?events=CREATE")
        .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
        .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http.HttpMethods.POST))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .to("http://localhost:8080/api/users/")
        .multicast().parallelProcessing()
        .to("file://C:/Users/RedrU/IdeaProjects/camel-test/fileout?fileName=${header.CamelFileName}",
            "rabbitmq:usersCreationQueue");
  }

}
