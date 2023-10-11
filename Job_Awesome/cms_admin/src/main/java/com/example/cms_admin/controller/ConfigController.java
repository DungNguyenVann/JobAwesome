package com.example.cms_admin.controller;

import com.example.cms_admin.model.Config;
import com.example.cms_admin.repository.ConfigRepository;
import com.example.cms_admin.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ConfigController {
    @Autowired
    ConfigService configService;

    @GetMapping("/configs")
    public String CompanyHomePage(Model model) {
        List<Config> configs = configService.configsList();
        model.addAttribute("configs", configs);

        return "configs/index";
    }

    @PostMapping("/saveConfigs")
    public String save(Config config, Model model) {
        configService.saveConfigs(config);
        model.addAttribute("configs", config);
        return "redirect:/configs";
    }

    @GetMapping("/newConfigs")
    public String showAddConfigPage(Model model) {
        Config config = new Config();
        model.addAttribute("configs", config);
        return "configs/createConfigs";
    }

    @GetMapping("/editConfigs/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Config config = configService.get(id);
        model.addAttribute("configs", config);

        return "configs/editConfigs";
    }

    @GetMapping("deleteConfigs/{id}")
    public String deleteConfigs(@PathVariable("id") long id) {
        configService.deleteConfigs(id);
        return "redirect:/configs";
    }
}
