package com.sivalabs.bookstore.webapp.web.controller;

import com.sivalabs.bookstore.webapp.client.catalog.CatalogServiceClient;
import com.sivalabs.bookstore.webapp.client.catalog.PagedResult;
import com.sivalabs.bookstore.webapp.client.catalog.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class ProductController {

    private final CatalogServiceClient catalogServiceClient;

    ProductController(CatalogServiceClient catalogServiceClient) {
        this.catalogServiceClient = catalogServiceClient;
    }

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String products(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int page) {
        return catalogServiceClient.getProducts(page);
    }
}
