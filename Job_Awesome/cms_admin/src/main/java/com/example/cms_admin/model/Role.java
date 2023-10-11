package com.example.cms_admin.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;
    private String slug;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Override
    public String toString(){
        return this.name;//de hien thi trong thymeleaf
    }

    public String getSlug() {
        return this.name;
    }

    public void setSlug(String slug) {
        this.name = slug;
    }
}
