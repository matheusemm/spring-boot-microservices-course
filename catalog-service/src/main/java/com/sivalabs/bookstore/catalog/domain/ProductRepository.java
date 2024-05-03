package com.sivalabs.bookstore.catalog.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(
            "select new com.sivalabs.bookstore.catalog.domain.Product(p.code, p.name, p.description, p.imageUrl, p.price) from ProductEntity p")
    Page<Product> findAllProducts(Pageable pageable);

    Optional<Product> findByCode(String code);
}
