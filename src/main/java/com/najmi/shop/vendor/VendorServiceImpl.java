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

        String[] userAgents = {
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/91.0.864.59 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/89.0",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Safari/14.1.1",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Mobile/15E148 Safari/604.1",
                "Mozilla/5.0 (Android 11; Mobile; rv:68.0) Gecko/68.0 Firefox/68.0",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 OPR/77.0.4054.277",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Vivaldi/4.1.2369.21",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Brave/1.26.74",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 YaBrowser/21.6.3.757 Yowser/2.5 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 DuckDuckGo/5",
                // Add more user agents as needed
        };



        String url = BASE_URL + "/marketplace/storeslist/tehran/electronic-devices";
        Document document = Jsoup.connect(url)
                .userAgent(userAgents[(int) (Math.random() * userAgents.length)])
                .get();
        Elements links = document.select("a[href*=marketplace]");
        for (Element link : links) {
            var vendor = new Vendor();
            Thread.sleep(1000);
            vendor.setTitle(link.select(".kt-event-row__text--title").text());
            vendor.setAddress(link.select(".kt-event-row__text--subtitle").text());
            vendorRepository.save(vendor);
            Document documentProduct = Jsoup.connect(BASE_URL + link.select("a[href]").attr("href"))
                    .userAgent(userAgents[(int) (Math.random() * userAgents.length)])
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
