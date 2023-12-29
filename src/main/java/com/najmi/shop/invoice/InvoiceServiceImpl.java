package com.najmi.shop.invoice;


import com.najmi.shop.cart.orm.Cart;
import com.najmi.shop.cart.orm.CartItem;
import com.najmi.shop.cart.orm.CartRepository;
import com.najmi.shop.user.controller.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;

    private final InvoiceItemRepository invoiceItemRepository;

    private final CartRepository cartRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InvoiceModel create(InvoiceModel invoiceModel) throws Exception {
        Cart cart =
                cartRepository.findByUserIdAndIsPayed(invoiceModel.getUser().getId(), false);
        Invoice invoice = new Invoice();
        invoice.setCart(cart);
        invoice.setUser(cart.getUser());
        cart.setIsPayed(true);
        cartRepository.save(cart);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setCartItem(cartItem);
            invoiceItem.setCount(cartItem.getCount());
            invoiceItem.setPrice(cartItem.getPrice());
            invoiceItem.setProduct(cartItem.getProduct());
            invoiceItem.setTotalPrice(cartItem.getTotalPrice());
            invoiceItem.setInvoice(invoice);
            invoiceItemRepository.save(invoiceItem);
            total = total.add(invoiceItem.getTotalPrice());
        }
        invoice.setTotal(total);
        invoiceRepository.save(invoice);
        return invoiceModel;
    }

    @Override
    public List<InvoiceModel> list(Integer id) throws Exception{
        return invoiceRepository.findAllByUserId(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceModel> listAdmin() throws Exception{
        return invoiceRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
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
