package com.example.delesblog.utilsc;

/* The purpose of the code is to handle user authentication based on JWT token
* 1ST - Check the authorization header is not empty and starts with bearer than set
* token and username extracted from token
*
* 2ND - Check if the username extracted from the token is not set in securityContextHolder, if true
then set the UserDetails(user information) gotten from database for that user to userDetails/
3RD - then check the validity of the token against the username extracted from userDetails
if token is still valid, then create a new UPAToken with the UserDetails, password set as null, and authorities of that user
4TH - Set more information such as the remote address and session ID to the UPAToken using the WebAuthenticationDetailSource
5TH - finally set the UPAToken with the userDetails and other information to the security ContextHolder
 so SSpring Security knows this user is now authenticated

using filterChain.do    Filter(request, response) to continue to next request */



import com.example.delesblog.serviceImpl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils utils;
    private final UserServiceImpl userService;

    @Autowired
    public JwtAuthenticationFilter(@Lazy UserServiceImpl userService, JwtUtils utils) {
         this.utils = utils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String authorizationHeader = null;
        String username = null;
        UserDetails userDetails = null;

        authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            token = authorizationHeader.substring(7);
            username = utils.extractUsername.apply(token);

        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            userDetails = userService.loadUserByUsername(username);

            if(userDetails != null && utils.isTokenValid.apply(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
