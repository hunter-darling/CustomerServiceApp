package org.hpd.customerservice.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private static final String testString = "Hello, %s!";

    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(testString, name);
    }
}
