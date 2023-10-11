package com.example.cms_admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String description;
    private String address;
    private String phoneNum;
    private String logoCompany;

    @Transient
    public String getLogoPath(){
        if (logoCompany == null || id==null) return null;

        return "/logoCompany/" +id +"/"+logoCompany;
    }

}
