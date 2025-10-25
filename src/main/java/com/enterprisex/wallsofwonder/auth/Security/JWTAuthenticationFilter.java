package com.enterprisex.wallsofwonder.auth.Security;

import com.enterprisex.wallsofwonder.auth.DTO.Response.ResponseHandler;
import com.enterprisex.wallsofwonder.auth.ServiceImpl.UserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    @Autowired
    private JwtUtil jwtUtil;

    public JWTAuthenticationFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ResponseHandler handler = new ResponseHandler();
        try {
            final String token = jwtUtil.resolveToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.parseToken(request);
            if (claims != null & jwtUtil.validateClaims(claims)) {
                String email = claims.getSubject();
                UserDetail user = new UserDetail();
                user.setId(Long.valueOf(email));
                user.setPhoneNumber(claims.get(JwtUtil.TOKEN_CLAIM_NUMBER).toString());
                user.setUserName(email);
                user.setRole(claims.get(JwtUtil.TOKEN_CLAIM_ROLES).toString());
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception e) {
            handler.setMessage("Authentication Error");
            handler.setData(e.getMessage());
            handler.setStatus(HttpStatus.FORBIDDEN.value());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), handler);

        }
        filterChain.doFilter(request, response);
    }
}
