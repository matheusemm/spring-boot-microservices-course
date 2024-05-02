package com.sivalabs.bookstore.webapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ProductController {

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String products() {
        return "products";
    }
}
