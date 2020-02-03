package com.comavp.infsystem.service.iservice;

import com.comavp.infsystem.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface IUserService extends UserDetailsService {
    User findByUsername(String username);
    void saveUser(User user);
    List<User> findAll();
    void addUser(User user);
    void deleteUser(Integer id);
}
