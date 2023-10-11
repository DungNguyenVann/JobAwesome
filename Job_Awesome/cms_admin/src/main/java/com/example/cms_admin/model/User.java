package com.example.cms_admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String displayName;
    private String image;
    private String password;
    private String email;
    private String phoneNum;
    private int chanel;
    private boolean accountStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

    @Transient
    public String getAvatarImagePath(){
        if(image == null || id==null) return null;

        return "/user_avatar/"+id +"/"+image;
    }
}
