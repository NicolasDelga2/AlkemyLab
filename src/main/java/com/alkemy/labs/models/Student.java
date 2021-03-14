package com.alkemy.labs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastname;
    private String dni;
    private String legajo;

    @OneToOne
    @Cascade({CascadeType.ALL,CascadeType.DELETE})
    private User user;

    @ManyToMany
    @Cascade( {CascadeType.SAVE_UPDATE,CascadeType.DELETE} )
    private List<Subject> subject;
}
