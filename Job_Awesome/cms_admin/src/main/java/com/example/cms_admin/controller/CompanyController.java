package com.example.cms_admin.controller;

import com.example.cms_admin.model.Company;
import com.example.cms_admin.service.CompanyService;
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
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public String CompanyHomePage(Model model) {
        List<Company> companyList = companyService.listAll();
        model.addAttribute("company", companyList);

        return "company/index";
    }

    @GetMapping("/newCompany")
    public String addCompany(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);

        return "company/createCompany";
    }

    @PostMapping("/saveCompany")
    public String saveCompany(Company company, Model model,
                              @RequestParam("logo") MultipartFile multipartFile) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        company.setLogoCompany(fileName);
        Company saved = companyService.save(company);
        String uploadDir = "./logoCompany/" + saved.getId();

        Path uploadPath1 = Paths.get(uploadDir);

        if (!Files.exists(uploadPath1)) {
            Files.createDirectories(uploadPath1);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath1.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new IOException("could save uploaded file");
        }

        model.addAttribute("company", company);
        return "redirect:/companies";

    }

    @GetMapping("/editCompany/{id}")
    public String showEditProductPage(@PathVariable(name = "id") int id, Model model) {
        Company company = companyService.get(id);
        model.addAttribute("company", company);

        return "company/editCompany";
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable(name = "id") int id) {
        companyService.delete(id);
        return "redirect:/companies";
    }
}
