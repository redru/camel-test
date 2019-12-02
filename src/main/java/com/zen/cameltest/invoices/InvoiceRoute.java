package com.zen.cameltest.invoices;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceRoute extends RouteBuilder {

  private final InvoiceService invoiceService;

  @Autowired
  public InvoiceRoute(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @Override
  public void configure() throws Exception {
    restConfiguration()
        .contextPath("/camel")
        .port(8080)
        .enableCORS(true)
        .apiContextPath("/api-doc")
        .apiProperty("api.title", "Test REST API")
        .apiProperty("api.version", "v1")
        .apiContextRouteId("doc-api")
        .component("servlet")
        .bindingMode(RestBindingMode.json);

    rest("/api/")
        .id("api-route")
        .consumes("application/json")
        .post("/bean")
        .bindingMode(RestBindingMode.json)
        .type(Invoice.class)
        .to("direct:remoteService");

    from("direct:remoteService")
        .routeId("direct-route")
        .tracing()
        .log(">>> ${body.id}")
        .log(">>> ${body.code}")
        .process(exchange -> {
          Invoice bodyIn = (Invoice) exchange.getIn().getBody();
          invoiceService.example(bodyIn);
          exchange.getIn().setBody(bodyIn);
        })
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
  }

}
