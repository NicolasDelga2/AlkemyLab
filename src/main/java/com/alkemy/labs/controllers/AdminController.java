package com.alkemy.labs.controllers;

import com.alkemy.labs.models.User;
import com.alkemy.labs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String getUsers(Model model){
        List<User> userList = userService.getUsers().stream()
                .filter(user -> user.getRol() != 1)
                .collect(Collectors.toList());

        model.addAttribute("users", userList);
        return "admin";
    }

    @GetMapping("/findById/")
    @ResponseBody
    public Optional<User> getUser(int id){
        return userService.getUser(id);
    }

    @PostMapping("/addNew")
    public String addNew(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT,RequestMethod.GET})
    public String update(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }


    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(int id){
        userService.delete(id);

        return "redirect:/admin";
    }

}
