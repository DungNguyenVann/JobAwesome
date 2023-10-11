package com.example.cms_admin.service;

import com.example.cms_admin.model.Company;
import com.example.cms_admin.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> listAll() {
        return companyRepository.findAll();
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company get(long id) {
        return companyRepository.findById(id).get();
    }

    public void delete(long id) {
        companyRepository.deleteById(id);
    }
}
