package com.najmi.shop.vendor;


import com.najmi.shop.product.orm.Product;
import com.najmi.shop.product.orm.ProductImage;
import com.najmi.shop.product.orm.ProductImageRepository;
import com.najmi.shop.product.orm.ProductRepository;
import com.najmi.shop.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private static final String BASE_URL = "https://divar.ir";

    private final VendorRepository vendorRepository;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fetch() throws Exception {
        String url = BASE_URL + "/marketplace/storeslist/tehran/electronic-devices";
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .get();
        Elements links = document.select("a[href*=marketplace]");
        for (Element link : links) {
            var vendor = new Vendor();
            Thread.sleep(1000);
            vendor.setTitle(link.select(".kt-event-row__text--title").text());
            vendor.setAddress(link.select(".kt-event-row__text--subtitle").text());
            vendorRepository.save(vendor);
            Document documentProduct = Jsoup.connect(BASE_URL + link.select("a[href]").attr("href"))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();
            Elements productLinks = documentProduct.select("a[href*=/v]");
            for (Element productLink : productLinks) {
                var product = new Product();
                product.setTitle(productLink.select(".kt-post-card__title").text());
                product.setPrice(new BigDecimal(CommonUtil.convertToEnglishDigits(productLink
                        .select(".kt-post-card__description").text().replace(" ", "")
                        .replace("تومان", "").replace("٬", ""))));
                product.setVendor(vendor);
                productRepository.save(product);
                productImageRepository.save(new ProductImage(product, productLink.select(".kt-image-block__image").attr("data-src")));
                productImageRepository.save(new ProductImage(product, productLink.select(".kt-image-block__image").attr("data-src")));
            }
        }
    }
}
