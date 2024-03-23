package org.hpd.customerservice.rest;

import org.hpd.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerRepository oneTimePasscodeRepository;
    private OneTimePasscodeMapper oneTimePasscodeMapper;

    @Autowired
    public OneTimePasscodeRestService(OneTimePasscodeRepository oneTimePasscodeRepository,
                                      OneTimePasscodeMapper oneTimePasscodeMapper) {
        this.oneTimePasscodeRepository = oneTimePasscodeRepository;
        this.oneTimePasscodeMapper = oneTimePasscodeMapper;
    }

    @GetMapping("/customers")
    public String getAllCustomers() {
        return this.
    }
}
