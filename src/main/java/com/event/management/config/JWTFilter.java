package com.event.management.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 4/11/2017.
 */
public class JWTFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "roles";

    @Override
<<<<<<< HEAD
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authHeader = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header.");
        } else {
            try {
                String token = authHeader.substring(7);
                Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
                httpServletRequest.setAttribute("claims", claims);
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(claims));
                filterChain.doFilter(request, response);
            } catch (SignatureException e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
            }
=======
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
>>>>>>> 5e3d9abe4d3edc1fb8e23554e52bc10efa3a86e1

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            //  chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {}

    /**
     * Method used to create authentication for Spring Security Context Holder from JWT claims
     * @param claims
     * @return token
     */
    private Authentication getAuthentication(Claims claims) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = (List<String>) claims.get(AUTHORITIES_KEY);
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        User principal = new User(claims.getSubject(), "", authorities);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        return token;
    }
}

