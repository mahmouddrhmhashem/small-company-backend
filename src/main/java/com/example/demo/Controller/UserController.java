package com.example.demo.Controller;

/*
import com.example.demo.Service.AuthenticationService;
*/
import com.example.demo.Entity.Products;
import com.example.demo.Service.MyLogoutHandler;
import com.example.demo.Service.UserService;
/*
import com.example.demo.configuration.EazyBankUsernamePwdAuthenticationProvider;
*/
import com.example.demo.dto.ProductsDTO;
import com.example.demo.dto.UserDTO;
        import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/customers")
public class UserController {
    private final UserService userService;
   private final MyLogoutHandler logoutHandler;
    //private final AuthenticationManager authenticationManager;
/*
    private final LogoutService logoutService;
*/
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO request)

    { return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public String authenticate(
            @RequestParam("username") String email , @RequestParam("password") String password,
            HttpServletResponse http) throws Exception {
        return userService.authenticate(email,password,http);
    }


     @PostMapping("/logout")
     public boolean logout(HttpServletRequest request,HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    logoutHandler.logout(request,response,authentication);
         return true;}

   /* public ResponseEntity<UserDTO> authenticate(
            @RequestParam("username") String email , @RequestParam("password") String password,
    HttpServletResponse http) {
        return ResponseEntity.ok(userService.authenticate(email,password, http));
    }
*/
   @PostMapping("/addCustomer")
   public UserDTO addCustomer(@RequestBody UserDTO userDTO){
       //BeanUtils.copyProperties(customerDTO,returnValue);

       return userService.createCustomer(userDTO);

   }
    @PutMapping
    public UserDTO updateCustomer(@RequestParam("id") int customer_Id, @RequestBody UserDTO userDTO){
        return userService.updateCustomer(customer_Id, userDTO);

    }
    @GetMapping("/get")
    public UserDTO get(@RequestParam int id){
        return userService.getCustomerById(id);
    }
    @DeleteMapping("/delete")
    public void deleteCustomer(@RequestParam("id") int customer_Id){
        userService.deletCustomer(customer_Id);
    }


    @PostMapping("/addfavorite")
    public ProductsDTO addFavorite(@RequestParam("id") int product_name){
        return userService.addFavorite(product_name);

    }
    @GetMapping("/showfavorite")
    public Set<Products> showFavorite() {
        return userService.showFavorite();
    }
}
