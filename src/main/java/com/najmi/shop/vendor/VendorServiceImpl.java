package com.najmi.shop.vendor;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private static final String BASE_URL = "https://divar.ir";

    private static final String BASE_URL_API = "https://api.divar.ir/v8";

    private final VendorRepository vendorRepository;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final RestClient restClient = RestClient.create();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void fetch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
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
            vendor.setTitle(link.select(".kt-event-row__text--title").text());//عنوان عرضه کننده
            vendor.setAddress(link.select(".kt-event-row__text--subtitle").text());//آدرس عرضه کننده
            String res = restClient.get()
                    .uri(BASE_URL_API + link.select("a[href]").attr("href").replace("/stores", "") + "/contact")
                    .header("Authorization", "Basic eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                            ".eyJzaWQiOiJmZWUyMTc4Yy1lYTUxLTRiZDUtOTI0My1jMzI5MDc0YmQxMDkiLCJ1c2VyLXR5cGUiOiJwZXJzb25hbC" +
                            "IsInVzZXItdHlwZS1mYSI6Ilx1MDY3ZVx1MDY0Nlx1MDY0NCBcdTA2MzRcdTA2MmVcdTA2MzVcdTA2Y2MiLCJ1c2Vy" +
                            "IjoiMDkzODI3MzA4MTgiLCJpc3MiOiJhdXRoIiwidmVyaWZpZWRfdGltZSI6MTcwNDk5Nzg4MC4zMzk2NzMsImlhd" +
                            "CI6MTcwNDk5Nzg4MCwiZXhwIjoxNzEwMTgxODgwfQ.eqzcAlDcei_tJ61wp1i4ExiMeQxXJSBmDIKFCtr_eeQ")
                    .retrieve()
                    .body(String.class);
            JsonNode jsonNode = objectMapper.readTree(res);
            // Extract phone_number from the JSON
            String phoneNumber = jsonNode.path("contact").path("phone_number").asText();
            vendor.setPhoneNumber(phoneNumber);
            var linkTemp = BASE_URL + link.select("a[href]").attr("href");
            vendor.setLink(linkTemp);
            vendorRepository.save(vendor);
            Document documentProduct = Jsoup.connect(linkTemp)
                    .userAgent(userAgents[(int) (Math.random() * userAgents.length)])
                    .get();
            Elements productLinks = documentProduct.select("a[href*=/v]");
            for (Element productLink : productLinks) {
                var product = new Product();
                product.setTitle(productLink.select(".kt-post-card__title").text());//عنوان کالا
                product.setPrice(new BigDecimal(CommonUtil.convertToEnglishDigits(productLink
                        .select(".kt-post-card__description").text().replace(" ", "")//مبلغ کالا
                        .replace("تومان", "").replace("٬", ""))));
                product.setVendor(vendor);
                productRepository.save(product);
                productImageRepository.save(new ProductImage(product, productLink.select(".kt-image-block__image").attr("data-src")));//عکس محصول
                productImageRepository.save(new ProductImage(product, productLink.select(".kt-image-block__image").attr("data-src")));
            }
        }
    }
}
