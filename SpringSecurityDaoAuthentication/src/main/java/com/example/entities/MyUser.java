package com.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "userlogin" , schema = "springbootschema")
@Data
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String userName;

    private String password;

    private String roles;

}
