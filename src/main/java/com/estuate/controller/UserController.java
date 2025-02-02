package com.estuate.controller;

import com.estuate.entity.User;
import com.estuate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        System.out.println(user);
        userService.saveUser(user);
        return "register";
    }


    @PostMapping("/find-match")
    public String findMatching(@ModelAttribute User user, @RequestParam int resultCount, Model model) {
        List<User> matching = userService.findMatching(user, resultCount);
        System.out.println(matching);
        model.addAttribute("matching", matching);
        return "index";
    }


}
