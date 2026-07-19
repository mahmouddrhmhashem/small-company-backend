package com.example.demo.Service;


import com.example.demo.EmailValidation;
import com.example.demo.Entity.Products;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProductRepo;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepo;
import com.example.demo.configuration.CustomAuthenticationProvider;
import com.example.demo.dto.ProductsDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exceptionHandling.ErrorMessages;
import com.example.demo.exceptionHandling.UserServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final CustomAuthenticationProvider eazy;
    private final JWTTokenGenerator jwt;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;



    public UserDTO register(UserDTO request) {
        String email = request.getEmail();
        if(!EmailValidation.patternMatches(email))
        throw new UserServiceException(ErrorMessages.BAD_EMAIL.getErrorMessage());
       User user = convertToEntity(request);
        Optional<User> optional = userRepo.findByEmail(user.getEmail());

        if(optional.isPresent()){
            throw new UserServiceException(ErrorMessages.EMAIL_ALREADY_EXIST.getErrorMessage());
        }
        Set<Role> roles = roleRepository.findByAuthority("USER");
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepo.save(user);

        return convertToDTO(user);

    }
    public String authenticate(String email , String password, HttpServletResponse httpServletResponse)
    throws Exception {

        Authentication authentication = eazy.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        jwt.generatejwt(httpServletResponse,authentication);
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "success";

    }





    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional =  userRepo.findByEmail(username);
        if(optional.isPresent())
            return optional.get();
        throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

    }
    public UserDTO createCustomer(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepo.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO getCustomerById(int id) {
        Optional<User> optional =  userRepo.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            return convertToDTO(user);
        }
        throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    }
    public void deletCustomer(int id){
        userRepo.deleteById(id);
    }


    public UserDTO updateCustomer(int id , UserDTO userDTO){
        User user = userRepo.findById(id).orElse(null);
        assert user != null;
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        User updatedUser = userRepo.save(user);
        return convertToDTO(updatedUser);
    }
    public ProductsDTO addFavorite(int ID) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Products products = productRepo.findById(ID).orElse(null);
        User user = userRepo.findByEmail(name).orElse(null);
        user.setProducts(products);
        userRepo.save(user);
        ProductsDTO productsDTO = new ProductsDTO();
        BeanUtils.copyProperties(products,productsDTO);
        System.out.print("asdasd");
        return productsDTO;
    }

    public Set<Products> showFavorite() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(name).orElse(null);
        return user.getProducts();

    }


}