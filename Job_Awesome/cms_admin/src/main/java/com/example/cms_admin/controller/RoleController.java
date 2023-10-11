package com.example.cms_admin.controller;

import com.example.cms_admin.model.Role;
import com.example.cms_admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/roles")
    public String showListRole(Model model) {
        List<Role> roles = roleService.listRole();
        model.addAttribute("roles", roles);
        return "role/index";
    }

    @GetMapping("/newRole")
    public String showAddRolePage(Model model) {
        Role role = new Role();
        model.addAttribute("roles", role);
        return "role/createRole";
    }

    @PostMapping("/saveRole")
    public String saveRole(Model model, Role role) {
        roleService.save(role);
        model.addAttribute("roles", role);
        return "redirect:/roles";
    }

    @GetMapping("/editRole/{id}")
    public String showEditRolePage(@PathVariable("id") long id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("roles", role);
        return "role/editRole";
    }

    @GetMapping("deleteRole/{id}")
    public String deleteRole(@PathVariable("id") long id) {
        roleService.delete(id);
        return "redirect:/roles";
    }
}
