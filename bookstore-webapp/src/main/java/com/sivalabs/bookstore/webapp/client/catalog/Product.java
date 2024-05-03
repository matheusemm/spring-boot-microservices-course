package com.sivalabs.bookstore.webapp.client.catalog;

import java.math.BigDecimal;

public record Product(String code, String name, String description, String imageUrl, BigDecimal price) {}
