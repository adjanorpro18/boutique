package com.example.boutique;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
    public class MvcController {

        @Autowired
        StoreService storeService;

        @GetMapping("/products")
        public String getProducts(Model model) {

            model.addAttribute("products", storeService.getAllProducts());

            return "products"; // templates/products.html
        }
    @GetMapping("create-product")
    public String createForm(Product newProduct){

        storeService.addProduct(newProduct);

        return "create-product";
    }

        @PostMapping("products")
        public String create( Model model, Product newProduct){

            storeService.addProduct(newProduct);

            model.addAttribute("products", storeService.getAllProducts());

            return "products";
        }
}
