package com.example.cms_admin.controller;

import com.example.cms_admin.model.User;
import com.example.cms_admin.repository.UserRepository;
import com.example.cms_admin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {

    private UserService userService;
    private UserRepository userRepository;

    public HomePageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, User user){
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/homePage")
    public String home(){return "index";}

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result, Model model){

        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser !=null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,"There is already an account registered with the same email");
        }
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

}
