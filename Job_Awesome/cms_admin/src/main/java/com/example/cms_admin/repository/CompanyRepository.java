package com.example.cms_admin.repository;

import com.example.cms_admin.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
