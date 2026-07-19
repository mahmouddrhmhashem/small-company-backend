package com.example.demo.Service;

import com.example.demo.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class MyLogoutHandler  {
            private final BlackList blackList;
          public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = request.getHeader(SecurityConstant.JWT_HEADER);
        if (null != jwt) {
            try {

                blackList.addToBlacklist(jwt);
                authentication.setAuthenticated(false);
                SecurityContextHolder.clearContext();

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
    }



}