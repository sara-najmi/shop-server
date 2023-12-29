package com.najmi.shop.invoice;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("invoice")
public record InvoiceController(InvoiceService invoiceService) {

    @PostMapping()
    public InvoiceModel create(@RequestBody InvoiceModel invoiceModel) throws Exception {
        return invoiceService.create(invoiceModel);
    }

    @GetMapping()
    public List<InvoiceModel> listAdmin() throws Exception {
        return invoiceService.listAdmin();
    }

    @GetMapping("/user/{id}")
    public List<InvoiceModel> list(@PathVariable Integer id) throws Exception {
        return invoiceService.list(id);
    }
}
