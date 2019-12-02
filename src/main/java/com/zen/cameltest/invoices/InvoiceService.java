package com.zen.cameltest.invoices;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

  public void example(Invoice bodyIn) {
    bodyIn.setId(bodyIn.getId() * 10);
    bodyIn.setCode("Hello, " + bodyIn.getCode());
    bodyIn.setCreationDate(LocalDateTime.now());
  }

}
