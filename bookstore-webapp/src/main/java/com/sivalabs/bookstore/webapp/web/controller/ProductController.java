package com.sivalabs.bookstore.webapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ProductController {

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String products(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "products";
    }
}
