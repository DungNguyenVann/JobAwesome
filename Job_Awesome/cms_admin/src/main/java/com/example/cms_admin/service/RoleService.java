package com.example.cms_admin.service;

import com.example.cms_admin.model.Role;
import com.example.cms_admin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> listRole(){return roleRepository.findAll();}
    public Role save(Role role){return roleRepository.save(role);}
    public Role get(Long id){return roleRepository.findById(id).get();}
    public void delete(Long id){roleRepository.deleteById(id);}

}
