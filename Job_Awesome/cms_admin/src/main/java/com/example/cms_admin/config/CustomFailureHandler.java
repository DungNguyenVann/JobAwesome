package com.example.cms_admin.config;

import com.example.cms_admin.model.User;
import com.example.cms_admin.repository.UserRepository;
import com.example.cms_admin.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");
        User user = userRepository.findByEmail(email);

        String password = request.getParameter("password");

        if (!user.isAccountStatus()) {
            exception = new LockedException("Your account is locked");
        } else if (email == null || password == null) {
            exception = new BadCredentialsException("Invalid Username or password");
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
