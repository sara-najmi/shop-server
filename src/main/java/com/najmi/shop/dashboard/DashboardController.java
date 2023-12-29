package com.najmi.shop.dashboard;


import com.najmi.shop.invoice.InvoiceModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dashboard")
public record DashboardController(DashboardService dashboardService) {

    @GetMapping("count-all")
    public DashboardCountModel countList() {
        return dashboardService.countList();
    }

    @GetMapping("order")
    public List<InvoiceModel> invoiceList() {
        return dashboardService.invoiceList();
    }

}
