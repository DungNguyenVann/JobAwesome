package com.example.cms_admin.repository;

import com.example.cms_admin.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long > {
}
