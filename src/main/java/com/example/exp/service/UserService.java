package com.example.exp.service;

import com.example.exp.entity.JwtUser;
import com.example.exp.entity.Role;
import com.example.exp.entity.User;
import com.example.exp.reposiroty.RoleRepository;
import com.example.exp.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    //这里的User是用来验证CurrentUser的,详见源码
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=userRepository.findByUsername(username);
        if(user!=null){
            List<Role> roles=roleRepository.findByUser_id(user.getId());
            if(roles!=null && !roles.isEmpty()){
                user.setRoles(roles);
            }
            return new JwtUser(user);
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }
    }
}
