package com.fawry.foodorderingapi.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fawry.foodorderingapi.entity.MyUser;
import com.fawry.foodorderingapi.repository.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserRepo myUserRepo;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username,password;

        try {
            Map<String,String> requestMap=new ObjectMapper().readValue(request.getInputStream(),Map.class);
            username=requestMap.get("username");
            password = requestMap.get("password");
        }catch (IOException e){
            throw new AuthenticationServiceException(e.getMessage(),e);
        }

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);

    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            MyUser user= (MyUser)authResult.getPrincipal();
            Algorithm algorithm= Algorithm.HMAC256("secret".getBytes());
            String access_token= JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis()+10*60*10*10*1000))
                    .withIssuer(request.getRequestURI().toString())
                    .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String,String>tokens=new HashMap<>();
            tokens.put("access_token",access_token);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);



    }
}
