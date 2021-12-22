package com.example.exp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;
    @ManyToOne
    @JoinColumn
    private User user;
    public Role(User user){
        this.user=user;
    }
}
