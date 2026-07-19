package com.example.demo.Service;

import com.example.demo.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Component
public class JWTTokenGenerator {

    public void generatejwt(HttpServletResponse response,Authentication authentication
                                    ) throws ServletException, IOException {
        if (null != authentication) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))

                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 3000000))
                    .signWith(key).compact();

            response.setHeader(SecurityConstant.JWT_HEADER, jwt);
        }

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
