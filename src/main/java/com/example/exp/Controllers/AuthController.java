package com.example.exp.Controllers;

import com.example.exp.entity.LoginUser;
import com.example.exp.entity.Role;
import com.example.exp.entity.User;
import com.example.exp.reposiroty.RoleRepository;
import com.example.exp.reposiroty.UserRepository;
import com.example.exp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    MailService mailService;
    @RequestMapping("/auth/register")
    public String register(@RequestBody LoginUser loginUser){
        User user = new User();
        user.setUsername(loginUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(loginUser.getPassword()));
        user.setEnabled(true);
        Role role=new Role(user);
        role.setRole("USER");

        userRepository.save(user);
        roleRepository.save(role);
        return "register successfully";
    }
    /**
     * TEST: send email to user
     * */
    @RequestMapping("/mail/code")
    public void sendEmail(@RequestParam(value = "to")String to){
        mailService.sendSimpleMail(to,"JAVA EMAIL TEST","JUST A TEST");
    }
}
