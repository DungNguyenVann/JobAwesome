package com.example.cms_admin.service;

import com.example.cms_admin.config.CustomUser;
import com.example.cms_admin.model.Role;
import com.example.cms_admin.model.User;
import com.example.cms_admin.repository.RoleRepository;
import com.example.cms_admin.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user== null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
//        return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
        return new CustomUser(user);
    }

    ////////////////////////NOTE
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

    public User saveUser(User user) {
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setPhoneNum(user.getPhoneNum());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountStatus(user.isAccountStatus());

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleIsExits();

        }
        user.setRoles(Arrays.asList(role));
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(User user, Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return new ArrayList<>(users);
    }

    public User get(long id) {
        return userRepository.findById(id).get();
    }

    public List<Role> findRoleUser() {
        return roleRepository.findAll();
    }

    private Role checkRoleIsExits() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
