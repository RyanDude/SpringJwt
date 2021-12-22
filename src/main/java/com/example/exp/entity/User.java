package com.example.exp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String fullName;
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean enabled;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Preference> preferences_list = new ArrayList<>();
    List<SimpleGrantedAuthority> getRoles(){
        List<String> role_list=roles.stream().map(Role::getRole).collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(String s:role_list){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+s));
        }
        return authorities;
    }
}
