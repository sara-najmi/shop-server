package com.najmi.shop.vendor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vendor")
public record VendorController(VendorService vendorService) {


    @GetMapping("fetch")
    void fetch() throws Exception {
        vendorService.fetch();
    }
}
