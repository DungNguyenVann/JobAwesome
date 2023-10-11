package com.example.cms_admin.controller;

import com.example.cms_admin.model.Role;
import com.example.cms_admin.model.User;
import com.example.cms_admin.repository.RoleRepository;
import com.example.cms_admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUser(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "user/index";
    }

    @GetMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") Long id, User user) {
        userService.deleteUser(user, id);
        return "redirect:/users?deleteSuccess";
    }

    @PostMapping("/saveUser")
    public String saveUser(Model model, User user,
                           @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setImage(fileName);
        User saved = userService.saveUser(user);
        String uploadDir = "./user_avatar/" + saved.getId();

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("could save uploaded file");
        }
        model.addAttribute("users", user);
        return "redirect:/users";
    }

    @GetMapping("/newUser")
    public String showAddUserPage(Model model) {
        User user = new User();
        List<Role> roles = userService.findRoleUser();
        model.addAttribute("roles", roles);
        model.addAttribute("users", user);

        return "user/createUser";
    }

    @GetMapping("/editUser/{id}")
    public String showEditProductPage(@PathVariable(name = "id") int id, Model model) {
        User users = userService.get(id);
        List<Role> roles = userService.findRoleUser();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);

        return "user/editUser";
    }

}
