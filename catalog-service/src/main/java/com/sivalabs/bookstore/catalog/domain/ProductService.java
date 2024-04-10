package com.sivalabs.bookstore.catalog.domain;

import com.sivalabs.bookstore.catalog.ApplicationProperties;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties properties;

    ProductService(ProductRepository productRepository, ApplicationProperties properties) {
        this.productRepository = productRepository;
        this.properties = properties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        var sort = Sort.by("name").ascending();
        var pageable = PageRequest.of(pageNo <= 1 ? 0 : pageNo - 1, properties.pageSize(), sort);
        var productsPage = productRepository.findAllProducts(pageable);

        return new PagedResult<>(productsPage);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }
}
