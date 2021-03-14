package com.alkemy.labs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username", unique = true)
    private String username;
    @Column(name="password")
    private String password;

    @Column(name="rol") // 0 = Administrator - 1 User/student
    private int rol;

    @Column(name ="isActive")
    private int activo; // 0 = not active - 1 = active


}
