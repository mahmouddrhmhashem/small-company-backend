package com.example.demo.Service;


import com.example.demo.Entity.Products;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProductRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.dto.ProductsDTO;
import com.example.demo.exceptionHandling.ErrorMessages;
import com.example.demo.exceptionHandling.UserServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public Set<ProductsDTO> getProduct(){
       String name = SecurityContextHolder.getContext().getAuthentication().getName();
       Optional<User> optional = userRepo.findByEmail(name);
       if(optional.isPresent()){
           User user = optional.get();
       Set<ProductsDTO> productsDTOS = new HashSet<>();
        //     BeanUtils.copyProperties(user.getProducts(),productsDTOS);
        for (Products product : user.getProducts()) {
            ProductsDTO products = new ProductsDTO();
            BeanUtils.copyProperties(product,products);
            productsDTOS.add(products);}
            return productsDTOS;}
       throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

    }

    public ProductsDTO addProduct(ProductsDTO productsDTO){
        Products products = new Products();
        BeanUtils.copyProperties(productsDTO,products);
        productRepo.save(products);
        return productsDTO;


    }


    public Set<ProductsDTO> showProducts() {
        Set<ProductsDTO> productsDTOS = new HashSet<>();
        //     BeanUtils.copyProperties(user.getProducts(),productsDTOS);
        for (Products product : productRepo.findAll()) {
//            ModelMapper
            ProductsDTO products = new ProductsDTO();
            BeanUtils.copyProperties(product,products);
            productsDTOS.add(products);
        }

        return productsDTOS;
}

    public ProductsDTO showProduct(int id) {
        Optional<Products> optional =  productRepo.findById(id);
        ProductsDTO productsDTO = new ProductsDTO();
        if (optional.isPresent()) {
            Products products = optional.get();
            BeanUtils.copyProperties(products,productsDTO);
            return productsDTO;
        }
        throw new UserServiceException("User not found with the given ID.");
    }


    }

