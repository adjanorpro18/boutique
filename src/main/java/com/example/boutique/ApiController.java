package com.example.boutique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    StoreService storeService;

    @GetMapping("products")
    public List<Product> getAllProducts() {

        System.out.println("___________GET api/products");
        return storeService.getAllProducts();
    }

    @PostMapping("products")
    public Product createProduct(@RequestBody Product newProduct) {

        System.out.println("___________ POST api/products");
        Product savedProduct = storeService.addProduct(newProduct);
        return savedProduct;
    }
}
