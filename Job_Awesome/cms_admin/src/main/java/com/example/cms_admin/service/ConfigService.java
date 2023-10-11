package com.example.cms_admin.service;

import com.example.cms_admin.model.Config;
import com.example.cms_admin.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService {
    @Autowired
    ConfigRepository configRepository;

    public List<Config> configsList(){
        return configRepository.findAll();
    }

    public void saveConfigs(Config config){
        configRepository.save(config);
    }

    public void deleteConfigs(long id){
        configRepository.deleteById(id);
    }

    public Config get(long id){
        return configRepository.findById(id).get();
    }

}
