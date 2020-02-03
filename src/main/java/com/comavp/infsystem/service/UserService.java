package com.comavp.infsystem.service;


import com.comavp.infsystem.entities.Role;
import com.comavp.infsystem.entities.User;
import com.comavp.infsystem.repositories.UserRepository;
import com.comavp.infsystem.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        User currentUser = null;
        List<User> users = userRepository.findAll();
        for (User s : users) {
            if (s.getName().equals(username)) {
                currentUser = s;
                break;
            }
        }
        return currentUser;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findByUsername(s);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        User currentUser = findByUsername(user.getName());
        if (currentUser == null) {
            user.setActive(true);
            if (userRepository.count() == 0) {
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(Role.valueOf("ADMIN"));
                adminRoles.add(Role.valueOf("MODERATOR"));
                user.setRoles(adminRoles);
            } else {
                user.setRoles(Collections.singleton(Role.USER));
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
