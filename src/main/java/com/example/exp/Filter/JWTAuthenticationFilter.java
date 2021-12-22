package com.example.exp.Filter;

import com.example.exp.entity.JwtUser;
import com.example.exp.entity.LoginUser;
import com.example.exp.utils.JwtUtil;
import com.example.exp.utils.SecurityConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {
    //private static StringRedisTemplate stringRedisTemplate = BeanInjectionHelper.getBean(StringRedisTemplate.class);
    private AuthenticationManager authenticationManager;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
        // set up the filter url
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException
    {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try{
            LoginUser loginUser=objectMapper.readValue(request.getInputStream(), LoginUser.class);
            System.err.println(loginUser.getUsername()+" "+loginUser.getPassword());
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword());
            return authenticationManager.authenticate(token);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication)
    {
        // getPrincipal()
        JwtUser user=(JwtUser)authentication.getPrincipal();
        List<String> roles=user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String token = JwtUtil.createToken(user.getUsername(), roles, true);
        // token - username
        //stringRedisTemplate.opsForValue().set(token, user.getUsername());
        System.err.println("SUUUCCC");
        //redisTemplate.opsForValue().set(user.getUsername(), token);
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
    }
}
