package com.app.projectbackend.controllers;

import com.app.projectbackend.models.Product;
import com.app.projectbackend.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/shop")
    public String shopMain(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "shop-main";
    }

    @GetMapping("/product/add")
    public String productAdd(Model model) {
        return "product-add";
    }

    @PostMapping("product/add")
    public String shopProductAdd(@RequestParam String title, @RequestParam BigDecimal price, @RequestParam String description, Model model) {
        Product product = new Product(title, price, description);
        productRepository.save(product);
        return "redirect:/shop";
    }

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable(value = "id") long id, Model model) {
        if (!productRepository.existsById(id)) {
            return "redirect:/shop";
        }
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "product-details";
    }

    @GetMapping("/product/{id}/edit")
    public String productEdit(@PathVariable(value = "id") long id, Model model) {
        if (!productRepository.existsById(id)) {
            return "redirect:/shop";
        }
        Optional<Product> product = productRepository.findById(id);
        ArrayList<Product> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);
        return "product-edit";
    }

    @PostMapping("product/{id}/edit")
    public String shopProductUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam BigDecimal price, @RequestParam String description, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        productRepository.save(product);
        return "redirect:/shop";
    }

    @PostMapping("product/{id}/remove")
    public String shopProductDelete(@PathVariable(value = "id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return "redirect:/shop";
    }
}
