package com.najmi.shop.dashboard;



import com.najmi.shop.invoice.InvoiceModel;

import java.util.List;

public interface DashboardService {

    DashboardCountModel countList();

    List<InvoiceModel> invoiceList();
}
