package com.mvm.config;

import com.mvm.service.JwtService;
import com.mvm.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
// to accept the geneterated Jwt token on swegger
@Component
public class JwtAuthFilter extends OncePerRequestFilter {//extends OncePerRequestFilter(it is an abstract class having one  method doFilterInternal) means jwtfilter will called once for every/per request

    @Autowired
    private JwtService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request,//request will receive the token
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");//getting header of the token

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);//fetching only token value from the output(header) by skipping "Bearer "=7 letter
            String username = jwtService.extractUsername(token);//extract username from the token.

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);


            if (jwtService.isValidToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }}
        }

        filterChain.doFilter(request, response);
    }
}

