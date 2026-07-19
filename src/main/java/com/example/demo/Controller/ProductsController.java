package com.example.demo.Controller;

import com.example.demo.Service.ProductService;
import com.example.demo.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")

public class ProductsController {
    private final ProductService productService;

    @GetMapping("/showProducts")
    public Set<ProductsDTO> showProducts(){
       return productService.showProducts();
    }
    @GetMapping("/showProduct")
    public ProductsDTO showProduct(@RequestParam int id){
        return productService.showProduct(id);
    }
    @GetMapping("/products")
    public Set<ProductsDTO> getProducts(){
        return productService.getProduct();
    }
    @PostMapping("/add")
    public ProductsDTO addProduct(@RequestBody ProductsDTO productsDTO){
        return productService.addProduct(productsDTO);

    }
}
