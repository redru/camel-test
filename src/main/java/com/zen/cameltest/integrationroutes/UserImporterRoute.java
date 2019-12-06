package com.zen.cameltest.integrationroutes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserImporterRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("file-watch://./filein?events=CREATE")
        .throttle(2).timePeriodMillis(2000)
        .routeId("UserImporterRoute")
        .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
        .setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http.HttpMethods.POST))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .to("http://localhost:8080/api/users/")
        .multicast().parallelProcessing()
        .to("file://./fileout?fileName=${exchangeId}-${date:now:hhmmss}-${header.CamelFileName}"/*,
            "rabbitmq:usersCreationQueue"*/,
            "direct:sendEmail");
  }

}
