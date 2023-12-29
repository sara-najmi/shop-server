package com.najmi.shop.invoice;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface InvoiceService {

    InvoiceModel create(InvoiceModel invoiceModel) throws Exception;

    List<InvoiceModel> list(Integer id) throws Exception;

    List<InvoiceModel> listAdmin() throws Exception;
}
