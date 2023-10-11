package com.example.cms_admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/register", "login", "index").permitAll()
                                .requestMatchers("/job-awesome/**").permitAll()
                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/products", "users", "company").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/homePage")
                                .failureHandler(failureHandler)
                                .successHandler(successHandler)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutSuccessUrl("/logout")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path userAvatarUploadDir = Paths.get("./user_avatar");
        String userAvatarUploadPath = userAvatarUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/user_avatar/**").addResourceLocations("file:/" + userAvatarUploadPath + "/");


        Path logoCompany = Paths.get("./logoCompany");
        String logoCompanyUploadPath = logoCompany.toFile().getAbsolutePath();
        registry.addResourceHandler("/logoCompany/**").addResourceLocations("file:/" + logoCompanyUploadPath + "/");
    }

    @Autowired
    CustomFailureHandler failureHandler;
    @Autowired
    CustomAuthSuccessHandler successHandler;

}
