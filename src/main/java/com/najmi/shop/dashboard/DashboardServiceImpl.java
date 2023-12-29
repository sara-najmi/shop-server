package com.najmi.shop.dashboard;


import com.najmi.shop.invoice.Invoice;
import com.najmi.shop.invoice.InvoiceModel;
import com.najmi.shop.invoice.InvoiceRepository;
import com.najmi.shop.product.orm.ProductRepository;
import com.najmi.shop.user.controller.UserModel;
import com.najmi.shop.user.orm.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;

    private final InvoiceRepository invoiceRepository;

    private final UserRepository userRepository;


    @Override
    public DashboardCountModel countList() {
        return new DashboardCountModel(userRepository.countAll(), invoiceRepository.countAll(), productRepository.countAll(), invoiceRepository.countAllDelivered(), invoiceRepository.countAllNotDelivered());
    }

    @Override
    public List<InvoiceModel> invoiceList() {
        return invoiceRepository.findTop10().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public InvoiceModel convertToDto(Invoice invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setUser(new UserModel(
                invoice.getUser().getId(), invoice.getUser().getFirstname(), invoice.getUser().getLastname(),
                invoice.getUser().getNationalCode(), invoice.getUser().getUniId()
        ));
        invoiceModel.setCreateAt(invoice.getCreatedAt());
        invoiceModel.setTotal(invoice.getTotal());
        invoiceModel.setIsDelivered(invoice.getIsDelivered());
        return invoiceModel;
    }
}
