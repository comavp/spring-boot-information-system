package com.comavp.infsystem.controllers;


import com.comavp.infsystem.entities.Role;
import com.comavp.infsystem.entities.User;
import com.comavp.infsystem.service.UserService;
import com.comavp.infsystem.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private IUserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("name", user.getName());
        return "index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addUser(User user, Model model) {
        userService.addUser(user);

        return "redirect:/login";
    }

    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @RequestMapping(value = "/editUser/{user}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String shotEditUserPage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editUser(@RequestParam String name,
                           @RequestParam(value = "rolesList", required = false) ArrayList<String> rolesList,
                           @RequestParam(value = "id") User user) {

        user.setName(name);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        if (rolesList != null) {
            for (String key : rolesList) {
                if (roles.contains(key)) {
                    user.getRoles().add(Role.valueOf(key));
                }
            }
        } else {
            user.getRoles().add(Role.USER);
        }
        userService.saveUser(user);
        return "redirect:/userList";
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/userList";
    }
}
